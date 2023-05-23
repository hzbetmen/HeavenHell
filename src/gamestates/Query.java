package gamestates;

import main.Game;
import ui.QueryButton;
import utilz.LoadSave;

import static utilz.Constants.Environment.queryButton.*;
import static utilz.HelpMethods.isIn;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Query extends State implements Statemethods {
    private QueryButton buttonA, buttonB, buttonC, buttonD;
    private QueryButton[] queryButtons;
    private BufferedImage jesusImg, backgroundQuery, question, endGameText, finalScene;
    private boolean answerCorrect = false;

    public Query(Game game) {
        super(game);
        loadImgs();
        createAnswerButtons();
    }

    private void loadImgs() {
        jesusImg = LoadSave.GetSpriteAtlas(LoadSave.JESUS_ATLAS);
        backgroundQuery = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_QUERY);
        question = LoadSave.GetSpriteAtlas(LoadSave.QUESTION);
        endGameText = LoadSave.GetSpriteAtlas(LoadSave.END_GAME_TEXT);
        finalScene = LoadSave.GetSpriteAtlas(LoadSave.FINAL);
    }

    private void createAnswerButtons() {
        int buttonACX = (int) (450 * Game.SCALE); //400
        int buttonBDX = (int) (620 * Game.SCALE);
        int buttonABY = (int) (280 * Game.SCALE);
        int buttonCDY = (int) (350 * Game.SCALE);

        buttonA = new QueryButton(buttonACX, buttonABY, ANSWERBUTTON_WIDTH, ANSWERBUTTON_HEIGHT, "A");
        buttonB = new QueryButton(buttonBDX, buttonABY, ANSWERBUTTON_WIDTH, ANSWERBUTTON_HEIGHT, "B");
        buttonC = new QueryButton(buttonACX, buttonCDY, ANSWERBUTTON_WIDTH, ANSWERBUTTON_HEIGHT, "C");
        buttonD = new QueryButton(buttonBDX, buttonCDY, ANSWERBUTTON_WIDTH, ANSWERBUTTON_HEIGHT, "D");

        queryButtons = new QueryButton[4];
        queryButtons[0] = buttonA;
        queryButtons[1] = buttonB;
        queryButtons[2] = buttonC;
        queryButtons[3] = buttonD;
    }

    @Override
    public void update() {
        for (QueryButton button : queryButtons)
            button.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundQuery, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        if (answerCorrect)
            g.drawImage(finalScene, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        if (!answerCorrect) {
            g.drawImage(question, (int) (650 / 1.5 * Game.SCALE), (int) (50 / 1.5 * Game.SCALE), (int) (350 * Game.SCALE), (int) (200 * Game.SCALE), null);
            //g.drawImage(jesusImg, 700, 30, 1300/5*2, 1500/5*2, null);

            for (QueryButton button : queryButtons) {
                button.draw(g);
                //button.drawHitbox(g);
            }
        } else {
            g.drawImage(endGameText, (int) (180 * Game.SCALE), (int) (180 * Game.SCALE), (int) (455 * Game.SCALE), (int) (65 * Game.SCALE), null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (QueryButton button : queryButtons)
            if (isIn(e, button)) {
                button.setMousePressed(true);
                break;
            }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (QueryButton button : queryButtons)
            if (isIn(e, button))
                if (button.isMousePressed()) {

                }
        if (isIn(e, buttonA)) {
            if (buttonA.isMousePressed()) {
                answerCorrect = true;
            }
        } else if (isIn(e, buttonB)) {
            System.out.println("Answer Wrong!");
        } else if (isIn(e, buttonC)) {
            System.out.println("Answer Wrong!");
        } else if (isIn(e, buttonD)) {
            System.out.println("Answer Wrong!");
        }

        for (QueryButton button : queryButtons)
            button.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (QueryButton button : queryButtons)
            button.setMouseOver(false);

        for (QueryButton button : queryButtons)
            if (isIn(e, button)) {
                button.setMouseOver(true);
                break;
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (answerCorrect)
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                answerCorrect = false;
                Gamestate.state = Gamestate.MENU;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
