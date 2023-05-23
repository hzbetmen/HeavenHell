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

public class GameOverOverlay {

    private Playing playing;
    private PlayHomeReplayButton replayButton;
    private PlayHomeReplayButton homeButton;
    private BufferedImage gameOverText = LoadSave.GetSpriteAtlas(LoadSave.GAME_OVER);

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        createReplayAndHomeButtons();
    }

    private void createReplayAndHomeButtons() {
        int playX = (int) (550 * Game.SCALE);
        int homeX = (int) (120 * Game.SCALE);
        int Y = (int) (130 * Game.SCALE);

        replayButton = new PlayHomeReplayButton(playX, Y, PLAYANDHOMEBUTTON_WIDTH, PLAYANDHOMEBUTTON_HEIGHT, 2);
        homeButton = new PlayHomeReplayButton(homeX, Y, PLAYANDHOMEBUTTON_WIDTH, PLAYANDHOMEBUTTON_HEIGHT, 1);
    }

    public void update() {
        replayButton.update();
        homeButton.update();
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(gameOverText, Game.GAME_WIDTH/2 - gameOverText.getWidth()/4, (int) (50 * Game.SCALE), gameOverText.getWidth()/2, homeButton.getHeight()/2, null);

        replayButton.draw(g);
        homeButton.draw(g);
    }

    public void mouseMoved(MouseEvent e) {
        replayButton.setMouseOver(false);
        homeButton.setMouseOver(false);

        if (isIn(e, replayButton))
            replayButton.setMouseOver(true);
        else if (isIn(e, homeButton))
            homeButton.setMouseOver(true);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, replayButton))
            replayButton.setMousePressed(true);
        else if (isIn(e, homeButton))
            homeButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, replayButton)) {
            if (replayButton.isMousePressed()) {
                playing.resetAll();
            }
        } else if (isIn(e, homeButton))
            if (homeButton.isMousePressed()) {
                playing.resetAll();
                playing.getLevelManager().setLevelIndex(0);
                playing.choosingCharacterDone(false);
                Gamestate.state = Gamestate.MENU;
            }

        replayButton.resetBools();
        homeButton.resetBools();
    }

}
