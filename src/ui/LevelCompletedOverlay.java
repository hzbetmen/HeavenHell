package ui;

import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class LevelCompletedOverlay {

    private Playing playing;
    private String nextLevel = "Press enter to move to next level!";

    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.drawString(nextLevel, Game.GAME_WIDTH/2 - nextLevel.length()/2, Game.GAME_HEIGHT/2);
    }

    public void update() {

    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            playing.loadNextLvl();
    }


}
