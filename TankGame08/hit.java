package TankGame;

/*
作者：宇宙超级无敌大马猴
姓：亥
字：子曜
号：栖逸居士
版本号：包是最新版的 
*/   public class hit {
    bomb bomb;
        public hit(shoot shoot,tank tank) {
            int a =shoot.x;
            int b =shoot.y;
            int x =tank.getX();
            int y =tank.getY();
            if (a>=x-30 && a<=x+30 && b>=y-30 && b<=y+30){
                shoot.islive=false;
                tank.islive=false;
                bomb = new bomb(shoot, tank);
            }
            //System.out.println("比较成功");

        }
}
