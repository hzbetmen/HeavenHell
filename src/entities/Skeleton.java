package entities;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;

public class Skeleton extends Enemy{

    private Rectangle2D.Float attackBox;
    private int attackBoxOffSetX;

    public Skeleton(float x, float y) {
        super(x, y, SKELETON_WIDTH,SKELETON_HEIGHT,SKELETON);
        initHitbox(x,y,(int)(17 * Game.SCALE),(int)(24 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,(int)(33 * Game.SCALE),(int)(24 * Game.SCALE));
        attackBoxOffSetX = (int) (Game.SCALE * 16);
    }

    public void update(int[][] lvlData,Player player) {
        updateBehaviour(lvlData, player);
        updateAnimationTick(SKELETON);
        updateAttackBox();
    }

    private void updateAttackBox() {
        if (walkDir == RIGHT)
            attackBox.x = hitBox.x - attackBoxOffSetX;
        else {
            attackBox.x = hitBox.x;
        }
        attackBox.y = hitBox.y;
    }

    private void updateBehaviour(int[][] lvlData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        if (inAir) {
            updateInAir(lvlData, SKELETON);
        } else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(lvlData,player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player, SKELETON)) {
                            newState(ATTACK);
                        }
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    if (aniIndex == 0) {
                        attackChecked = false;
                    }
                    if (aniIndex == 7 && !attackChecked) {
                        checkEnemyHit(attackBox,player);
                    }
                    break;
                case HIT:
                    break;
            }
        }
    }

    public void drawAttackBox(Graphics g, int xLvlOffSet) {
        g.setColor(Color.red);
        g.drawRect((int)(attackBox.x - xLvlOffSet),(int)attackBox.y,(int)attackBox.width,(int)attackBox.height);
    }
    public int flipX() {
        if (walkDir == RIGHT) {
            return 32;
        } else {
            return 0;
        }
    }
    public int flipW() {
        if (walkDir == RIGHT) {
            return -1;
        } else {
            return 1;
        }
    }
}
