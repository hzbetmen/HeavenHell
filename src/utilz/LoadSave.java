package utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class LoadSave {
    public static final String EVE_ATLAS = "eve.png";
    public static final String ADAM_ATLAS = "adam.png";
    public static final String LEVEL_ATLAS = "BLOCKS.png";
    public static final String MENU_BACKGROUND_OUT = "background_menu_test.png";

    public static final String BACKGROUND_PLAYING = "Background.png";
    public static final String PLAY_AND_HOME = "playAndHome.png";
    public static final String PLAY_HOME_REPLAY = "play_home_replay.png";
    public static final String CHOOSE_BUTTON = "choose_button.png";
    public static final String CHOOSE_CHARACTER_BACKGROUND = "choosing_character_background.png";

    public static final String PAUSED_TEXT = "paused_text.png";
    public static final String GAME_OVER = "gameOver.png";
    public static final String LEVEL_COMPLETED_TEXT = "level_completed_text.png";
    public static final String ANSWER_A = "A.png";
    public static final String ANSWER_B = "B.png";
    public static final String ANSWER_C = "C.png";
    public static final String ANSWER_D = "D.png";
    public static final String BACKGROUND_QUERY = "background_query.png";
    public static final String JESUS_ATLAS = "jesus.png";
    public static final String QUESTION = "questions.png";
    public static final String END_GAME_TEXT = "end_game.png";
    public static final String POTION_ATLAS = "potions.png";
    public static final String HEALTH_MANA_BAR = "health_mana_bar.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACK_GROUND = "menu_background.png";
    public static final String FIRE_BALL_ATLAS = "fire_ball.png";

    public static final String SKELETON_ATLAS = "skeleton.png";
    public static final String DEVIL_ATLAS = "devil.png";
    public static final String GO_BACK = "go_back.png";
    public static final String HOW_TO_PLAY = "keys.png";
    public static final String FINAL = "final.png";



    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);


        try {
            img = ImageIO.read(is);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }



    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/lvls");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        File files[] = file.listFiles();
        File filesSorted[] = new File[files.length];

        for (int i = 0; i < files.length; i++)
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals("" + (i + 1) + ".png"))
                    filesSorted[i] = files[j];
            }

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for (int i = 0; i < imgs.length; i++) {
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return imgs;
    }

}