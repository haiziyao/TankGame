package TankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

/*
作者：宇宙超级无敌大马猴
姓：亥
字：子曜
号：栖逸居士
版本号：包是最新版的 
*/   public class Mypanel extends JPanel implements KeyListener,Runnable {

    Hero hero =null;
    Hero hero2=null;
    static Vector<Enemy> enemies = new Vector<>();
    bomb bomb = null;
    static int  enemiessize = 5 ;  //因为用了.properties这个配置文件,
    // 所以这里设置一个默认值，防止文件打不开而程序无法运行
    //    but 好像直接其他地方报错了，并没有什么用
    Properties p = new Properties();
    static String filename ="out/game.properties";
    FileReader fr = null;
    static {
        Properties prop = new Properties();
        try {
            prop.load(new FileReader("out/configuration.properties"));
            enemiessize = Integer.parseInt(prop.getProperty("enemiessize"));
            filename= prop.getProperty("filename");
        } catch (IOException e) {
            System.out.println("文件读取错误，已经使用默认值");
            throw new RuntimeException(e);

        }

    }


    public Mypanel() throws IOException {
        p.load(fr=new FileReader(filename));
        if (TankGame.downloadstore){
            int h1x = Integer.parseInt(p.getProperty("h1x"));
            int h1y = Integer.parseInt(p.getProperty("h1y"));
            int h2x = Integer.parseInt(p.getProperty("h2x"));
            int h2y = Integer.parseInt(p.getProperty("h2y"));
            boolean h1l = Boolean.parseBoolean(p.getProperty("h1l"));
            boolean h2l = Boolean.parseBoolean(p.getProperty("h2l"));
            hero =new Hero(h1x ,h1y );
            hero.islive=h1l;
            hero2 =new Hero(h2x ,h2y );
            hero2.islive=h2l;
        }
        else {hero = new Hero(100,800);
            hero2 = new Hero(300,300);
        }
        //new Thread(new setEnemysize()).start();//想要坦克源源不断补充，但造成了线程冲突
        for (int i = 0; i < enemiessize; i++) {
            Enemy e;
            if (TankGame.downloadstore){
                int x = Integer.parseInt(p.getProperty("e"+i+"x"));
                int y = Integer.parseInt(p.getProperty("e"+i+"y"));
                int d = Integer.parseInt(p.getProperty("e"+i+"d"));
                boolean l = Boolean.parseBoolean(p.getProperty("e"+i+"l"));
                e =new Enemy(x,y);
                e.setDirection(d);
                e.islive=l;
            }else{
                e =new Enemy((100*(i+1))%1200,100);
                e.setDirection(2);
            }
            enemies.add(e);
            Thread thread = new Thread(e);
            thread.start();
            shoot shoot =new shoot(e.getX(),e.getY(),e.getDirection());
            e.shoots.add(shoot);
            //System.out.println("敌方坦克子弹被添加");
            new Thread(shoot).start();
            //System.out.println("敌方坦克子弹已经启动了");
        }


    }




    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

//            for (int i = 0; i < enemiessize; i++) {
//                enemies.get(i).randommove();
//            }

            //遍历敌人坦克
            for (int i = 0; i < enemiessize; i++) {
                Enemy enemy = enemies.get(i);
            }
            //判断敌人坦克是否被我方子弹击中
            for (int j = 0; j < hero.shoots.size(); j++) {
                for (int i = 0; i < enemiessize; i++) {
                    shoot shoot =hero.shoots.get(j);
                    if (enemies.get(i).islive&&shoot.islive) {
                        hit hit =new hit(shoot, enemies.get(i));
                        bomb=hit.bomb;
                        if(bomb!=null) {
                            new Thread(bomb).start();
                        }
                    }
                }
            }
            //判断英雄是否被敌方子弹击中
            for (int j = 0; j < enemiessize; j++) {
                Enemy e = enemies.get(j);
                for (int i = 0; i < e.shoots.size(); i++) {
                    shoot shoot=e.shoots.get(i);
                    if (shoot.islive&&hero.islive){
                    hit hit =new hit(shoot,hero);
                    bomb =hit.bomb;
                    if(bomb!=null) {
                        new Thread(bomb).start();
                    }
                }
                }
                for (int i = 0; i < e.shoots.size(); i++) {
                    shoot shoot=e.shoots.get(i);
                    if (shoot.islive&&hero2.islive){
                    hit hit =new hit(shoot,hero2);
                    bomb =hit.bomb;
                    if(bomb!=null) {
                        new Thread(bomb).start();
                    }
                }
                }
            }


//          这些代码已被合并
//            if(bomb!=null) {
//                if(bomb.life>0&&bomb.islive) {
//                    drawbomb(bomb.x,bomb.y,bomb.life,getGraphics());
//                    bomb.life--;
//                }else{
//                    bomb.islive=false;
//                }
//            }
            //判断我方坦克是否被敌人坦克击中
            if (TankGame.store){
                for (int i = 0; i < enemiessize; i++) {
                    Enemy e = enemies.get(i);
                    p.setProperty("e"+i+"x",e.getX()+"");
                    p.setProperty("e"+i+"y",e.getY()+"");
                    p.setProperty("e"+i+"d",e.getDirection()+"");
                    p.setProperty("e"+i+"l",e.islive+"");
                }
                p.setProperty("h"+1+"x",hero.getX()+"");
                p.setProperty("h"+1+"y",hero.getY()+"");
                p.setProperty("h"+1+"d",hero.getDirection()+"");
                p.setProperty("h"+1+"l",hero.islive+"");
                p.setProperty("h"+2+"x",hero2.getX()+"");
                p.setProperty("h"+2+"y",hero2.getY()+"");
                p.setProperty("h"+2+"d",hero2.getDirection()+"");
                p.setProperty("h"+2+"l",hero2.islive+"");
                try {
                    p.store(new FileWriter(filename),null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            this.repaint();
        }
    }




    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                if(hero.getDirection() != 0){
                    hero.setDirection(0);
                }else
                    hero.moveup();
                break;
            case KeyEvent.VK_S:
                if(hero.getDirection() != 2){
                    hero.setDirection(2);
                }else
                    hero.movedown();
                break;
            case KeyEvent.VK_D:
                if(hero.getDirection() != 1){
                    hero.setDirection(1);
                }else
                    hero.moveright();
                break;
            case KeyEvent.VK_A:
                if(hero.getDirection() != 3){
                    hero.setDirection(3);
                }else
                    hero.moveleft();
                break;
            case KeyEvent.VK_LEFT:
                if(hero2.getDirection() != 3){
                    hero2.setDirection(3);
                }else{
                    hero2.moveleft();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(hero2.getDirection() != 1){
                    hero2.setDirection(1);
                }else {
                    hero2.moveright();
                }
                break;
            case KeyEvent.VK_UP:
                if(hero2.getDirection() != 0){
                    hero2.setDirection(0);
                }else {
                    hero2.moveup();
                }
                break;
            case KeyEvent.VK_DOWN:
                if(hero2.getDirection() != 2){
                    hero2.setDirection(2);
                }else {
                    hero2.movedown();
                }
                break;
        }

        if (e.getKeyCode() == KeyEvent.VK_L||e.getKeyCode() == KeyEvent.VK_SPACE){
            switch (hero.getDirection()) {
                case 0:hero.moveup();hero.moveup();hero.moveup();break;
                case 1:hero.moveright();hero.moveright();hero.moveright();break;
                case 2:hero.movedown();hero.movedown();hero.movedown();break;
                case 3:hero.moveleft();hero.moveleft();hero.moveleft();break;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_J&&hero.islive) {
            hero.fire();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1500, 1000);
        if (hero.islive){ drawTank(hero.getX(),hero.getY(),g,hero.getDirection(),0);}
        if(hero2.islive){drawTank(hero2.getX(),hero2.getY(),g,hero2.getDirection(),0);}//绘制出我想要的坦克
        for (int i = 0; i < enemies.size(); i++) {
            if(enemies.get(i).islive){
                drawTank(enemies.get(i).getX(),enemies.get(i).getY(),g,enemies.get(i).getDirection(),1);
            }
            for (int j = 0; j < enemies.get(i).shoots.size(); j++) {
                if (enemies.get(i).shoots.get(j).islive&&enemies.get(i).shoots.get(j)!=null){
//                    g.fillOval(enemies.get(i).getX(),enemies.get(i).getY(),8,8);
                    drawshoot(enemies.get(i).shoots.get(j).x,enemies.get(i).shoots.get(j).y,enemies.get(i).shoots.get(j).direction,g);
                    //System.out.println("坦克子弹被绘制");
                }
            }
        }

        for (int i = 0; i < hero.shoots.size(); i++) {

            if (hero.shoots.get(i)!=null&& hero.shoots.get(i).islive){
                shoot shoot =hero.shoots.get(i);
                herodrawshoot(shoot.x,shoot.y,shoot.direction,g);
            }
        }



    }


//    public void drawshoot(int a,int b,int type,Graphics g) {
//        switch (type) {
//            case 0:
//                g.setColor(Color.red);
//                g.fillOval(a,b-40,7,7);
//
//                break;
//            case 1:
//                break;
//            case 2:
//                break;
//            case 3:
//                break;
//        }
//
//    }


    public void drawshoot(int x,int y,int direction,Graphics g){
            g.setColor(Color.red);
        g.fillOval(x-4,y-4,8,8);
    }

    public void herodrawshoot(int x,int y,int direction,Graphics g){
        g.setColor(Color.green);
        g.fillOval(x-4,y-4,8,8);
    }


    public void drawbomb(int x,int y,int life,Graphics g){

            if (life > 9) {
                g.setColor(Color.red);
                g.fillOval(x - 50, y - 50, 100, 100);
            } else if (life > 7) {
                g.setColor(Color.red);
                g.fillOval(x - 30, y - 30, 60, 60);
            } else if (life > 4) {
                g.setColor(Color.red);
                g.fillOval(x - 20, y - 20, 40, 40);
            } else if (life >= 0) {
                g.setColor(Color.red);
                g.fillOval(x - 9, y - 9, 18, 18);
            }

    }





    public void drawTank(int a,int b,Graphics g,int direction,int type) {//注意这里a,b表示坦克中心坐标
        //int x=a-30;int y=b-30;
        switch (type) {
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.red);
                break;
        }
        switch (direction) {//0 1 2 3表示上 右 下 左
            case 0:
                g.fill3DRect(a-30, b-30, 10,60,false);
                g.fill3DRect(a+20,b-30,10,60,false);
                g.fill3DRect(a-20,b-20,40,40,false);
                g.setColor(Color.green);
                g.fillOval(a-20,b-20,40,40);
                g.setColor(Color.blue);
                g.fillOval(a-10,b-10,20,20);
                g.setColor(Color.red);
                g.drawLine(a,b,a,b-40);
                break;
            case 1:
                g.fill3DRect(a-30, b-30, 60,10,false);
                g.fill3DRect(a-30,b+20,60,10,false);
                g.fill3DRect(a-20,b-20,40,40,false);
                g.setColor(Color.green);
                g.fillOval(a-20,b-20,40,40);
                g.setColor(Color.blue);
                g.fillOval(a-10,b-10,20,20);
                g.setColor(Color.red);
                g.drawLine(a,b,a+40,b);
                break;
            case 2:
                g.fill3DRect(a-30, b-30, 10,60,false);
                g.fill3DRect(a+20,b-30,10,60,false);
                g.fill3DRect(a-20,b-20,40,40,false);
                g.setColor(Color.green);
                g.fillOval(a-20,b-20,40,40);
                g.setColor(Color.blue);
                g.fillOval(a-10,b-10,20,20);
                g.setColor(Color.red);
                g.drawLine(a,b,a,b+40);
                break;
            case 3:
                g.fill3DRect(a-30, b-30, 60,10,false);
                g.fill3DRect(a-30,b+20,60,10,false);
                g.fill3DRect(a-20,b-20,40,40,false);
                g.setColor(Color.green);
                g.fillOval(a-20,b-20,40,40);
                g.setColor(Color.blue);
                g.fillOval(a-10,b-10,20,20);
                g.setColor(Color.red);
                g.drawLine(a,b,a-40,b);
                break;
        }



    }


}
