package ui;

import utilz.LoadSave;
import static utilz.Constants.Environment.pauseOverlay.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayButton extends PauseButton{

    private BufferedImage[] imgs;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    public PlayButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);

        this.rowIndex = rowIndex;
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.PLAY_BUTTON);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i*PLAYBUTTON_DEFAULT_WIDTH, 0, PLAYBUTTON_DEFAULT_WIDTH, PLAYBUTTON_DEFAULT_HEIGHT);
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[index], x, y, PLAYBUTTON_WIDTH, PLAYBUTTON_HEIGHT, null);
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
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
