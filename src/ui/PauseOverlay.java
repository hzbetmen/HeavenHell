package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import static utilz.Constants.Environment.pauseOverlay.*;
import static utilz.HelpMethods.isIn;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PauseOverlay {

    private Playing playing;
    private String pauseOverlay = "PAUSE";

    private PlayHomeReplayButton playButton;
    private PlayHomeReplayButton homeButton;
    private BufferedImage pausedText;
    private int pausedTextW, pausedTextH, pausedTextX, pausedTextY;

    public PauseOverlay(Playing pLaying) {
        this.playing = pLaying;
        createPlayAndHomeButtons();
        loadPausedText();
    }

    private void loadPausedText() {
        pausedText = LoadSave.GetSpriteAtlas(LoadSave.PAUSED_TEXT);
        pausedTextW = (int) (145 * Game.SCALE);
        pausedTextH = (int) (60 * Game.SCALE);
        pausedTextX = Game.GAME_WIDTH/2 - pausedTextW/2 - (int) (5*Game.SCALE);
        pausedTextY = (int) (33 * Game.SCALE);
    }

    private void createPlayAndHomeButtons() {
        int playX = (int) (550 * Game.SCALE);
        int homeX = (int) (120 * Game.SCALE);
        int Y = (int) (130 * Game.SCALE);

        playButton = new PlayHomeReplayButton(playX, Y, PLAYANDHOMEBUTTON_WIDTH, PLAYANDHOMEBUTTON_HEIGHT, 0);
        homeButton = new PlayHomeReplayButton(homeX, Y, PLAYANDHOMEBUTTON_WIDTH, PLAYANDHOMEBUTTON_HEIGHT, 1);

    }

    public void update() {
        playButton.update();
        homeButton.update();
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(pausedText,pausedTextX, pausedTextY, pausedTextW, pausedTextH, null);

        playButton.draw(g);
        homeButton.draw(g);
    }

    public void mouseMoved(MouseEvent e) {
        playButton.setMouseOver(false);
        homeButton.setMouseOver(false);

        if (isIn(e, playButton))
            playButton.setMouseOver(true);
        else if (isIn(e, homeButton))
            homeButton.setMouseOver(true);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, playButton))
            playButton.setMousePressed(true);
        else if (isIn(e, homeButton))
            homeButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, playButton)) {
            if (playButton.isMousePressed())
                playing.unPausedGame(true);
        } else if (isIn(e, homeButton))
            if (homeButton.isMousePressed()) {
                playing.resetAll();
                playing.getLevelManager().setLevelIndex(0);
                playing.choosingCharacterDone(false);
                Gamestate.state = Gamestate.MENU;
            }

        playButton.resetBools();
        homeButton.resetBools();
    }

}
