package Powerups;

import Main.Game;
import Main.ID;

import java.awt.*;

public class HealthPowerup extends Powerup {
    private boolean visible = true;

    public HealthPowerup(int x, int y, ID id, Game game, String name) {
        super(x, y, id, game, name);
    }

    public void tick() {
    }

    public void render(Graphics g) {
        if (visible) {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, 10, 10);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }
}