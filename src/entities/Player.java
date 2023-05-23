package entities;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.Constants.Environment.chooseCharacter.*;
import static utilz.Constants.ObjectConstants.MANA_COST;
import static utilz.Constants.ObjectConstants.COOL_DOWN_FIRE_BALL;
import static utilz.Constants.playerConstants.*;

import static utilz.HelpMethods.*;
import static utilz.HelpMethods.EntityIsOnFloor;

public class Player extends Entity {

    private Playing playing;
    private int adamOrEve;
    private BufferedImage[][] adamAnimations;
    private BufferedImage[][] eveAnimations;
    private int aniTick, aniIndex;
    private final int aniSpeed = 45;
    private int playerAction = IDLE;
    private boolean left, right, jump;
    private boolean moving = false;
    private boolean attacking = false;
    private final int playerSpeed = (int) (1.5 * Game.SCALE);
    private int[][] lvlData;
    private float xDrawOffSet = 0 * Game.SCALE, yDrawOffSet = 2 * Game.SCALE;

    //Jumping & Gravity
    private float airSpeed = 0f;
    private final float jumpSpeed = -3f * Game.SCALE;
    private float gravity = 0.04f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    //HealthMana
    private BufferedImage healthManaBar;
    //30,90,30,190
    private final int healthManaBarWidth = (int) (300 * Game.SCALE);
    private final int healthManaBarHeight = (int) (60 * Game.SCALE);
    private final int healthManaBarX = (int) (10 * Game.SCALE);
    private final int healthManaBarY = (int) (10 * Game.SCALE);

    private final int healthBarWidth = (int) (286 * Game.SCALE);
    private final int healthBarHeight = (int) (9.2 * Game.SCALE);
    private final int healthBarX = (int) (7.4 * Game.SCALE) + healthManaBarX;
    private final int healthBarY = (int) (17.4 * Game.SCALE) + healthManaBarY;

    private final int manaBarWidth = (int) (286 * Game.SCALE);
    private final int manaBarHeight = (int) (9.2 * Game.SCALE);
    private final int manaBarX = (int) (7.4 * Game.SCALE) + healthManaBarX;
    private final int manaBarY = (int) (36.4 * Game.SCALE) + healthManaBarY;

    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = (int) ((float) currentHealth / (float) maxHealth * healthBarWidth);

    private int maxMana = 100;
    private int currentMana = maxMana;
    private int manaWidth = (int) ((float) currentMana / (float) maxMana * manaBarWidth);

    private boolean shootFireBall = false;
    private boolean coolDownAttack = false, coolDownFireBall = false;
    private int coolDownFireBallTick, coolDownAttackTick;
    private boolean faceLeft, faceRight;

    private int flipX = 0;
    private int flipWidth = 1;
    private boolean attackChecked;
    private Rectangle2D.Float attackBox;


    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimations();
        initHitbox(x, y, (int) (21 * Game.SCALE), (int) (29 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
    }

    public void update() {
        updateHealthBar();
        updateManaBar();

        if (currentHealth <= 0) {
            playing.setGameOver(true);
            return;
        }

        updateAttackBox();

        updatePos();
        if (moving)
            checkPotionTouched();

        if (attacking) {
            checkAttack();
        }

        updateAnimationTick();
        setAnimation();

    }

    private void updateAttackBox() {
        if (right) {
            attackBox.x = hitBox.x + hitBox.width + (int) (1.8 * Game.SCALE);
        } else if (left) {
            attackBox.x = hitBox.x - hitBox.width - (int) (1 * Game.SCALE);
        }
        attackBox.y = hitBox.y + (5 * Game.SCALE);
    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 1) {
            return;
        }
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
        coolDownAttack = true;
    }

    private void updateHealthBar() {
        healthWidth = (int) ((float) (currentHealth) / maxHealth * healthBarWidth);
    }

    private void updateManaBar() {
        manaWidth = (int) ((float) (currentMana) / maxMana * manaBarWidth);
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    private void updatePos() {

        moving = false;

        if (jump) {
            jump();
        }

        if (!inAir)
            if ((!right && !left) || (right && left))
                return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
            flipX = (int) hitBox.width;
            flipWidth = -1;
        }

        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipWidth = 1;
        }

        if (!inAir)
            if (!EntityIsOnFloor(hitBox, lvlData))
                inAir = true;


