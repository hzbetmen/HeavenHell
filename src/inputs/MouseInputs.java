package inputs;

import gamestates.Gamestate;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING -> GamePanel.getGame().getPlaying().mouseMoved(e);
            case MENU -> GamePanel.getGame().getMenu().mouseMoved(e);
            case QUERY -> gamePanel.getGame().getQuery().mouseMoved(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING -> GamePanel.getGame().getPlaying().mouseClicked(e);
            case MENU -> GamePanel.getGame().getMenu().mouseClicked(e);
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING -> GamePanel.getGame().getPlaying().mousePressed(e);
            case MENU -> GamePanel.getGame().getMenu().mousePressed(e);
            case QUERY -> gamePanel.getGame().getQuery().mousePressed(e);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING -> GamePanel.getGame().getPlaying().mouseReleased(e);
            case MENU -> GamePanel.getGame().getMenu().mouseReleased(e);
            case QUERY -> gamePanel.getGame().getQuery().mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
