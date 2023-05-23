package ui;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import static utilz.Constants.Environment.chooseCharacter.*;
import static utilz.HelpMethods.isIn;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ChoosingCharacter {

    private Playing playing;
    private BufferedImage eve, adam, backgroundImg;
    private ChooseButton adamButton, eveButton;

    public ChoosingCharacter(Playing pLaying) {
        this.playing = pLaying;
        createButtons();
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage eveTemp = LoadSave.GetSpriteAtlas(LoadSave.EVE_ATLAS);
        eve = eveTemp.getSubimage(0, 0, 33, 33);

        BufferedImage adamTemp = LoadSave.GetSpriteAtlas(LoadSave.ADAM_ATLAS);
        adam = adamTemp.getSubimage(0, 0, 33, 33);

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.CHOOSE_CHARACTER_BACKGROUND);
    }

    private void createButtons() {
        int adamX = (int) (132 * Game.SCALE);
        int eveX = (int) (537 * Game.SCALE);
        int buttonY = (int) (300 * Game.SCALE);

        adamButton = new ChooseButton(adamX, buttonY, CHOOSEB_WIDTH, CHOOSEB_HEIGHT);
        eveButton = new ChooseButton(eveX, buttonY, CHOOSEB_WIDTH, CHOOSEB_HEIGHT);

    }

    public void update() {
        adamButton.update();
        eveButton.update();
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        g.drawImage(adam, 220, 132, 300, 300, null);
        adamButton.draw(g);

        g.drawImage(eve, 820, 130, 300, 300, null);
        eveButton.draw(g);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, adamButton))
            adamButton.setMousePressed(true);
        else if (isIn(e, eveButton))
            eveButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, adamButton)) {
            if (adamButton.isMousePressed()) {
                playing.getPlayer().getAdamOrEveChoosing(ADAM);
                playing.choosingCharacterDone(true);
            }
        } else if (isIn(e, eveButton))
            if (eveButton.isMousePressed()) {
                playing.getPlayer().getAdamOrEveChoosing(EVE);
                playing.choosingCharacterDone(true);
            }

        adamButton.resetBools();
        eveButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        adamButton.setMouseOver(false);
        eveButton.setMouseOver(false);

        if (isIn(e, adamButton))
            adamButton.setMouseOver(true);
        else if (isIn(e, eveButton))
            eveButton.setMouseOver(true);
    }

}
