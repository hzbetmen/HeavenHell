package ui;

import gamestates.Gamestate;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.Environment.queryButton.*;

public class QueryButton extends Button {

    private BufferedImage[] answerButtons;
    private int index;
    private boolean mouseOver, mousePressed;
    private String ABCD;


    public QueryButton(int x, int y, int width, int height, String ABCD) {
        super(x, y, width, height);
        this.ABCD = ABCD;
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage imgs = null;
        if (ABCD.equalsIgnoreCase("A"))
            imgs = LoadSave.GetSpriteAtlas(LoadSave.ANSWER_A);
        else if (ABCD.equalsIgnoreCase("B")) {
            imgs = LoadSave.GetSpriteAtlas(LoadSave.ANSWER_B);
        } else if (ABCD.equalsIgnoreCase("C")) {
            imgs = LoadSave.GetSpriteAtlas(LoadSave.ANSWER_C);
        } else if (ABCD.equalsIgnoreCase("D")) {
            imgs = LoadSave.GetSpriteAtlas(LoadSave.ANSWER_D);
        }

        answerButtons = new BufferedImage[3];
        for (int i = 0; i < answerButtons.length; i++)
            answerButtons[i] = imgs.getSubimage(i * ANSWERBUTTON_DEFAULT_WIDTH, 0, ANSWERBUTTON_DEFAULT_WIDTH, ANSWERBUTTON_DEFAULT_HEIGHT);
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    public void draw(Graphics g) {
        g.drawImage(answerButtons[index], x, y, ANSWERBUTTON_WIDTH, ANSWERBUTTON_HEIGHT, null);
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
