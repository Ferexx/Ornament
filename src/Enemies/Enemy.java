package Enemies;

import Main.Game;
import Main.GameObject;
import Main.ID;

import java.awt.*;

public abstract class Enemy extends GameObject {
    protected int health;
    protected int attackDamage;
    protected int width;
    protected int height;
    protected Game game;
    protected ID id;

    public Enemy(int x, int y, ID id, Game game, int health) {
        super(x, y, id, game);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public ID getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void tick() {

    }
    public void render(Graphics g) {

    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
