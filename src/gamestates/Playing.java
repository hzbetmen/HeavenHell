package gamestates;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.ChoosingCharacter;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static main.Game.TILES_SIZE;

public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;

    private BufferedImage backgroundImg;

    //ChoosePlayer
    private ChoosingCharacter choosingCharacter;
    private boolean choosingCharacterDone = false;

    //Enemy
    private EnemyManager enemyManager;

    //pause
    private boolean paused = false;
    private PauseOverlay pauseOverlay;

    private int xLvlOffset = 0;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);


    //Complete level
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean levelCompleted = false;
    private int maxLvlOffset;
    private ObjectManager objectManager;

    //GameOver
    private GameOverOverlay gameOverOverlay;
    private boolean gameOver;


    public Playing(Game game) {
        super(game);
        initClasses();

        loadBackground();
        calcLvlOffset();
        loadStartLvl();
    }

    private void loadStartLvl() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_PLAYING);
    }

    private void calcLvlOffset() {
        maxLvlOffset = levelManager.getCurrentLevel().getLvlOffset();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        player = new Player(5 * TILES_SIZE, 5 * TILES_SIZE, (int) (32 * Game.SCALE), (int) (32 * Game.SCALE), this); // xpos yPos height width
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
        pauseOverlay = new PauseOverlay(this);
        choosingCharacter = new ChoosingCharacter(this);
    }

    @Override
    public void update() {
        if (!choosingCharacterDone)
            choosingCharacter.update();
        else if (gameOver) {
            gameOverOverlay.update();
        } else if (paused)
            pauseOverlay.update();
        else if (levelCompleted)
            levelCompletedOverlay.update();
        else {
            levelManager.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            objectManager.update(levelManager.getCurrentLevel().getLevelData(), player, enemyManager);
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
        if (!choosingCharacterDone)
            choosingCharacter.draw(g);
        else {
            g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

            //Level
            levelManager.draw(g, xLvlOffset);

            //Enemy
            enemyManager.draw(g, xLvlOffset);

            //Player
            player.render(g, xLvlOffset);

            //Object
            objectManager.draw(g, xLvlOffset);

            if (paused)
                pauseOverlay.draw(g);
            else if (levelCompleted)
                levelCompletedOverlay.draw(g);
            else if (gameOver) {
                gameOverOverlay.draw(g);
            }

            if ((player.getHitBox().x >= 0 && player.getHitBox().x <= 32) && (player.getHitBox().y >= 0 && player.getHitBox().y <= 32)) {
                drawJustOnce(g);
                if (player.getHitBox().x < 50) {
                }
            }
        }
    }

    private void drawJustOnce(Graphics g) {
        g.drawImage(LoadSave.GetSpriteAtlas(LoadSave.GO_BACK), Game.TILES_IN_WIDTH / 2 * Game.TILES_DEFAULT_SIZE, Game.TILES_IN_HEIGHT / 2 * Game.TILES_DEFAULT_SIZE, TILES_SIZE * 4, TILES_SIZE * 4, null);
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void checkPotionTouched(Rectangle2D.Float hitbox) {
        objectManager.checkPotionsTouched(hitbox);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!choosingCharacterDone)
            choosingCharacter.mousePressed(e);
        else if (paused)
            pauseOverlay.mousePressed(e);
        else if (levelCompleted)
            levelCompletedOverlay.mousePressed(e);
        else if (gameOver)
            gameOverOverlay.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!choosingCharacterDone)
            choosingCharacter.mouseReleased(e);
        else if (paused)
            pauseOverlay.mouseReleased(e);
        else if (levelCompleted)
            levelCompletedOverlay.mouseReleased(e);
        else if (gameOver)
            gameOverOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!choosingCharacterDone)
            choosingCharacter.mouseMoved(e);
        else if (paused)
            pauseOverlay.mouseMoved(e);
        else if (levelCompleted)
            levelCompletedOverlay.mouseMoved(e);
        else if (gameOver)
            gameOverOverlay.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setJump(true);
            case KeyEvent.VK_A -> {
                player.setLeft(true);
                player.faceLeft(true);
                player.faceRight(false);
            }
            case KeyEvent.VK_D -> {
                player.setRight(true);
                player.faceLeft(false);
                player.faceRight(true);
            }
            case KeyEvent.VK_K -> player.setAttacking(true);
            case KeyEvent.VK_L -> {
                if (!player.fireBallIsOnCooldown())
                    player.activeSkillFireBall(true);
            }

            case KeyEvent.VK_ESCAPE -> paused = true;
            case KeyEvent.VK_N -> levelCompleted = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setJump(false);
            case KeyEvent.VK_A -> player.setLeft(false);
            case KeyEvent.VK_D -> player.setRight(false);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.resetDirBoolean();
    }

    public void choosingCharacterDone(boolean choosingCharacterDone) {
        this.choosingCharacterDone = choosingCharacterDone;
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffset = lvlOffset;
    }

    public void resetAll() {
        gameOver = false;
        paused = false;
        levelCompleted = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }

    public void loadNextLvl() {
        resetAll();
        levelManager.loadNextLevel();
    }

    public void unPausedGame(boolean paused) {
        this.paused = !paused;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }
}
