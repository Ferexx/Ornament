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

public class Menu extends MouseAdapter {

    private BufferedImage menuImage;
    private Game game;
    private Spawner spawn;
    private static boolean playOutline = false, optionOutline = false, quitOutline = false;

    private int mX;
    private int mY;

    public Menu(Game game) {
        this.game = game;

        game.addMouseListener(this);
        game.addMouseMotionListener(this);

        spawn = new Spawner(game);
    }

    public void mouseMoved(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();

        if (mouseOver(mX, mY, Game.WIDTH / 2 - 163, Game.HEIGHT / 2 - 89, 303, 89)) {
            playOutline = true;
            optionOutline = false;
            quitOutline = false;
        } else if (mouseOver(mX, mY, Game.WIDTH / 2 - 131, Game.HEIGHT / 2 + 7, 234, 73)) {
            playOutline = false;
            optionOutline = true;
            quitOutline = false;
        } else if (mouseOver(mX, mY, Game.WIDTH / 2 - 97, Game.HEIGHT / 2 + 86, 166, 53)) {
            playOutline = false;
            optionOutline = false;
            quitOutline = true;
        } else {
            playOutline = false;
            optionOutline = false;
            quitOutline = false;
        }

    }

    public void mousePressed(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();

        if (mouseOver(mX, mY, Game.WIDTH / 2 - 163, Game.HEIGHT / 2 - 89, 303, 89)) {
            //Play game
            game.gameState = STATE.Game;
            game.removeMouseListener(this);
            game.removeMouseMotionListener(this);

            spawn.spawn(new File("Levels/Level1"));
        }

        if (mouseOver(mX, mY, Game.WIDTH / 2 - 131, Game.HEIGHT / 2 + 7, 234, 73)) {
            //Options
        }

        if (mouseOver(mX, mY, Game.WIDTH / 2 - 97, Game.HEIGHT / 2 + 86, 166, 53)) {
            //quit
            System.exit(0);
        }

        //If the user selects load a level, then give them a file chooser dialog, and load the according level
        if(mouseOver(mX, mY, Game.WIDTH / 2 + 148, Game.HEIGHT / 2 - 89, 66, 34)) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.setDialogTitle("Choose Level Folder");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(game.window);
            if (result == JFileChooser.APPROVE_OPTION) {
                game.gameState = STATE.Game;
                game.removeMouseListener(this);
                game.removeMouseMotionListener(this);
                game.spawner.spawn(fileChooser.getSelectedFile());
            }
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
        g.setColor(Color.black);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        try {
            menuImage = ImageIO.read(new File("assets/menuImage.png"));
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
            System.exit(0);
        }

        g.drawImage(menuImage, 150, 0, null);

        g.setColor(Color.green);
        if (playOutline) {
            g.drawRect(Game.WIDTH / 2 - 163, Game.HEIGHT / 2 - 89, 302, 88);
            g.drawRect(Game.WIDTH / 2 - 164, Game.HEIGHT / 2 - 90, 304, 90);
            g.drawRect(Game.WIDTH / 2 - 165, Game.HEIGHT / 2 - 91, 306, 92);
            g.drawRect(Game.WIDTH / 2 - 166, Game.HEIGHT / 2 - 92, 308, 94);
        } else if (optionOutline) {
            g.drawRect(Game.WIDTH / 2 - 131, Game.HEIGHT / 2 + 7, 233, 72);
            g.drawRect(Game.WIDTH / 2 - 132, Game.HEIGHT / 2 + 6, 235, 74);
            g.drawRect(Game.WIDTH / 2 - 133, Game.HEIGHT / 2 + 5, 237, 76);
            g.drawRect(Game.WIDTH / 2 - 134, Game.HEIGHT / 2 + 4, 239, 78);
        } else if (quitOutline) {
            g.drawRect(Game.WIDTH / 2 - 97, Game.HEIGHT / 2 + 86, 165, 52);
            g.drawRect(Game.WIDTH / 2 - 98, Game.HEIGHT / 2 + 85, 167, 54);
            g.drawRect(Game.WIDTH / 2 - 99, Game.HEIGHT / 2 + 84, 169, 56);
            g.drawRect(Game.WIDTH / 2 - 100, Game.HEIGHT / 2 + 83, 171, 58);
        } else if(mouseOver(mX, mY, Game.WIDTH / 2 + 148, Game.HEIGHT / 2 - 89, 66, 34)) {
            g.drawRect(Game.WIDTH / 2 + 148, Game.HEIGHT / 2 - 89, 66, 34);
            g.drawRect(Game.WIDTH / 2 + 147, Game.HEIGHT / 2 - 90, 68, 36);
            g.drawRect(Game.WIDTH / 2 + 146, Game.HEIGHT / 2 - 91, 70, 38);
            g.drawRect(Game.WIDTH / 2 + 145, Game.HEIGHT / 2 - 92, 72, 40);
        }

    }
}
