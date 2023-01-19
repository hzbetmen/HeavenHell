package utilz;

import main.Game;

public class Constants {

    public static class Environment {
        public static class pauseOverlay {
            public static int PLAYBUTTON_DEFAULT_WIDTH = 400;
            public static int PLAYBUTTON_DEFAULT_HEIGHT = 500;

            public static int PLAYBUTTON_WIDTH = (int) (PLAYBUTTON_DEFAULT_WIDTH/5*2 * Game.SCALE);
            public static int PLAYBUTTON_HEIGHT = (int) (PLAYBUTTON_DEFAULT_HEIGHT/5*2 * Game.SCALE);
        }
    }
    public static class playerConstants {

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK_1 = 2;
        public static final int JUMP = 3;
        public static final int FALLING = 4;
        public static final int GROUND = 5;
        public static final int HIT = 6;
        public static final int ATTACK_JUMP_1 = 7;
        public static final int ATTACK_JUMP_2 = 8;

        public static int GetSpriteAmount(int playerAction) {
            switch (playerAction) {
                case IDLE:
                    return 1;
                case RUNNING:
                case ATTACK_1:
                case ATTACK_JUMP_1:
                case ATTACK_JUMP_2:
                    return 2;
                case GROUND:
                    return 2;
                case FALLING:
                default:
                    return 1;
            }


        }
    }
}
