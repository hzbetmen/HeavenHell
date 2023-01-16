package utilz;

import main.Game;

import java.awt.geom.Rectangle2D;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData) && !IsSolid(x + width, y + height, lvlData) && !IsSolid(x + width, y, lvlData) && !IsSolid(x, y + height, lvlData)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {

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

    public static float GetEntityXPosNextToWall(float xSpeed, Rectangle2D.Float hitbox) {
        int currentTile = (int) hitbox.x / Game.TILES_SIZE;
        int tileXPos = currentTile * Game.TILES_SIZE;

        if (xSpeed > 0) {
            //Right
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else {
            //Left
            return tileXPos;
        }
    }

    public static boolean EntityIsOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;
        return true;
    }

    public static float GetEntityYPosBelowRoofOrAboveFloor(float airSpeed, Rectangle2D.Float hitbox) {
        int currentTile = (int) hitbox.y / Game.TILES_SIZE;
        int tileYPos = currentTile * Game.TILES_SIZE;

        if (airSpeed > 0) {
            //Falling - Hit Floor
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            //Jumping - Hit Roof
            return tileYPos;
        }
    }



}