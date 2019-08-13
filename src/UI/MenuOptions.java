package UI;

import Main.Game;
import Main.STATE;
import Main.Spawner;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuOptions extends MouseAdapter {

    private BufferedImage optionsImage;
    private Game game;
    private Background background;

    private int mX;
    private int mY;

    public MenuOptions(Game game) {

    }

    public void mouseMoved(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
    }

    public void mousePressed(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
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
        try {
        optionsImage = ImageIO.read(new File("assets/UI_Elements/OptionsMenu.png"));
    } catch (IOException e) {
        System.out.println("File not found");
        e.printStackTrace();
        System.exit(0);
    }
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g.drawImage(optionsImage, 150, 0, null);

    }
}
