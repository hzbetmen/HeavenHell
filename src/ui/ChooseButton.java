package ui;

import utilz.LoadSave;

import static utilz.Constants.Environment.chooseCharacter.*;
import static utilz.Constants.Environment.pauseOverlay.PLAYANDHOMEBUTTON_HEIGHT;
import static utilz.Constants.Environment.pauseOverlay.PLAYANDHOMEBUTTON_WIDTH;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ChooseButton extends Button {

    private BufferedImage[] chooseButton;
    private int index;
    private boolean mouseOver, mousePressed;


    public ChooseButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage imgs = LoadSave.GetSpriteAtlas(LoadSave.CHOOSE_BUTTON);
        chooseButton = new BufferedImage[3];
        for (int i = 0; i < chooseButton.length; i++)
            chooseButton[i] = imgs.getSubimage(i * CHOOSEB_DEFAULT_WIDTH, 0, CHOOSEB_DEFAULT_WIDTH, CHOOSEB_DEFAULT_HEIGHT);
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    public void draw(Graphics g) {
        g.drawImage(chooseButton[index], x, y, CHOOSEB_WIDTH, CHOOSEB_HEIGHT, null);
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
