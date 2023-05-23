package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.Environment.pauseOverlay.PLAYANDHOMEBUTTON_HEIGHT;
import static utilz.Constants.Environment.pauseOverlay.PLAYANDHOMEBUTTON_WIDTH;
import static utilz.HelpMethods.isIn;

public class LevelCompletedOverlay {
    private PlayHomeReplayButton nextLevelButton;
    private PlayHomeReplayButton homeButton;

    private Playing playing;
    private BufferedImage levelCompletedText;
    private int levelCompletedTextW, levelCompletedTextH, levelCompletedTextX, levelCompletedTextY;

    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        createHomeOrNextLevelButtons();
        loadLevelCompletedText();
    }

    private void loadLevelCompletedText() {
        levelCompletedText = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_COMPLETED_TEXT);
        levelCompletedTextW = (int) (310 * Game.SCALE);
        levelCompletedTextH = (int) (60 * Game.SCALE);
        levelCompletedTextX = Game.GAME_WIDTH/2 - levelCompletedTextW/2 - (int) (5*Game.SCALE);
        levelCompletedTextY = (int) (33 * Game.SCALE);
    }

    private void createHomeOrNextLevelButtons() {
        int playX = (int) (550 * Game.SCALE);
        int homeX = (int) (120 * Game.SCALE);
        int Y = (int) (130 * Game.SCALE);

        nextLevelButton = new PlayHomeReplayButton(playX, Y, PLAYANDHOMEBUTTON_WIDTH, PLAYANDHOMEBUTTON_HEIGHT, 0);
        homeButton = new PlayHomeReplayButton(homeX, Y, PLAYANDHOMEBUTTON_WIDTH, PLAYANDHOMEBUTTON_HEIGHT, 1);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(levelCompletedText,levelCompletedTextX, levelCompletedTextY, levelCompletedTextW, levelCompletedTextH, null);

        nextLevelButton.draw(g);
        homeButton.draw(g);
    }

    public void update() {
        nextLevelButton.update();
        homeButton.update();
    }

    public void mouseMoved(MouseEvent e) {
        nextLevelButton.setMouseOver(false);
        homeButton.setMouseOver(false);

        if (isIn(e, nextLevelButton))
            nextLevelButton.setMouseOver(true);
        else if (isIn(e, homeButton))
            homeButton.setMouseOver(true);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, nextLevelButton))
            nextLevelButton.setMousePressed(true);
        else if (isIn(e, homeButton))
            homeButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, nextLevelButton)) {
            if (nextLevelButton.isMousePressed())
                playing.loadNextLvl();
        } else if (isIn(e, homeButton))
            if (homeButton.isMousePressed()) {
                playing.resetAll();
                playing.getLevelManager().setLevelIndex(0);
                playing.choosingCharacterDone(false);
                Gamestate.state = Gamestate.MENU;
            }

        nextLevelButton.resetBools();
        homeButton.resetBools();
    }

}