        if (inAir) {
            if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y = GetEntityYPosBelowRoofOrAboveFloor(airSpeed, hitBox, false);
                if (airSpeed > 0) {
                    if (hitBox.y + hitBox.height + 1 >= Game.GAME_HEIGHT)
                        playing.setGameOver(true);
                    resetInAir();
                } else
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

    private void drawUI(Graphics g) {
        g.drawImage(healthManaBar, healthManaBarX, healthManaBarY, healthManaBarWidth, healthManaBarHeight, null);

        g.setColor(Color.red);
        g.fillRect(healthBarX, healthBarY, healthWidth, healthBarHeight);

        g.setColor(Color.blue);
        g.fillRect(manaBarX, manaBarY, manaWidth, manaBarHeight);
    }

    public void changeHealth(int value) {
        currentHealth += value;
        if (currentHealth < 0)
            currentHealth = 0;
        else if (currentHealth > maxHealth)
            currentHealth = maxHealth;
    }

    public void changeMana(int value) {
        currentMana += value;
        if (currentMana < 0)
            currentMana = 0;
        else if (currentMana > maxMana)
            currentMana = maxMana;
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
            playerAction = ATTACK_MELEE;
        }

        if (shootFireBall)
            playerAction = THROW_FIREBALL;

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
                attackChecked = false;
                shootFireBall = false;
            }
        }

        checkCoolDown();

    }

    private void checkCoolDown() {
        if (coolDownAttack) {
            coolDownAttackTick++;
            if (coolDownAttackTick >= COOL_DOWN_ATTACK * 120) {
                coolDownAttack = false;
                coolDownAttackTick = 0;
            }
        }

        if (coolDownFireBall) {
            coolDownFireBallTick++;
            if (coolDownFireBallTick >= COOL_DOWN_FIRE_BALL * 120) {
                coolDownFireBall = false;
                coolDownFireBallTick = 0;
            }
        }
    }


    private void resetAniTickAndIndex() {
        aniTick = 0;
        aniIndex = 0;
    }

    public void render(Graphics g, int xLvlOffset) {
        if (adamOrEve == ADAM) {
            g.drawImage(adamAnimations[playerAction][aniIndex], (int) (hitBox.x - xDrawOffSet) - xLvlOffset + flipX, (int) (hitBox.y - yDrawOffSet), width * flipWidth, height, null);
        } else {
            g.drawImage(eveAnimations[playerAction][aniIndex], (int) (hitBox.x - xDrawOffSet) - xLvlOffset + flipX, (int) (hitBox.y - yDrawOffSet), width * flipWidth, height, null);
        }
//        drawHitbox(g, xLvlOffset);
//        drawAttackBox(g, xLvlOffset);
        drawUI(g);
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!EntityIsOnFloor(hitBox, lvlData))
            inAir = true;
    }

    private void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - xLvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }


    public void getAdamOrEveChoosing(int adamOrEve) {
        this.adamOrEve = adamOrEve;
    }


    private void loadAnimations() {
        BufferedImage adamTemp = LoadSave.GetSpriteAtlas(LoadSave.ADAM_ATLAS);
        adamAnimations = new BufferedImage[5][2];
        for (int j = 0; j < adamAnimations.length; j++) {
            for (int i = 0; i < adamAnimations[j].length; i++) {
                adamAnimations[j][i] = adamTemp.getSubimage(i * 33, j * 33, 33, 33);
            }
        }

        BufferedImage eveTemp = LoadSave.GetSpriteAtlas(LoadSave.EVE_ATLAS);
        eveAnimations = new BufferedImage[5][2];
        for (int j = 0; j < eveAnimations.length; j++) {
            for (int i = 0; i < eveAnimations[j].length; i++) {
                eveAnimations[j][i] = eveTemp.getSubimage(i * 33, j * 33, 33, 33);
            }
        }

        healthManaBar = LoadSave.GetSpriteAtlas(LoadSave.HEALTH_MANA_BAR);
    }

    private void checkPotionTouched() {
        playing.checkPotionTouched(hitBox);
    }

    public void resetDirBoolean() {
        left = false;
        right = false;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
        resetDirBoolean();
        inAir = false;
        attacking = false;
        moving = false;
        playerAction = IDLE;

        currentHealth = maxHealth;
        currentMana = maxMana;

        hitBox.x = x;
        hitBox.y = y;

        if (!EntityIsOnFloor(hitBox, lvlData))
            inAir = true;

    }

    public boolean shootFireBall() {
        return shootFireBall;
    }

    public void activeSkillFireBall(boolean shootFireBall) {
        if (currentMana >= MANA_COST) {
            this.shootFireBall = shootFireBall;
            changeMana(-MANA_COST);
        }
    }

    public boolean attackIsOnCooldown() {
        return coolDownAttack;
    }

    public boolean fireBallIsOnCooldown() {
        return coolDownFireBall;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getAniTick() {
        return aniTick;
    }

    public void startCooldown() {
        this.coolDownFireBall = true;
    }

    public void faceLeft(boolean faceLeft) {
        this.faceLeft = faceLeft;
    }

    public void faceRight(boolean faceRight) {
        this.faceRight = faceRight;
    }

    public boolean isFacingLeft() {
        return faceLeft;
    }

    public boolean isFacingRight() {
        return faceRight;
    }

}