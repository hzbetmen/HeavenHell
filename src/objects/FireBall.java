package objects;

import main.Game;

import static utilz.Constants.ObjectConstants.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class FireBall{

    private Rectangle2D.Float hitBox;
    private int dir, rowIndex;
    private int flipX = 0;
    private boolean active = true;
    private int yOffsetHitbox = (int) (20 * Game.SCALE);

    public FireBall(int x, int y, int dir, int rowIndex) {
        hitBox = new Rectangle2D.Float(x, y, FIRE_BALL_WIDTH, FIRE_BALL_HEIGHT - yOffsetHitbox);
        this.dir = dir;
        if (dir < 0)
            this.flipX = FIRE_BALL_WIDTH;
        this.rowIndex = rowIndex;
    }

    public void updatePos() {
        hitBox.x += dir * SPEED;
    }

    public void setPos(int x, int y) {
        hitBox.x = x;
        hitBox.y = y;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.blue);
        g.drawRect((int) hitBox.x - xLvlOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    public int getDir() {
        return dir;
    }

    public int getFlipX() {
        return flipX;
    }
}
