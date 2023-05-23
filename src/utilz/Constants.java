package utilz;

import main.Game;

public class Constants {
    public static class Directions {
        public static int LEFT = 1;
        public static int RIGHT = 3;
    }

    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }
    }

    public static class Environment {
        public static class pauseOverlay {
            public static int PLAYANDHOMEBUTTON_DEFAULT_WIDTH = 400;
            public static int PLAYANDHOMEBUTTON_DEFAULT_HEIGHT = 500;
            public static int PLAYANDHOMEBUTTON_WIDTH = (int) (PLAYANDHOMEBUTTON_DEFAULT_WIDTH /5*2 * Game.SCALE);
            public static int PLAYANDHOMEBUTTON_HEIGHT = (int) (PLAYANDHOMEBUTTON_DEFAULT_HEIGHT /5*2 * Game.SCALE);
        }

        public static class chooseCharacter {
            public static int ADAM = 0;
            public static int EVE = 1;
            public static int CHOOSEB_DEFAULT_WIDTH = 320;
            public static int CHOOSEB_DEFAULT_HEIGHT = 112;

            public static int CHOOSEB_WIDTH = (int) (CHOOSEB_DEFAULT_WIDTH/2 * Game.SCALE);
            public static int CHOOSEB_HEIGHT = (int) (CHOOSEB_DEFAULT_HEIGHT/2 *Game.SCALE);
        }

        public static class queryButton {
            public static int ANSWERBUTTON_DEFAULT_WIDTH = 300;
            public static int ANSWERBUTTON_DEFAULT_HEIGHT = 150;

            public static int ANSWERBUTTON_WIDTH = (int) (ANSWERBUTTON_DEFAULT_WIDTH/2 * Game.SCALE);
            public static int ANSWERBUTTON_HEIGHT = (int) (ANSWERBUTTON_DEFAULT_HEIGHT/3 * Game.SCALE);


        }
    }
    public static class playerConstants {
        public static final float COOL_DOWN_ATTACK = 2f; //0.5s
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK_MELEE = 2;
        public static final int JUMP = 3;
        public static final int THROW_FIREBALL = 4;

        public static int GetSpriteAmount(int playerAction) {
            switch (playerAction) {
                case RUNNING:
                case ATTACK_MELEE:
                    return 2;
                case IDLE:
                case THROW_FIREBALL:
                default:
                    return 1;
            }


        }
    }

    public static class EnemyConstants {
        //SKELETON
        public static final int SKELETON = 0;
        public static final int DEVIL = 1;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int SKELETON_WIDTH_DEFAULT = 32;
        public static final int SKELETON_HEIGHT_DEFAULT = 32;

        public static final int SKELETON_WIDTH = (int) (SKELETON_WIDTH_DEFAULT * Game.SCALE);
        public static final int SKELETON_HEIGHT = (int) (SKELETON_HEIGHT_DEFAULT * Game.SCALE);

        public static final int SKELETON_DRAWOFFSETX = (int) (2 * Game.SCALE);
        public static final int SKELETON_DRAWOFFSETY = (int) (8 * Game.SCALE);

        //FINAL BOSS


        public static final int DEVIL_WIDTH_DEFAULT = 32;
        public static final int DEVIL_HEIGHT_DEFAULT = 32;

        public static final int DEVIL_DRAW_OFFSET_X = (int) (-10 * Game.SCALE);
        public static final int DEVIL_DRAW_OFFSET_Y = (int) (16 * Game.SCALE);
        public static final int DEVIL_WIDTH = (int) (DEVIL_WIDTH_DEFAULT * Game.SCALE) * 2;
        public static final int DEVIL_HEIGHT = (int) (DEVIL_HEIGHT_DEFAULT * Game.SCALE) * 2;

        public static final int DEVIL_DRAW_WIDTH = (int) (DEVIL_WIDTH * 1.28);
        public static final int DEVIL_DRAW_HEIGHT = (int) (DEVIL_HEIGHT * 1.28);




        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            switch (enemy_state) {
                case IDLE: {
                    if (enemy_type == SKELETON) {
                        return 11;
                    } else if (enemy_type == DEVIL) {
                        return 4;
                    }
                }
                case RUNNING: {
                    if (enemy_type == SKELETON) {
                        return 13;
                    } else if (enemy_type == DEVIL) {
                        return 8;
                    }
                }
                case ATTACK:
                    if (enemy_type == SKELETON) {
                        return 18;
                    } else if (enemy_type == DEVIL) {
                        return 5;
                    }
                case HIT:
                    if (enemy_type == SKELETON) {
                        return 8;
                    } else if (enemy_type == DEVIL) {
                        return 4;
                    }
                case DEAD:
                    if (enemy_type == SKELETON) {
                        return 15;
                    } else if (enemy_type == DEVIL) {
                        return 5;
                    }
            }
            return 0;
        }
        public static int GetMaxHealth(int enemyType) {
            switch (enemyType) {
                case SKELETON:
                    return 10;
                case DEVIL:
                    return 100;
                default:
                    return 1;
            }
        }

        public static int GetEnemyDmg(int enemyType) {
            switch (enemyType) {
                case SKELETON:
                    return 10;
                case DEVIL:
                    return 30;
                default:
                    return 0;
            }
        }
    }

    public static class ObjectConstants {
        public static final int RED_POTION = 0;
        public static final int BLUE_POTION = 1;

        public static final int RED_POTION_VALUE = 15;
        public static final int BLUE_POTION_VALUE = 10;

        public static final int POTION_WIDTH_DEFAULT = 152;
        public static final int POTION_HEIGHT_DEFAULT = 205;
        public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT/10);
        public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT/10);

        public static final int FIREBALL = 2;
        public static final int FIREBALL_BLOWING = 3;
        public static final int FIRE_BALL_DEFAULT_WIDTH = 32;
        public static final int FIRE_BALL_DEFAULT_HEIGHT = 32;

        public static final int FIRE_BALL_WIDTH = (int) (FIRE_BALL_DEFAULT_WIDTH * Game.SCALE);
        public static final int FIRE_BALL_HEIGHT = (int) (FIRE_BALL_DEFAULT_HEIGHT * Game.SCALE);

        public static final int SPEED = (int) (2 * Game.SCALE);
        public static final int Y_FIRE_BALL_OFFSET = (int) (10*Game.SCALE);
        public static final int COOL_DOWN_FIRE_BALL = 2; //2 seconds
        public static final int MANA_COST = 5;

        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case RED_POTION, BLUE_POTION:
                    return 0;
                case FIREBALL:
                    return 1;
                case FIREBALL_BLOWING:
                    return 3;
            }
            return 0;
        }
    }

}
