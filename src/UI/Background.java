package UI;

import Attacks.Attack;
import Enemies.Enemy;
import Main.Game;
import Main.GameObject;
import Main.ID;
import Main.STATE;
import Powerups.Powerup;
import World.WorldObject;

import java.awt.*;

public class Background extends GameObject {
    public Image backgroundImage;
    public Image backgroundImage2;
    public int b1x = 0;
    public int b2x;

    public Background(Game game, String textureLocation) {
        super(game);

        backgroundImage = Toolkit.getDefaultToolkit().createImage(textureLocation);
        backgroundImage2 = Toolkit.getDefaultToolkit().createImage(textureLocation);
    }

    public void tick() {
        for (int i = 0; i < game.handler.object.size(); i++) {
            GameObject tempObject = game.handler.object.get(i);
            if (tempObject.getID() == ID.Player) {
                if (tempObject.getX() > 630 && tempObject.getVelX() > 0) {
                    scrollLTR();
                }
                if (tempObject.getX() < 380 && tempObject.getVelX() < 0 && game.player.absoluteX >= 410) {
                    scrollRTL();
                }
            }
        }
        if(game.gameState == STATE.Menu || game.gameState == STATE.MenuOptions) {
            menuScroll();
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
        if (b1x < 0) b2x = b1x + 1270;
        if (b2x < 0) b1x = b2x + 1270;
        updatePosition();
    }

    public void scrollRTL() {
        if (b1x > -10) b2x = b1x - 1270;
        if (b2x > -10) b1x = b2x - 1270;
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
        for (int i = 0; i < game.handler.attacks.size(); i++) {
            Attack attackObject = game.handler.attacks.get(i);
            attackObject.setX(attackObject.getX() - (int) game.player.getVelX());
        }
        for (int i = 0; i < game.handler.enemies.size(); i++) {
            Enemy enemyObject = game.handler.enemies.get(i);
            enemyObject.setX(enemyObject.getX() - (int) game.player.getVelX());
        }
        for (int i = 0; i < game.handler.powerups.size(); i++) {
            Powerup powerupObject = game.handler.powerups.get(i);
            powerupObject.setX(powerupObject.getX() - (int) game.player.getVelX());
        }
        for (int i = 0; i < game.handler.world.size(); i++) {
            WorldObject worldObject = game.handler.world.get(i);
            worldObject.setX(worldObject.getX() - (int) game.player.getVelX());
        }
    }

    public void menuScroll() {
        if (b1x < 0) b2x = b1x + 1270;
        if (b2x < 0) b1x = b2x + 1270;
        b1x--;
        b2x--;
    }
}
