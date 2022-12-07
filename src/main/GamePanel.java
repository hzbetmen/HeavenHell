package main;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;

    private float xDelta = 100, yDelta = 100;

    private BufferedImage img, idleAni[];
    private int aniTick, aniIndex, aniSpeed = 30;
    private final int imgTile = 16;
    private int blockScale = 4;
    private int imgHeight = imgTile * blockScale, imgWidth = imgTile * blockScale;


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
        InputStream is = getClass().getResourceAsStream("/FLOOR_HELL.png");

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
//        idleAni = new BufferedImage[2];
//        for (int i = 0; i < idleAni.length; i++) {
//            idleAni[i] = img.getSubimage(i*8, 0, 8, 16);
//        }
    }

    private void setPanelSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = new Dimension(screenSize.width,screenSize.height);
        setPreferredSize(size);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.drawImage(img, (int) xDelta, (int) yDelta, imgWidth, imgHeight, null);


    }

    public void setPos(int x, int y) {
        xDelta = x;
        yDelta = y;
    }

}
