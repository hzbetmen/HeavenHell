package ui;

import utilz.LoadSave;

import static utilz.Constants.Environment.pauseOverlay.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayHomeReplayButton extends Button {

    private BufferedImage[] imgs;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    public PlayHomeReplayButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);

        this.rowIndex = rowIndex;
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.PLAY_HOME_REPLAY);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * PLAYANDHOMEBUTTON_DEFAULT_WIDTH, rowIndex * PLAYANDHOMEBUTTON_DEFAULT_HEIGHT, PLAYANDHOMEBUTTON_DEFAULT_WIDTH, PLAYANDHOMEBUTTON_DEFAULT_HEIGHT);
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[index], x, y, PLAYANDHOMEBUTTON_WIDTH, PLAYANDHOMEBUTTON_HEIGHT, null);
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

}
