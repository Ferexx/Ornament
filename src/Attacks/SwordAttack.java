package Attacks;

import Main.Game;
import Main.ID;
import Main.KeyInput;

import java.awt.*;
import java.security.Key;

public class SwordAttack extends MeleeAttack {
    public static int range = 42;
    public static int swordDamage = 10;

    public SwordAttack(int x, int y, ID id, Game game) {
        super(x, y, swordDamage, id, game, range);

        System.out.println("yoyo");

        fade();

    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(new Color(52, 204, 255));
        if (KeyInput.leftDown) {
            g.fillRect(game.player.getX() - range, game.player.getY() - 35, range, 5);
        } else {
            g.fillRect(game.player.getX() + 20, game.player.getY() - 35, range, 5);
        }
    }

    public Rectangle getBounds() {
        if (KeyInput.leftDown) {
            return new Rectangle(game.player.getX() - range, game.player.getY() - 35, range, 24);
        } else {
            return new Rectangle(game.player.getX() + 20, game.player.getY() - 35, range, 24);
        }
    }

    public void fade() {
        if(!KeyInput.rightAttack || !KeyInput.leftAttack) {
            System.out.println("heere");
            game.handler.removeAttack(this);
        }
    }
}