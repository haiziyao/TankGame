package TankGame;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/*
作者：宇宙超级无敌大马猴
姓：亥
字：子曜
号：栖逸居士
版本号：包是最新版的
01 实现了hero的多发子弹
02 实现了子弹的攻击以及爆炸效果
03 修复了hero死亡后仍然可以发射子弹的bug,修复了坦克穿出地图的bug
04 基本实现所有的tank功能
05 添加了一些保护机制s
06 添加了空格和L 加速移动的功能
07 实现了敌人坦克的碰撞箱，但仍然存在
08 实现了对于游戏数据的记录，从而使得能够继续进行上一场游戏
*/   public class TankGame extends JFrame {

     public static Mypanel mp=null;
     public static boolean store =true;
     public static boolean downloadstore =false;
    // public static boolean downloadstore =true;
     static boolean safe = true;
    static {
        Properties prop = new Properties();
        try {
            prop.load(new FileReader("out/configuration.properties"));
            store = Boolean.parseBoolean(prop.getProperty("store"));
            safe = Boolean.parseBoolean(prop.getProperty("safe"));
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    }


    public static void main(String[] args) throws IOException {
            TankGame tankGame01=new TankGame();
    }
    public TankGame() throws IOException {
        if (safe) {new safeprotect();}
        mp=new Mypanel();
        this.add(mp);
        new Thread(mp ).start();
        this.setTitle("Tank Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200,1000);
        this.addKeyListener(mp);
        this.setVisible(true);
    }
}
