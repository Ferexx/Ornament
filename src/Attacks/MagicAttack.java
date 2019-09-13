package Attacks;

import Main.Game;
import Main.ID;

import java.awt.*;

public abstract class MagicAttack extends Attack {

    protected double magicCost;
    protected double velX;
    protected double velY;

    public MagicAttack(int x, int y, int dmg, int level, ID id, Game game, double magicCost) {
        super(x, y, dmg, level, id, game);
        this.magicCost = magicCost;
    }
    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }
    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double getMagicCost() {
        return magicCost;
    }

    public void setMagicCost(double magicCost) {
        this.magicCost = magicCost;
    }
}
