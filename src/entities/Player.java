package entities;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.playerConstants.*;
import static utilz.HelpMethods.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 45;
    private int playerAction = IDLE;
    private boolean up, left, down, right, jump;
    private boolean moving = false, attacking = false;
    private int playerSpeed = 2;
    private int[][] lvlData;
    private float xDrawOffSet = 0 * Game.SCALE, yDrawOffSet = 2 * Game.SCALE;

    //Jumping & Gravity
    private float airSpeed = 0f;
    private float jumpSpeed = -3f * Game.SCALE;
    private float gravity = 0.04f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, (int) (21 * Game.SCALE), (int) (29 * Game.SCALE));
    }

    public void update() {

        updatePos();
        updateAnimationTick();
        setAnimation();

    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    private void updatePos() {

        moving = false;

        if (jump) {
            jump();
        }

        if (!right && !left && !inAir)
            return;

        float xSpeed = 0;

        if (left)
            xSpeed -= playerSpeed;

        if (right)
            xSpeed += playerSpeed;

        if (!inAir)
            if (!EntityIsOnFloor(hitBox, lvlData))
                inAir = true;


        if (inAir) {
            if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y = GetEntityYPosBelowRoofOrAboveFloor(airSpeed, hitBox);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else
            updateXPos(xSpeed);

        moving = true;
    }

    private void jump() {
        if (inAir)
            return;
        else {
            inAir = true;
            airSpeed = jumpSpeed;
        }
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
            hitBox.x += xSpeed;
        } else {
            hitBox.x = GetEntityXPosNextToWall(xSpeed, hitBox);
        }
    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (inAir) {
            //if (airSpeed < 0)
            playerAction = JUMP;
            //else
            //playerAction = FALLING;
        }

        if (attacking) {
            playerAction = ATTACK_1;
        }

        if (startAni != playerAction) {
            resetAniTickAndIndex();
        }
    }

    private void updateAnimationTick() {

        aniTick++;
        if (aniTick > aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }


    private void resetAniTickAndIndex() {
        aniTick = 0;
        aniIndex = 0;
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitBox.x - xDrawOffSet), (int) (hitBox.y - yDrawOffSet), width, height, null);
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!EntityIsOnFloor(hitBox, lvlData))
            inAir = true;
    }

    private void loadAnimations() {
        BufferedImage img = LoadSave.GetPlayerAtlas(LoadSave.EVE_ATLAS);
        animations = new BufferedImage[4][2];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 33, j * 33, 33, 33);
            }
        }

    }

    public void resetDirBoolean() {
        up = false;
        left = false;
        down = false;
        right = false;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}