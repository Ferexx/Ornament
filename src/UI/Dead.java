package UI;

import Main.Game;
import Main.STATE;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Dead extends MouseAdapter {

    private BufferedImage deadScreenImage;
    private Game game;
    private static boolean respawnOutline = false, optionOutline = false, quitOutline = false;

    private int mX;
    private int mY;

    public Dead(Game game) {
        this.game = game;
    }

    public void mouseMoved(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();

        if (mouseOver(mX, mY, Game.WIDTH / 2 - 163, Game.HEIGHT / 2 - 89, 303, 89)) {
            respawnOutline = true;
            optionOutline = false;
            quitOutline = false;
        } else if (mouseOver(mX, mY, Game.WIDTH / 2 - 131, Game.HEIGHT / 2 + 7, 234, 73)) {
            respawnOutline = false;
            optionOutline = true;
            quitOutline = false;
        } else if (mouseOver(mX, mY, Game.WIDTH / 2 - 97, Game.HEIGHT / 2 + 86, 166, 53)) {
            respawnOutline = false;
            optionOutline = false;
            quitOutline = true;
        } else {
            respawnOutline = false;
            optionOutline = false;
            quitOutline = false;
        }

    }

    public void mousePressed(MouseEvent e) {

        mX = e.getX();
        mY = e.getY();

        if (mouseOver(mX, mY, Game.WIDTH / 2 - 163, Game.HEIGHT / 2 - 89, 303, 89)) {
            //Play game
            game.removeMouseListener(this);
            game.removeMouseListener(this);
            respawn(game);
        }

        if (mouseOver(mX, mY, Game.WIDTH / 2 - 131, Game.HEIGHT / 2 + 7, 234, 73)) {
            //Options
        }

        if (mouseOver(mX, mY, Game.WIDTH / 2 - 97, Game.HEIGHT / 2 + 86, 166, 53)) {
            //quit
            System.exit(0);
        }
    }

    public void mouseReleased(MouseEvent e) {

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
            deadScreenImage = ImageIO.read(new File("assets/UI_Elements/dieded.png"));
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
            System.exit(0);
        }

        g.drawImage(deadScreenImage, 150, 0, null);

        if (respawnOutline) {
            g.setColor(Color.green);
            g.drawRect(Game.WIDTH / 2 - 163, Game.HEIGHT / 2 - 89, 302, 88);
            g.drawRect(Game.WIDTH / 2 - 164, Game.HEIGHT / 2 - 88, 304, 88);
            g.drawRect(Game.WIDTH / 2 - 165, Game.HEIGHT / 2 - 87, 306, 88);
            g.drawRect(Game.WIDTH / 2 - 166, Game.HEIGHT / 2 - 86, 308, 88);
        } else if (optionOutline) {
            g.setColor(Color.green);
            g.drawRect(Game.WIDTH / 2 - 131, Game.HEIGHT / 2 + 7, 233, 72);
            g.drawRect(Game.WIDTH / 2 - 132, Game.HEIGHT / 2 + 8, 235, 72);
            g.drawRect(Game.WIDTH / 2 - 133, Game.HEIGHT / 2 + 9, 237, 72);
            g.drawRect(Game.WIDTH / 2 - 134, Game.HEIGHT / 2 + 10, 239, 72);
        } else if (quitOutline) {
            g.setColor(Color.green);
            g.drawRect(Game.WIDTH / 2 - 97, Game.HEIGHT / 2 + 86, 165, 52);
            g.drawRect(Game.WIDTH / 2 - 98, Game.HEIGHT / 2 + 87, 167, 52);
            g.drawRect(Game.WIDTH / 2 - 99, Game.HEIGHT / 2 + 88, 169, 52);
            g.drawRect(Game.WIDTH / 2 - 100, Game.HEIGHT / 2 + 89, 171, 52);
        }

    }

    public void respawn(Game game) {
        game.gameState = STATE.Menu;
    }
}
