package Powerups;

import Main.Game;
import Main.GameObject;
import Main.ID;

import java.awt.*;

public abstract class Powerup extends GameObject {

    protected String powerupName;

    public Powerup(int x, int y, ID id, Game game, String name) {
        super(x, y, id, game);
        this.powerupName = name;
    }

    public void tick() {

    }

    public void render(Graphics g) {

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public String getPowerupName() {
        return powerupName;
    }

    public void setPowerupName(String powerupName) {
        this.powerupName = powerupName;
    }
}
