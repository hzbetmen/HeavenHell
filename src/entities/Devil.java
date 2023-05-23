package entities;

import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;

public class Devil extends Enemy {

    private Rectangle2D.Float attackBox;
    protected int attackBoxOffSetX;
    private int startWalkDir;

    public Devil(float x, float y) {
        super(x, y, DEVIL_WIDTH, DEVIL_HEIGHT, DEVIL);
        initHitbox(x, y, (int) (64 * Game.SCALE), (int) (64 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, DEVIL_WIDTH + (int) (0 * Game.SCALE), DEVIL_HEIGHT);
        attackBoxOffSetX = (int) (Game.SCALE * 33);
    }

    public void update(int[][] lvlData, Playing playing) {
        updateBehaviour(lvlData, playing);
        updateAnimationTick(DEVIL);
        updateHitbox();
        updateAttackBox();
    }

    private void updateHitbox() {
        if (startWalkDir != walkDir) {
            if (walkDir == RIGHT)
                hitBox.x -= hitBox.width / 2;
            else {
                hitBox.x += hitBox.width / 2;
            }
        }

        startWalkDir = walkDir;
    }

    private void updateAttackBox() {
        if (walkDir == RIGHT)
            attackBox.x = hitBox.x - attackBoxOffSetX;
        else {
            attackBox.x = hitBox.x + attackBoxOffSetX;
        }
        attackBox.y = hitBox.y;
    }

    private void updateBehaviour(int[][] lvlData, Playing playing) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        if (inAir) {
            updateInAir(lvlData, DEVIL);
        } else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canDevilSeePlayer(lvlData, playing.getPlayer())) {
                        turnTowardsPlayer(playing.getPlayer());
                        if (isPlayerCloseForAttack(playing.getPlayer(), DEVIL)) {
                            newState(ATTACK);
                            System.out.println("Devil Y: " + hitBox.y);
                        }
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    if (aniIndex == 0) {
                        attackChecked = false;
                    }
                    if (aniIndex == 3 && !attackChecked) {
                        checkEnemyHit(attackBox, playing.getPlayer());
                    }
                    break;
                case HIT:
                    break;
            }
        }
    }

    public void drawAttackBox(Graphics g, int xLvlOffSet) {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLvlOffSet), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public int flipX() {
        if (walkDir == RIGHT) {
            return DEVIL_DRAW_WIDTH / 2;
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
