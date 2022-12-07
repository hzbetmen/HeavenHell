package main;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;

    private float xDelta = 100, yDelta = 100;

    private BufferedImage img, idleAni[];
    private int aniTick, aniIndex, aniSpeed = 30;


    public GamePanel() {

        mouseInputs = new MouseInputs(this);

        importImg();
        loadAnimations();
        setPanelSize();

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //private void ani
    private void loadAnimations() {
        idleAni = new BufferedImage[5];
        for (int i = 1; i < idleAni.length; i++) {
            idleAni[i] = img.getSubimage(i*64, 0, 64, 40);
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1920, 1080);
        setPreferredSize(size);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.drawImage(idleAni[2], (int) xDelta, (int) yDelta, 128, 80, null);


    }

    public void setPos(int x, int y) {
        xDelta = x;
        yDelta = y;
    }

}
