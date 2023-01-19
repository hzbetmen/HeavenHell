package levels;

import gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int levelIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        importLevelSprite();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage img : allLevels)
            levels.add(new Level(img));
    }

    private void importLevelSprite() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[16];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                levelSprite[i * 4 + j] = img.getSubimage(j * Game.TILES_DEFAULT_SIZE, i * Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE); // Every block of terrain is a tile
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ONE_BACKGROUND);
        Image bg = img.getScaledInstance(Game.GAME_WIDTH, Game.GAME_HEIGHT, Image.SCALE_DEFAULT);
        g.drawImage(bg, 0, 0, null);

        for (int i = 0; i < Game.TILES_IN_HEIGHT; i++) {
            for (int j = 0; j < levels.get(levelIndex).getLevelData()[0].length; j++) {
                int index = levels.get(levelIndex).getSpriteIndex(j, i);
                g.drawImage(levelSprite[index], j * Game.TILES_SIZE - xLvlOffset, i * Game.TILES_SIZE, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }

    }

    public void update() {

    }

    public int GetAmountOfLevels() {
        return levels.size();
    }

    public Level getCurrentLevel() {
        return levels.get(levelIndex);
    }

    public void loadNextLevel() {
        levelIndex++;
        if (levelIndex >= levels.size()) {
            levelIndex = 0;
            System.out.println("No more levels!");
            Gamestate.state = Gamestate.MENU;
        }

        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
    }
}