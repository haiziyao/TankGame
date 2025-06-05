package TankGame;

import java.util.Vector;

/*
作者：宇宙超级无敌大马猴
姓：亥
字：子曜
号：栖逸居士
版本号：包是最新版的 
*/   public class tank {
    private int x;
    private int y;
    private int speed=4;
    private int direction;
    Boolean islive = true;



    public void moveup(){
        if((this.y-speed-30)>0)
        {y-=speed;}
    }
    public void movedown(){
        if((this.y+speed+60)<1000)
        {y+=speed;}
    }
    public void moveleft(){
        if((this.x-speed-30)>0)
        { x-=speed;}
    }
    public void moveright(){
        if((this.x+speed+40)<1200)
         {x+=speed;}
    }


    public void randommove() {
        if (islive){
            boolean up=true,down=true,right=true,left=true;
                for (int j = 0; j < Mypanel.enemiessize; j++) {
                    Enemy E = Mypanel.enemies.get(j);
                    if (this!=E){
                        int X =this.x-E.getX();
                        int Y =this.y-E.getY();
                        if (X>0&&X-speed<60){
                            left=false;
                        }
                        if (X<0&&X+speed>-60){
                            right=false;
                        }

                        if (Y>0&&Y-speed<60){
                            up=false;
                        }
                        if (Y<0&&Y+speed>-60){
                            down=false;
                        }
                    }
                }


        int random = (int) (Math.random() * 8 + 6);
        switch (random % 7) {
            case 0:
                direction = 0;
                for (int i = 0; i < random; i++) {
                    if (up){ moveup();}
                }
                break;
            case 1:
            case 4:
                direction = 1;
                for (int i = 0; i < random; i++) {
                    if(right){moveright();}
                }
                break;
            case 2:
            case 5:
            case 6:
                direction = 2;
                for (int i = 0; i < random; i++) {
                   if(down){movedown();}
                }
                break;
            case 3:
                direction = 3;
                for (int i = 0; i < random; i++) {
                    if(left){moveleft();}
                }
                break;
        }


    }//true
    }





    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}




class Hero extends tank {
    Vector<shoot> shoots = new Vector<>();

    public Hero(int x, int y) {
        super(x, y);
    }


    public void fire(){
        shoot shoot = null;
        switch (this.getDirection()){
            case 0:
                shoot=new shoot(this.getX(),this.getY()-40,this.getDirection());
                break;
            case 1:
                shoot=new shoot(this.getX()+40,this.getY(),this.getDirection());
                break;
            case 2:
                shoot=new shoot(this.getX(),this.getY()+40,this.getDirection());
                break;
            case 3:
                shoot=new shoot(this.getX()-40,this.getY(),this.getDirection());
                break;
        }
        new Thread(shoot).start();
        shoots.add(shoot);


    }
}










class Enemy extends tank implements Runnable{
    int    everyshootsize=2;
    @Override
    public void run() {
        shoot shoot = null;
        while(this.islive){
             Thread thread = new Thread(this);
            try {
                thread.sleep(700);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            randommove();
            for (int i = 0; i < everyshootsize; i++) {
                switch (this.getDirection()){
                    case 0:
                        shoot=new shoot(this.getX(),this.getY()-40,this.getDirection());
                        break;
                    case 1:
                        shoot=new shoot(this.getX()+40,this.getY(),this.getDirection());
                        break;
                    case 2:
                        shoot=new shoot(this.getX(),this.getY()+40,this.getDirection());
                        break;
                    case 3:
                        shoot=new shoot(this.getX()-40,this.getY(),this.getDirection());
                        break;
                }
                shoots.add(shoot);
                new Thread(new Thread(shoot)).start();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    Vector<shoot> shoots = new Vector<>();
    public Enemy(int x, int y) {
        super(x, y);
    }

    }
