package entities;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.playerConstants.*;
import static utilz.HelpMethods.CanMoveHere;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 30;
    private int playerAction = IDLE;
    private boolean up, left, down, right;
    private boolean moving = false, attacking = false;
    private int playerSpeed = 2;
    private int[][] levelData;
    private float xDrawOffSet = 0 * Game.SCALE, yDrawOffSet = 1 * Game.SCALE;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, (int) (21 * Game.SCALE), (int) (31 * Game.SCALE));
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

        if (!up && !left && !down && !right) {
            return ;
        } else {
            float xSpeed = 0, ySpeed = 0;

            if (left && !right) {
                xSpeed = -playerSpeed;
            } else if (right && !left) {
                xSpeed = playerSpeed;
            }

            if (up && !down) {
                ySpeed = -playerSpeed;
            } else if (down && !up) {
                ySpeed = playerSpeed;
            }

            if (CanMoveHere(hitBox.x + xSpeed, hitBox.y + ySpeed, hitBox.width, hitBox.height, levelData)) {
                moving = true;
                hitBox.x += xSpeed;
                hitBox.y += ySpeed;
            }
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

    private void setAnimation() {
        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (attacking) {
            playerAction = ATTACK_1;
        }

        if (startAni != playerAction) {
            resetAniTickAndIndex();
        }
    }

    private void resetAniTickAndIndex() {
        aniTick = 0;
        aniIndex = 0;
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitBox.x - xDrawOffSet), (int) (hitBox.y - yDrawOffSet), width, height, null);
        drawHitbox(g);
    }

    public void loadLvlData(int[][] levelData) {
        this.levelData = levelData;
    }

    private void loadAnimations() {
        BufferedImage img = LoadSave.GetPlayerAtlas(LoadSave.EVE_ATLAS);
        animations = new BufferedImage[3][2];
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
}