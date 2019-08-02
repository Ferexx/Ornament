package Attacks;

import Main.Game;
import Main.ID;

import java.awt.*;

public abstract class Attack {
    protected int x;
    protected int y;
    protected int dmg;
    protected Main.ID id;
    protected Game game;
    public static int height;
    public static int width;
    protected boolean rightFacing;

    public Attack(int x, int y, int dmg, ID id, Game game) {
        this.x = x;
        this.y = y;
        this.dmg = dmg;
        this.id = id;
        this.game = game;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public int getDmg() {
        return dmg;
    }

    public void setID(ID id) {
        this.id = id;
    }

    public ID getID() {
        return id;
    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
