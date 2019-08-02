package Powerups;

import Main.Game;
import Main.ID;

import java.awt.*;

public class DoubleJumpPowerup extends Powerup {

    public DoubleJumpPowerup(int x, int y, ID id, Game game, String name) {
        super(x, y, id, game, name);
    }

    public void tick() {
        
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 10, 10);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }
}
