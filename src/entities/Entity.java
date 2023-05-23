package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitBox;
    public Entity(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        initHitbox(x, y, width, height);
    }
    protected void initHitbox(float x, float y, int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, width, height);

    }
    protected void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.green);
        g.drawRect((int) hitBox.x - xLvlOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }
    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }
}