package UI;

import Data.PlayerSettings;
import Main.Game;
import Sound.SoundPlayer;

import javax.imageio.ImageIO;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static Main.Game.HEIGHT;
import static Main.Game.WIDTH;

public class MenuOptions extends MouseInputAdapter {

    private BufferedImage optionsImage;
    private Game game;
    private Background background;

    private boolean saveButton;

    private int mX;
    private int mY;

    //TODO
    //SliderX is the volume control. This is what you need to save in the player config file @Jack
    public static int sliderX = WIDTH/2-10, sliderY = HEIGHT/4+39, sliderThickness = 34, sliderLength = 16, volumeModifier = 0;

    public MenuOptions(Game game) {
        this.game = game;
        background = new Background(game, "assets/TownBackground.png");
    }

    public void mouseMoved(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
        if(mouseOver(mX, mY, 812, 450, 109, 32)) {
            saveButton = true;
        } else {
            saveButton = false;
        }
    }

    public void mousePressed(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();

        if(mouseOver(mX, mY, WIDTH/2-10, HEIGHT/4+39, 250, 33)) {
            sliderX = mX - sliderLength/2;
            volumeModifier = sliderX - WIDTH/2-10;
            game.player.settings.volume = volumeModifier;
        } else if (mouseOver(mX, mY, 812, 450, 109, 32)) {
            game.player.settings.saveSettings();
        }
    }

    public void mouseDragged(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();

        if(mouseOver(mX, mY, WIDTH/2-10, HEIGHT/4+39, 250, 33)) {
            sliderX = mX - sliderLength/2;
            volumeModifier = sliderX - WIDTH/2-10;
            game.player.settings.volume = volumeModifier;
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
        background.tick();
    }

    public void render(Graphics g) {
        background.render(g);
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
            g.drawRect(812, 450, 109, 32);
        }

        g.setColor(Color.yellow);
        g.fillRect(sliderX, sliderY, sliderLength, sliderThickness);
        g.setColor(Color.black);
        g.drawRect(sliderX-1, sliderY-1, sliderLength+1, sliderThickness+1);
        g.drawRect(sliderX-2, sliderY-2, sliderLength+3, sliderThickness+3);
    }
}
