package ui;

import gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;

import static utilz.LoadSave.*;
import static utilz.Constants.UI.Buttons.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.LinkedTransferQueue;


public class MenuButton extends Button {
    private int rowIndex, index;
    private int xOffsetCenter = B_WIDTH / 2;
    private Gamestate state;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;

    public MenuButton(int x, int y, int rowIndex, Gamestate state) {
        super(x - B_WIDTH/2, y, B_WIDTH, B_HEIGHT);
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBounds();

    }

    private void initBounds() {
        bounds = new Rectangle(x, y, B_WIDTH, B_HEIGHT);
    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect(bounds.x, bound.y, bound.width, bound.height);
    }

    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[index], x, y, B_WIDTH, B_HEIGHT - 3, null);
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;

    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void applyGamestate() {
        Gamestate.state = state;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;

    }
}