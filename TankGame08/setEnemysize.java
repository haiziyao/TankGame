package TankGame;

/*
作者：宇宙超级无敌大马猴
姓：亥
字：子曜
号：栖逸居士
版本号：包是最新版的 
*/   public class setEnemysize extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //Mypanel.enemiessize++;编译错误了，先暂时关掉原因在于多线程，冲突
        }
    }
}
