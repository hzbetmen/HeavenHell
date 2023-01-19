package levels;

import main.Game;
import utilz.LoadSave;
import static utilz.HelpMethods.*;

import java.awt.image.BufferedImage;

import static main.Game.TILES_SIZE;

public class Level {
    private int[][] levelData;
    private BufferedImage img;

    //lvlOffset
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffset;


    public Level(BufferedImage img) {
        this.img = img;
        createLvlData();
        calcLvlOffset();
    }

    private void calcLvlOffset() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffset = maxTilesOffset * TILES_SIZE;
    }

    private void createLvlData() {
        levelData = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y) {
        return levelData[y][x];
    }

    public int[][] getLevelData() {
        return levelData;
    }

    public int getLvlOffset() {
        return maxLvlOffset;
    }
}