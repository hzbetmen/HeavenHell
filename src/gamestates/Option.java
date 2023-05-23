package gamestates;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Option extends State implements Statemethods{
    BufferedImage howToPlay = LoadSave.GetSpriteAtlas(LoadSave.HOW_TO_PLAY);
    public Option(Game game) {
        super(game);
    }

    @Override
    public void update() {

    }
    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0,0,0,200));
        g.fillRect(0,0,Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.ORANGE);
        g.drawString("Press ESC to go back to MENU!", Game.GAME_WIDTH/2 - (int) (70 * Game.SCALE), (int) (50 * Game.SCALE));

        g.drawImage(howToPlay, Game.GAME_WIDTH/2 - howToPlay.getWidth()/4, (int) (100 * Game.SCALE), howToPlay.getWidth()/2, howToPlay.getHeight()/2, null);
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseMoved(MouseEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            Gamestate.state = Gamestate.MENU;
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
