package levels;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        //levelSprite = LoadSave.GetPlayerAtlas(LoadSave.LEVEL_ATLAS);
        importLevelSprite();
        levelOne = new Level(LoadSave.GetLevelData());
    }

    private void importLevelSprite() {
        BufferedImage img = LoadSave.GetPlayerAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[16];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                levelSprite[i * 4 + j] = img.getSubimage(j * Game.TILES_DEFAULT_SIZE, i * Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE); // Every block of terrain is a tile
            }
        }
    }

    public void draw(Graphics g) {
        BufferedImage img = LoadSave.GetPlayerAtlas(LoadSave.LEVEL_ONE_BACKGROUND);
        Image bg = img.getScaledInstance(Game.GAME_WIDTH, Game.GAME_HEIGHT, Image.SCALE_DEFAULT);
        g.drawImage(bg, 0, 0, null);

        for (int i = 0; i < Game.TILES_IN_HEIGHT; i++) {
            for (int j = 0; j < Game.TILES_IN_WIDTH; j++) {
                int index = levelOne.getSpriteIndex(j, i);
                g.drawImage(levelSprite[index], j * Game.TILES_SIZE, i * Game.TILES_SIZE, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }

    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levelOne;
    }

}