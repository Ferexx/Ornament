package UI;

import Main.Game;
import Main.GameObject;
import Main.ID;

import java.awt.*;

public class Background extends GameObject {
    public Image backgroundImage;
    public Image backgroundImage2;
    public int b1x = 0;
    public int b2x;

    public Background(Game game) {
        super(game);

        backgroundImage = Toolkit.getDefaultToolkit().createImage("assets/TownBackground.png");
        backgroundImage2 = Toolkit.getDefaultToolkit().createImage("assets/TownBackground.png");
    }

    public void tick() {
        for (int i = 0; i < game.handler.object.size(); i++) {
            GameObject tempObject = game.handler.object.get(i);
            if (tempObject.getID() == ID.Player) {
                if (tempObject.getX() > 630 && tempObject.getVelX() > 0) {
                    scrollLTR();
                }
                if (tempObject.getX() < 380 && tempObject.getVelX() < 0) {
                    scrollRTL();
                }
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(backgroundImage, b1x, 0, null);
        g.drawImage(backgroundImage2, b2x, 0, null);
    }

    public Rectangle getBounds() {
        return null;
    }

    public void scrollLTR() {
        if (b1x < 0) b2x = b1x + 1280;
        if (b2x < 0) b1x = b2x + 1280;
        updatePosition();
    }

    public void scrollRTL() {
        if (b1x > -10) b2x = b1x - 1280;
        if (b2x > -10) b1x = b2x - 1280;
        updatePosition();
    }

    private void updatePosition() {
        b1x -= game.player.getVelX();
        b2x -= game.player.getVelX();
        for (int i = 0; i < game.handler.object.size(); i++) {
            GameObject tempObject = game.handler.object.get(i);
            if (tempObject.getID() != ID.Player) {
                tempObject.setX(tempObject.getX() - (int) game.player.getVelX());
            }
        }
    }
}
