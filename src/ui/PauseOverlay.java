package ui;

import gamestates.Playing;
import main.Game;
import static utilz.Constants.Environment.pauseOverlay.*;


import java.awt.*;
import java.awt.event.MouseEvent;

public class PauseOverlay {

    private Playing playing;
    private String pauseOverlay = "PAUSE";

    private PlayButton playButton;

    public PauseOverlay(Playing pLaying) {
        this.playing = pLaying;
        createPlayButton();
    }

    private void createPlayButton() {
        int playX = (int) (550 * Game.SCALE);
        int playY = (int) (120 * Game.SCALE);

        playButton = new PlayButton(playX, playY, PLAYBUTTON_WIDTH, PLAYBUTTON_HEIGHT, 0);
    }

    public void update() {
        playButton.update();

    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.magenta);
        g.drawString(pauseOverlay, Game.GAME_WIDTH/2 - pauseOverlay.length()/2 - (int) (2 * Game.SCALE), 100);

        playButton.draw(g);
    }

    public void mouseMoved(MouseEvent e) {
        playButton.setMouseOver(false);

        if (isIn(e, playButton))
            playButton.setMouseOver(true);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, playButton))
            playButton.setMousePressed(true);

    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, playButton))
            if (playButton.isMousePressed())
                playing.unpausedGame(false);

        playButton.resetBools();
    }

    private boolean isIn(MouseEvent e, PauseButton pauseButton) {
        return pauseButton.getBound().contains(e.getX(),e.getY());
    }
}
