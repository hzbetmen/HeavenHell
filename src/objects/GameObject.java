package objects;

import java.awt.*;

import static utilz.Constants.ObjectConstants.*;

import java.awt.geom.Rectangle2D;

public class GameObject {
    protected int x, y, objType;
    protected Rectangle2D.Float hitBox;
    protected boolean doAnimation, active = true;
    protected int aniTick, aniIndex;
    protected int xDrawOffSet, yDrawOffSet;

    public GameObject(int x, int y, int objType) {
        this.x = x;
        this.y = y;
        this.objType = objType;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= 30) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(objType)) {
                aniIndex = 0;

            }
        }
    }

    protected void reset() {
        aniIndex = 0;
        aniTick = 0;
        active = true;
    }

    protected void initHitbox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, width, height);
    }


    public int getObjType() {
        return objType;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getxDrawOffSet() {
        return xDrawOffSet;
    }

    public int getyDrawOffSet() {
        return yDrawOffSet;
    }

    public int getAniIndex() {
        return aniIndex;
    }


}