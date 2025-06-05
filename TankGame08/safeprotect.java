package TankGame;

/*
作者：宇宙超级无敌大马猴
姓：亥
字：子曜
号：栖逸居士
版本号：包是最新版的 
*/   public class safeprotect {
    public safeprotect() {
            if (Mypanel.enemiessize>100){
                Mypanel.enemiessize=(int) (Math.random()*21)+5;//好像不管用
                System.out.println("我们出于对您的电脑的考虑，敌人数量不能大于100");
                System.out.println("已经自动为您改成  "+Mypanel.enemiessize);
                System.out.println("如果您强制想要改变敌人数量，想死机，那就在TakeGame中关闭安全保护");
                System.out.println("具体措施：将safe的值改为false");
            }
    }
}
