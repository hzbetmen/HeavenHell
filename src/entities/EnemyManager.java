package entities;

import gamestates.Playing;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import levels.Level;
import main.Game;
import utilz.LoadSave;

import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] skeletonArr, devilArr;
    private ArrayList<Skeleton> skeletons = new ArrayList<>();
    private ArrayList<Devil> devils = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        skeletons = level.getSkeletons();
        devils = level.getDevils();
    }

    public void update(int[][] lvlData, Player player) {
        boolean anyActive = true;

        for (Skeleton s : skeletons) {
            if (s.isActive()) {
                s.update(lvlData, player);
                anyActive = false;
            }
        }
        for (Devil d : devils) {
            if (d.isActive()) {
                d.update(lvlData, playing);
                anyActive = false;
            }
        }

        if (anyActive)
            playing.loadNextLvl();

    }

    public void draw(Graphics g, int xLvlOffSet) {
        drawSkeletons(g, xLvlOffSet);
        drawDevils(g, xLvlOffSet);
    }

    private void drawSkeletons(Graphics g, int xLvlOffSet) {
        for (Skeleton s : skeletons) {
            if (s.isActive()) {
                g.drawImage(skeletonArr[s.getEnemyState()][s.getAniIndex()],
                        (int) s.getHitBox().x - SKELETON_DRAWOFFSETX - xLvlOffSet + s.flipX(),
                        (int) s.getHitBox().y - SKELETON_DRAWOFFSETY,
                        SKELETON_WIDTH * s.flipW(), SKELETON_HEIGHT,
                        null);
//                s.drawHitbox(g, xLvlOffSet);
//                s.drawAttackBox(g, xLvlOffSet);
            }
        }
    }

    private void drawDevils(Graphics g, int xLvlOffSet) {
        for (Devil d : devils) {
            if (d.isActive()) {
                g.drawImage(devilArr[d.getEnemyState()][d.getAniIndex()],
                        (int) d.getHitBox().x - DEVIL_DRAW_OFFSET_X - xLvlOffSet + d.flipX(),
                        (int) d.getHitBox().y - DEVIL_DRAW_OFFSET_Y,
                        DEVIL_DRAW_WIDTH * d.flipW(),
                        DEVIL_DRAW_HEIGHT,
                        null);
//                d.drawHitbox(g, xLvlOffSet);
//                d.drawAttackBox(g, xLvlOffSet);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Skeleton s : skeletons) {
            if (s.isActive()) {
                if (attackBox.intersects(s.getHitBox()))
                    if (!playing.getPlayer().attackIsOnCooldown()) {
                        s.hurt(10);
                        return;
                    }
            }
        }
        for (Devil d : devils) {
            if (d.isActive()) {
                if (attackBox.intersects(d.getHitBox()))
                    if (!playing.getPlayer().attackIsOnCooldown()) {
                        d.hurt(10);
                        return;
                    }
            }
        }
    }

    private void loadEnemyImgs() {
        skeletonArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.SKELETON_ATLAS), 18, 5, SKELETON_WIDTH_DEFAULT, SKELETON_HEIGHT_DEFAULT);
        devilArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.DEVIL_ATLAS), 8, 5, DEVIL_WIDTH_DEFAULT, DEVIL_HEIGHT_DEFAULT);
    }

    private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
        BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
        for (int j = 0; j < tempArr.length; j++)
            for (int i = 0; i < tempArr[j].length; i++)
                tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
        return tempArr;
    }

    public void resetAllEnemies() {
        for (Skeleton s : skeletons) {
            s.resetEnemy();
        }
        for (Devil d : devils) {
            d.resetEnemy();
        }
    }

    public ArrayList<Skeleton> getSkeletons() {
        return skeletons;
    }

    public ArrayList<Devil> getDevils() {
        return devils;
    }

}
