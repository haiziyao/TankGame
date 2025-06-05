package TankGame;

/*
作者：宇宙超级无敌大马猴
姓：亥
字：子曜
号：栖逸居士
版本号：包是最新版的 
*/   public class shoot implements Runnable {
    int x;
    int y;
    int direction;
    int speed=3;
    boolean islive=true;
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            switch(direction){
                case 0:
                    y-=speed*2;
                    break;
                case 1:
                    x+=speed*2;
                    break;
                case 2:
                    y+=speed*2;
                    break;
                case 3:
                    x-=speed*2;
                    break;
            }


            //System.out.println(x+"  "+y);


            if (!(x>=0&&x<=1200&&y>=0&&y<=1000)){
                islive=false;
                break;
            }
        }
    }

    public shoot(int x, int y, int direction) {
        this.y = y;
        this.x = x;
        this.direction = direction;
    }
}
