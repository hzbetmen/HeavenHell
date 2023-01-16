package gamestates;

import entities.Player;
import levels.LevelManager;
import main.Game;

import javax.swing.undo.StateEdit;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static main.Game.TILES_SIZE;

public class Playing extends State implements Statemethods{
    private Player player;
    private LevelManager levelManager;

    public Playing(Game game) {
        super(game);
        initClasses();


    }
    private void initClasses() {
        levelManager = new LevelManager(game);
        player = new Player(5*TILES_SIZE, 5*TILES_SIZE, (int) (32 * Game.SCALE), (int) (32 * Game.SCALE)); // xpos yPos height width
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
    }

    @Override
    public void update() {
        levelManager.update();
        player.update();

    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        player.render(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setJump(true);
            case KeyEvent.VK_A -> player.setLeft(true);
            case KeyEvent.VK_D -> player.setRight(true);
            case KeyEvent.VK_J -> player.setAttacking(true);
            case KeyEvent.VK_BACK_SPACE -> Gamestate.state = Gamestate.MENU;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setJump(false);
            case KeyEvent.VK_A -> player.setLeft(false);
            case KeyEvent.VK_D -> player.setRight(false);
            //case KeyEvent.VK_J -> player.setAttacking(false); not setting stop atk here cauze it will stop the animation midway as the time between press and release is smaller than the the time between changing animations
            // Break in between each case?
        }

    } public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.resetDirBoolean();
    }
}
