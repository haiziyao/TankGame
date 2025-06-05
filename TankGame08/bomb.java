package TankGame;

import java.util.Vector;

/*
作者：宇宙超级无敌大马猴
姓：亥
字：子曜
号：栖逸居士
版本号：包是最新版的
可以通过life调节显示爆炸的图片的数量，
通过调节sleep的时间可以调节图片显示的快慢
*/   public class bomb extends Thread {
    int x;
    int y;
    int life = 14;
    boolean islive = true;
    Vector bomb=new Vector();


    @Override
    public void run() {
        while (islive) {

        if (life > 0 && islive) {
            TankGame.mp.drawbomb(x, y,life, TankGame.mp.getGraphics());
           life--;
        } else {
            islive = false;
        }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public bomb(shoot shoot, tank tank){
        x=tank.getX();
        y=tank.getY();

    }

    public void lifeDown(){
        --life;
        islive = false;
    }
}
