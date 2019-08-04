package Powerups;

import Main.Game;
import Main.GameObject;
import Main.ID;

import java.awt.*;

public abstract class Powerup extends GameObject {

    public Powerup(int x, int y, ID id, Game game) {
        super(x, y, id, game);
    }

    public void tick() {

    }

    public void render(Graphics g) {

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
