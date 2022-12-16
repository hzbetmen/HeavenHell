package utilz;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String EVE_ATLAS = "eve.png";
    public static final String ADAM_ATLAS = "adam.png";
    public static final String SPEARMAN_ATLAS = "spearman.png";

    public static final String LEVEL_ATLAS = "BLOCKS.png";
    public static final String LEVEL_ONE_DATA = "test_level.png";


    public static BufferedImage GetPlayerAtlas(String fileName) {
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

    public static int[][] GetLevelData() {
        int[][] levelData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = LoadSave.GetPlayerAtlas(LEVEL_ONE_DATA);

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getRed();
                if (value >= 16)
                    value = 0;
                levelData[i][j] = value;
            }
        }
        return levelData;
    }

}