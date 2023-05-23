package entities;
import main.Game;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.Directions.*;
import static utilz.HelpMethods.*;

public abstract class Enemy extends Entity {
    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.5f * Game.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected int maxHealth;
    protected int currentHealth;
    protected boolean active = true;
    protected boolean attackChecked;
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x,y,width,height);
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
    }

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!EntityIsOnFloor(hitBox,lvlData)) {
            inAir = true;
            firstUpdate = false;
        }
    }

    protected void updateInAir(int[][] lvlData, int skeletonOrDevil) {
        if (CanMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height,lvlData)) {
            hitBox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            if (skeletonOrDevil == SKELETON)
                hitBox.y = GetEntityYPosBelowRoofOrAboveFloor(fallSpeed, hitBox, false);
            else if (skeletonOrDevil == DEVIL)
                hitBox.y = GetEntityYPosBelowRoofOrAboveFloor(fallSpeed, hitBox, true);

            tileY = (int) (hitBox.y / Game.TILES_SIZE);
            inAir = false;
        }
    }

    protected void move(int[][] lvlData) {
        float xSpeed = 0;
        if (walkDir == LEFT) {
            xSpeed = walkSpeed;
        } else {
            xSpeed = -walkSpeed;
        }
        if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height,lvlData)) {
            if (IsFloor(hitBox, xSpeed, lvlData)) {
                hitBox.x += xSpeed;
                return;
            }
        }
        changeWalkDir();
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitBox.x > hitBox.x) {
            walkDir = LEFT;
        } else {
            walkDir = RIGHT;
        }
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int)(player.getHitBox().y / Game.TILES_SIZE);
        if (playerTileY == tileY) {
            if (isPlayerInRange(player)) {
                if (IsSightClear(lvlData, hitBox, player.hitBox, tileY)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean canDevilSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int)(player.getHitBox().y / Game.TILES_SIZE);
        if (playerTileY == tileY + 2 || playerTileY == tileY + 1) {
            if (isPlayerInRange(player)) {
                if (IsSightClear(lvlData, hitBox, player.hitBox, tileY + 2)) {
                    return true;
                }
            }
        }
        return false;
    }
    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
        return absValue <= attackDistance * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player, int skeletonOrDevil) {
        int absValue = 0;
        if (walkDir == RIGHT || skeletonOrDevil == SKELETON)
            absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
        else if (walkDir == LEFT)
            absValue = (int) Math.abs(player.hitBox.x - (hitBox.x + (int) (44 * Game.SCALE)));

        return absValue < attackDistance;
    }

    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0) {
            newState(DEAD);
        } else {
            newState(HIT);
        }
    }

    protected void checkEnemyHit(Rectangle2D.Float attackBox,Player player) {
        if (attackBox.intersects(player.hitBox)) {
            player.changeHealth(-GetEnemyDmg(enemyType));
        }
        attackChecked = true;
    }
    protected void updateAnimationTick(int skeletonOrDevil) {
        aniTick++;

        switch (skeletonOrDevil) {
            case SKELETON -> aniSpeed = 15;
            case DEVIL -> aniSpeed = 25;
        }

        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType,enemyState)) {
                aniIndex = 0;
                switch (enemyState) {
                    case ATTACK,HIT -> enemyState = IDLE;
                    case DEAD -> active = false;
                }
            }
        }
    }


    protected void changeWalkDir() {
        if (walkDir == LEFT) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    public void resetEnemy() {
        hitBox.x = x;
        hitBox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        fallSpeed = 0;
    }

    public int getAniIndex() {
        return aniIndex;
    }
    public int getEnemyState() {
        return enemyState;
    }

    public boolean isActive() {
        return active;
    }
}
