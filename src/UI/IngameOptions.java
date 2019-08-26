package UI;

import Main.Game;

import javax.imageio.ImageIO;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
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
    private boolean volumeListener = false;
    private boolean mouseIsPressed = false;

    private int mX;
    private int mY;

    public IngameOptions(Game game) {
        this.game = game;
    }

    public void mousePressed(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();

        if(mouseOver(mX, mY, WIDTH/2-10, HEIGHT/4+39, 250, 33)) {
            sliderX = mX - sliderLength/2;
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

        g.setColor(Color.yellow);
        g.fillRect(sliderX, sliderY, sliderLength, sliderThickness);
        g.setColor(Color.black);
        g.drawRect(sliderX-1, sliderY-1, sliderLength+1, sliderThickness+1);
        g.drawRect(sliderX-2, sliderY-2, sliderLength+3, sliderThickness+3);

    }
}
