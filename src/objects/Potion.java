package objects;

import main.Game;

public class Potion extends GameObject {

    public Potion(int x, int y, int objType){
        super(x, y, objType);
        doAnimation = true;
        initHitbox(7,14);
        xDrawOffSet = (int)(3 * Game.SCALE);
        yDrawOffSet = (int)(2 * Game.SCALE);
    }

    public void update(){
        updateAnimationTick();
    }
}