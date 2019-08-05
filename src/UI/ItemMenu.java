package UI;

import Main.Game;
import Main.Player;
import Main.STATE;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class ItemMenu extends MouseAdapter {

    private Game game;
    private int mX;
    private int mY;
    private boolean c1r1 = false, c1r2 = false, c1r3 = false, c1r4 = false, c2r1 = false, c2r2 = false, c2r3 = false, c2r4 = false, c3r1 = false, c3r2 = false, c3r3 = false, c3r4 = false, overNextClass = false, overPrevClass = false, overResume = false;
    private Image itemSelectImage;

    public ItemMenu(Game game) {
        this.game = game;
    }

    public void mouseMoved(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();

        if (mouseOver(mX, mY, 847, 493, 88, 23)) {
            overResume = true;
        }else if (mouseOver(mX, mY,326, 302, 177, 39)) {
            c1r1 = true;
        }else if (mouseOver(mX, mY,541, 302, 177, 39)) {
            c2r1 = true;
        }else if (mouseOver(mX, mY,756, 302, 177, 39)) {
            c3r1 = true;
        }else if (mouseOver(mX, mY,326, 350, 177, 40)) {
            c1r2 = true;
        }else if (mouseOver(mX, mY,541, 350, 177, 40)) {
            c2r2 = true;
        }else if (mouseOver(mX, mY,756, 350, 177, 40)) {
            c3r2 = true;
        }else if (mouseOver(mX, mY,326, 398, 177, 39)) {
            c1r3 = true;
        }else if (mouseOver(mX, mY,541, 398, 177, 39)) {
            c2r3 = true;
        }else if (mouseOver(mX, mY,756, 398, 177, 39)) {
            c3r3 = true;
        }else if (mouseOver(mX, mY,326, 446, 177, 39)) {
            c1r4 = true;
        }else if (mouseOver(mX, mY,541, 446, 177, 39)) {
            c2r4 = true;
        }else if (mouseOver(mX, mY,756, 446, 177, 39)) {
            c3r4 = true;
        } else {
            c1r1 = false;
            c1r2 = false;
            c1r3 = false;
            c1r4 = false;
            c2r1 = false;
            c2r2 = false;
            c2r3 = false;
            c2r4 = false;
            c3r1 = false;
            c3r2 = false;
            c3r3 = false;
            c3r4 = false;
            overNextClass = false;
            overPrevClass = false;
            overResume = false;
        }

    }

    public void mousePressed(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
        if(mouseOver(mX, mY, 847, 493, 88, 22)) {
            game.gameState = STATE.Game;
            game.removeMouseListener(this);
            game.removeMouseMotionListener(this);
        }

        //Toggles between the different abilities
        if(mouseOver(mX, mY, 326, 302, 177, 39)) {
            game.player.canEnergyAttack = true;
            game.player.canLightningAttack = false;
            game.player.canSwordAttack = false;
        } else if (mouseOver(mX, mY, 326, 350, 177, 39)) {
            game.player.canEnergyAttack = false;
            game.player.canLightningAttack = true;
            game.player.canSwordAttack = false;
        }
    }

    private boolean mouseOver(int mX, int mY, int x, int y, int width, int height) {
        if (mX > x && mX < x + width) {
            if (mY > y && mY < y + height) {
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
            itemSelectImage = ImageIO.read(new File("assets/ItemSelect.png"));
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
            System.exit(0);
        }

        g.drawImage(itemSelectImage, 150, 0, null);

        //Draws ability text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", 1, 18));
        g.drawString("Energy Attack", 330, 330);
        g.drawString("Lightning Attack", 330, 378);

        //Showing active power
        g.setColor(Color.green);
        if(game.player.canEnergyAttack) {
            g.drawRect(326, 302, 177, 38);
            g.drawRect(325, 301, 179, 40);
            g.drawRect(324, 300, 181, 42);
            g.drawRect(323, 299, 183, 44);
        } else if(game.player.canLightningAttack) {
            g.drawRect(326, 350, 177, 40);
            g.drawRect(325, 351, 179, 38);
            g.drawRect(324, 352, 181, 36);
            g.drawRect(323, 353, 183, 34);
        }

        //Showing button outline
        g.setColor(Color.blue);
        if(overResume) {
            //847, 493, 88, 23
            g.drawRect(847, 493, 87, 22);
            g.drawRect(846, 494, 89, 22);
        } else if(overNextClass) {

        } else if (overPrevClass) {

        } else if (c1r1) {
            g.drawRect(326, 302, 177, 38);
            g.drawRect(325, 301, 179, 40);
        } else if (c2r1) {
            g.drawRect(541, 302, 177, 38);
            g.drawRect(540, 301, 179, 40);
        } else if (c3r1) {
            g.drawRect(756, 302, 177, 38);
            g.drawRect(755, 301, 179, 40);
        } else if (c1r2) {
            g.drawRect(326, 350, 177, 40);
            g.drawRect(325, 351, 179, 38);
        } else if (c2r2) {
            g.drawRect(541, 350, 177, 39);
            g.drawRect(540, 349, 179, 41);
        } else if (c3r2) {
            g.drawRect(756, 350, 177, 39);
            g.drawRect(755, 349, 179, 41);
        } else if (c1r3) {
            g.drawRect(326, 398, 177, 39);
            g.drawRect(325, 397, 179, 39);
        } else if (c2r3) {
            g.drawRect(541, 398, 177, 39);
            g.drawRect(540, 397, 179, 39);
        } else if (c3r3) {
            g.drawRect(756, 398, 177, 39);
            g.drawRect(755, 397, 179, 39);
        } else if (c1r4) {
            g.drawRect(326, 446, 177, 39);
            g.drawRect(325, 445, 179, 39);
        } else if (c2r4) {
            g.drawRect(541, 446, 177, 39);
            g.drawRect(540, 445, 179, 39);
        } else if (c3r4) {
            g.drawRect(756, 446, 177, 39);
            g.drawRect(755, 445, 179, 39);
        }
    }
}