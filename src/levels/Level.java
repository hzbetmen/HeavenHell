package levels;

import entities.Devil;
import entities.Skeleton;
import main.Game;
import objects.Potion;

import static utilz.HelpMethods.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.Game.TILES_SIZE;

public class Level {
    private int[][] levelData;
    private BufferedImage img;

    //lvlOffset
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffset;
    private ArrayList<Potion> potions;

    //enemies
    private ArrayList<Skeleton> skeletons;
    private ArrayList<Devil> devils;


    public Level(BufferedImage img) {
        this.img = img;
        createLvlData();
        createEnemies();
        createPotions();
        calcLvlOffset();
    }

    private void createEnemies() {
        skeletons = GetSkeleton(img);
        devils = GetDevils(img);
    }

    private void createPotions() {
        potions = GetPotions(img);
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


    public ArrayList<Skeleton> getSkeletons() {
        return skeletons;
    }

    public ArrayList<Devil> getDevils() {
        return devils;
    }

    public ArrayList<Potion> getPotions() {
        return potions;
    }
}