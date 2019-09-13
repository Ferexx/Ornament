package Attacks;

import Main.Game;
import Main.ID;

import java.awt.*;

public abstract class MeleeAttack extends Attack{

    protected double staminaCost;
    protected double velX;
    protected double velY;

    public MeleeAttack(int x, int y, int dmg, int level, ID id, Game game, int staminaCost) {
        super(x, y, dmg, level, id, game);
        this.staminaCost = staminaCost;
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
        return staminaCost;
    }

    public void setMagicCost(double magicCost) {
        this.staminaCost = staminaCost;
    }
}
