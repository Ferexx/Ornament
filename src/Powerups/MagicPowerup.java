package Powerups;

import Main.Game;
import Main.ID;

import java.awt.*;

public class MagicPowerup extends Powerup {

    public MagicPowerup(int x, int y, ID id, Game game, String name) {
        super(x, y, id, game, name);
    }

    public void tick() {
    }

    public void render(Graphics g) {
        g.setColor(new Color(0, 125, 255));
        g.fillRect(x, y, 10, 10);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }
}
