package UI;

import Main.Game;
import Main.STATE;

import javax.imageio.ImageIO;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static Main.Game.HEIGHT;
import static Main.Game.WIDTH;
import static UI.MenuOptions.*;

public class IngameOptions extends MouseInputAdapter {

    private BufferedImage optionsImage;
    private Game game;

    private boolean saveButton;
    private boolean discardButton;

    private int mX;
    private int mY;

    public IngameOptions(Game game) {
        this.game = game;
        sliderX = (int) (630 + game.player.settings.volume*2.5);

        if(game.player.settings.volume>100) game.player.settings.volume=100;
        if(game.player.settings.volume<0) game.player.settings.volume=0;
    }

    public void mouseMoved(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
        saveButton = mouseOver(mX, mY, 812, 450, 109, 32);
        discardButton = mouseOver(mX, mY, 694, 450, 109, 32);
    }

    public void mousePressed(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();

        //Slider
        if(mouseOver(mX, mY, WIDTH/2-10, HEIGHT/4+39, 250, 33)) {
            sliderX = mX - sliderLength/2;
            volumeModifier = (int) ((sliderX - 630)/2.5);
            game.handler.changeVolume(volumeModifier);
        }
        //Save button
        else if (mouseOver(mX, mY, 812, 450, 109, 32)) {
            game.gameState = STATE.Pause;
            game.removeMouseListener(this);
            game.removeMouseMotionListener(this);

            game.player.settings.volume = volumeModifier;
            game.player.settings.saveSettings();

            game.addMouseMotionListener(game.pause);
            game.addMouseListener(game.pause);
        }
        //Discard button
        else if (mouseOver(mX, mY, 694, 450, 109, 32)) {
            game.gameState = STATE.Pause;
            game.removeMouseListener(this);
            game.removeMouseListener(this);

            game.handler.changeVolume(game.player.settings.volume);

            game.addMouseMotionListener(game.menu);
            game.addMouseListener(game.menu);
        }
    }

    public void mouseDragged(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();

        if(mouseOver(mX, mY, WIDTH/2-10, HEIGHT/4+39, 250, 33)) {
            sliderX = mX - sliderLength/2;
        }
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void tick() {

    }

    public void render(Graphics g) {
        Color transparent = new Color(0, 0, 0, 200);
        g.setColor(transparent);
        g.fillRect(0, 0, WIDTH, Game.HEIGHT);
        try {
            optionsImage = ImageIO.read(new File("assets/UI_Elements/OptionsMenu.png"));
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
            System.exit(0);
        }
        g.drawImage(optionsImage, 150, 0, null);

        //Volume slider bounds
        //g.setColor(Color.green);
        //g.drawRect(WIDTH/2-10, HEIGHT/4+39, 250, 33);

        if(saveButton) {
            g.setColor(Color.green);
            g.drawRect(813, 451, 107, 30);
            g.drawRect(812, 450, 109, 32);
            g.drawRect(811, 449, 111, 34);
        }
        if(discardButton) {
            g.setColor(Color.RED);
            g.drawRect(695, 451, 107, 30);
            g.drawRect(694, 450, 109, 32);
            g.drawRect(693, 449, 111, 34);
        }

        g.setColor(Color.yellow);
        g.fillRect(sliderX, sliderY, sliderLength, sliderThickness);
        g.setColor(Color.black);
        g.drawRect(sliderX-1, sliderY-1, sliderLength+1, sliderThickness+1);
        g.drawRect(sliderX-2, sliderY-2, sliderLength+3, sliderThickness+3);

    }
}
