package Powerups;

import Main.Game;
import Main.ID;

import java.awt.*;

public class HealthPowerup extends Powerup {

    public HealthPowerup(int x, int y, ID id, Game game) {
        super(x, y, id, game);
    }

    public void tick() {
    }

    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, 10, 10);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }
}