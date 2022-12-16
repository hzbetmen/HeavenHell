package utilz;

import main.Game;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!isSolid(x, y, lvlData) && !isSolid(x + width, y + height, lvlData) && !isSolid(x + width, y, lvlData) && !isSolid(x, y + height, lvlData)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isSolid(float x, float y, int[][] lvlData) {

        if (x < 0 || x >= Game.GAME_WIDTH)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;
        int value = lvlData[(int) yIndex][(int) xIndex];

        if (value == 3 || value == 7 || value == 11) {
            return false;
        } else return true;
    }

}
