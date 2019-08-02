package Attacks;

import Main.Game;
import Main.ID;

import java.awt.*;

public abstract class RangedAttack extends Attack {

    protected double xVel;
    protected double yVel;
    protected int range;
    protected boolean rightFacing;

    public RangedAttack(int x, int y, int dmg, ID id, Game game, int range) {
        super(x, y, dmg, id, game);
        this.range = range;
    }

    public void tick() {

    }

    public void render(Graphics g) {

    }

    public Rectangle getBounds() {
        return null;
    }

    public double getVelX() {
        return xVel;
    }

    public void setVelX(double xVel) {
        this.xVel = xVel;
    }

    public double getVelY() {
        return yVel;
    }

    public void setVelY(double yVel) {
        this.yVel = yVel;
    }
}
