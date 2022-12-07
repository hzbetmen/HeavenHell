package main;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    public GameWindow(GamePanel gamePanel) {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        jFrame.setUndecorated(true);
        jFrame.add(gamePanel);
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setVisible(true);
    }

}
