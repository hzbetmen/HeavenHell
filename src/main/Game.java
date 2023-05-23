package main;


import gamestates.*;
import gamestates.Menu;
import ui.ChoosingCharacter;

import java.awt.*;
import java.awt.event.KeyListener;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;

    private Menu menu;
    private Query query;
    private Option option;

    private ChoosingCharacter choosingCharacter;


    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Game() {

        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        startGameLoop();


    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
        option = new Option(this);
        query = new Query(this);
    }


    public void render(Graphics g) {
        switch (Gamestate.state) {
            case MENU -> menu.draw(g);
            case PLAYING -> playing.draw(g);
            case OPTION -> option.draw(g);
            case QUIT -> System.exit(0);
            case QUERY -> query.draw(g);
        }
    }

    public void update() {

        switch (Gamestate.state) {
            case MENU -> menu.update();
            case PLAYING -> playing.update();
            case OPTION -> option.update();
            case QUIT -> System.exit(0);
            case QUERY -> query.update();
        }
    }


    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING)
            playing.getPlayer().resetDirBoolean();
    }

    public Menu getMenu() {
        return menu;
    }

    public Query getQuery() {
        return query;
    }
    public Option getOption() {
        return option;
    }

    public Playing getPlaying() {
        return playing;
    }

    public ChoosingCharacter getChoosingCharacter() {
        return choosingCharacter;
    }


    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;

        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {

            long currentTime = System.nanoTime();

            deltaF += (currentTime - previousTime) / timePerFrame;
            deltaU += (currentTime - previousTime) / timePerUpdate;

            previousTime = currentTime;

            if (deltaF >= 1.0) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (deltaU >= 1.0) {
                update();
                updates++;
                deltaU--;
            }


            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                //System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }

        }

    }

}