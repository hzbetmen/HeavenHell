package objects;

import entities.Devil;
import entities.EnemyManager;
import entities.Player;
import entities.Skeleton;
import gamestates.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;

import java.awt.Graphics;


import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.ObjectConstants.*;
import static utilz.HelpMethods.isFireBallHittingLevel;

public class ObjectManager {
    private Playing playing;
    private BufferedImage[][] potionImgs, fireBallImgs;
    private ArrayList<Potion> potions;
    private ArrayList<FireBall> fireBalls = new ArrayList<>();

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }

    public void checkPotionsTouched(Rectangle2D.Float hitBox) {
        for (Potion p : potions)
            if (p.isActive()) {
                if (hitBox.intersects(p.getHitBox())) {
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
            }
    }

    public void applyEffectToPlayer(Potion p) {
        if (p.getObjType() == RED_POTION)
            playing.getPlayer().changeHealth(RED_POTION_VALUE);
        else
            playing.getPlayer().changeMana(BLUE_POTION_VALUE);
    }

    public void loadObjects(Level newLevel) {
        potions = newLevel.getPotions();
        fireBalls.clear();
    }

    private void loadImgs() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImgs = new BufferedImage[2][1];
        for (int j = 0; j < potionImgs.length; j++)
            for (int i = 0; i < potionImgs[j].length; i++)
                potionImgs[j][i] = potionSprite.getSubimage(POTION_WIDTH_DEFAULT * i, POTION_HEIGHT_DEFAULT * j, POTION_WIDTH_DEFAULT, POTION_HEIGHT_DEFAULT);


        BufferedImage fireBallSprite = LoadSave.GetSpriteAtlas(LoadSave.FIRE_BALL_ATLAS);
        fireBallImgs = new BufferedImage[2][3];
        for (int i = 0; i < fireBallImgs.length; i++)
            for (int j = 0; j < fireBallImgs[i].length; j++)
                fireBallImgs[i][j] = fireBallSprite.getSubimage(j * FIRE_BALL_DEFAULT_WIDTH, i * FIRE_BALL_DEFAULT_HEIGHT, FIRE_BALL_DEFAULT_WIDTH, FIRE_BALL_DEFAULT_HEIGHT);
    }

    public void update(int[][] lvlData, Player player, EnemyManager enemyManager) {
        for (Potion p : potions)
            if (p.isActive())
                p.update();

        if (player.shootFireBall() && !player.fireBallIsOnCooldown()) {
            shootFireBall(player);
            player.startCooldown();
        }

        updateFireBalls(lvlData, player, enemyManager);
    }

    private void shootFireBall(Player player) {
        int dir = 1;
        int xFireBallOffset = (int) (player.getHitBox().width + 5 * Game.SCALE);
        if (player.isFacingLeft()) {
            dir = -1;
            xFireBallOffset = -(int) (player.getHitBox().width + 5 * Game.SCALE);
        }
        int yFireBallOffset = (int) player.getHitBox().height / 2;

        fireBalls.add(new FireBall((int) player.getHitBox().x + xFireBallOffset, (int) player.getHitBox().y + yFireBallOffset, dir, 0));
    }

    private void updateFireBalls(int[][] lvlData, Player player, EnemyManager enemyManager) {
        for (FireBall fp : fireBalls) {
            if (fp.isActive())
                fp.updatePos();

            if (isFireBallHittingLevel(fp, lvlData))
                fp.setActive(false);

            for (Skeleton s : enemyManager.getSkeletons())
                if (fp.getHitBox().intersects(s.getHitBox()) && fp.isActive() && s.isActive()) {
                    fp.setActive(false);
                    s.hurt(20);
                }

            for (Devil d : enemyManager.getDevils())
                if (fp.getHitBox().intersects(d.getHitBox()) && fp.isActive() && d.isActive()) {
                    fp.setActive(false);
                    d.hurt(20);
                }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawFireBalls(g, xLvlOffset);
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        for (Potion p : potions)
            if (p.isActive()) {
                int type = 1;
                if (p.getObjType() == RED_POTION)
                    type = 0;
                g.drawImage(potionImgs[type][0], (int) (p.getHitBox().x - p.getxDrawOffSet() - xLvlOffset), (int) (p.getHitBox().y - p.getyDrawOffSet()), POTION_WIDTH, POTION_HEIGHT,
                        null);
            }
    }

    private void drawFireBalls(Graphics g, int xLvlOffset) {
        for (int i = 0; i < fireBalls.size(); i++) {
            int fireBallX = (int) (fireBalls.get(i).getHitBox().x - xLvlOffset + fireBalls.get(i).getFlipX());
            int fireBallY = (int) fireBalls.get(i).getHitBox().y - Y_FIRE_BALL_OFFSET;
            int fireBallWidth = FIRE_BALL_WIDTH * fireBalls.get(i).getDir();

            if (fireBalls.get(i).isActive()) {
                g.drawImage(fireBallImgs[fireBalls.get(i).getRowIndex()][0], fireBallX, fireBallY, fireBallWidth, FIRE_BALL_HEIGHT, null);

                //fireBalls.get(i).drawHitbox(g, xLvlOffset);
            }
        }
    }

    public void resetAllObjects() {
        for (Potion p : potions)
            p.reset();
    }
}