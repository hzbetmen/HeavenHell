package gamestates;

import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.LevelCompletedOverlay;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static main.Game.TILES_SIZE;

public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;

    private int xLvlOffset = 0;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);


    //Complete level
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean levelCompleted = false;
    private int maxLvlOffset;


    public Playing(Game game) {
        super(game);
        initClasses();

        calcLvlOffset();
    }

    private void calcLvlOffset() {
        maxLvlOffset = levelManager.getCurrentLevel().getLvlOffset();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        player = new Player(5 * TILES_SIZE, 5 * TILES_SIZE, (int) (32 * Game.SCALE), (int) (32 * Game.SCALE)); // xpos yPos height width
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        levelCompletedOverlay = new LevelCompletedOverlay(this);
    }

    @Override
    public void update() {
        if (levelCompleted)
            levelCompletedOverlay.update();
        else {
            levelManager.update();
            player.update();
            checkCloseToBorder();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitBox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if (xLvlOffset >= maxLvlOffset)
            xLvlOffset = maxLvlOffset;
        else if (xLvlOffset <= 0)
            xLvlOffset = 0;

    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);

        if (levelCompleted)
            levelCompletedOverlay.draw(g);

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
        if (levelCompleted)
            levelCompletedOverlay.keyPressed(e);
        else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> player.setJump(true);
                case KeyEvent.VK_A -> player.setLeft(true);
                case KeyEvent.VK_D -> player.setRight(true);
                case KeyEvent.VK_J -> player.setAttacking(true);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (levelCompleted)
            levelCompletedOverlay.keyReleased(e);
        else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> player.setJump(false);
                case KeyEvent.VK_A -> player.setLeft(false);
                case KeyEvent.VK_D -> player.setRight(false);
                case KeyEvent.VK_ESCAPE -> Gamestate.state = Gamestate.MENU;
                case KeyEvent.VK_N -> levelCompleted = true;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.resetDirBoolean();
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffset = lvlOffset;
    }

    public void resetAll() {
        levelCompleted = false;
        player.resetAll();
    }

    public void loadNextLvl() {
        resetAll();
        levelManager.loadNextLevel();
    }
}
