package Attacks;

import Enemies.Enemy;
import Main.Game;
import Main.ID;
import Main.KeyInput;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static Main.KeyInput.leftDown;
import static Main.KeyInput.rightDown;

public class SwordAttack extends MeleeAttack {
    public static int swordDamage = 25;
    public static int swordCost = 5;
    private Image attackImage;
    private Game game;
    private SwordAttack self = this;

    public SwordAttack(int x, int y, Game game, boolean facingRight) {
        super(x, y, swordDamage, ID.SwordAttack, game, swordCost);
        height = 50;
        width = 75;
        this.game=game;
        rightFacing=facingRight;
        fade();

    }

    public void tick() {

        collision();
    }

    private void collision() {
        //If attack collides with an enemy
        for(int i = 0; i < game.handler.enemies.size(); i++) {
            Enemy tempEnemy = game.handler.enemies.get(i);
            if(getBounds().intersects(tempEnemy.getBounds())) {
                tempEnemy.setHealth(tempEnemy.getHealth()-this.getDmg());
                if(tempEnemy.getHealth()<1) game.handler.removeEnemy(tempEnemy);
            }
        }
    }

    public void render(Graphics g) {

    }

    public Rectangle getBounds() {
        if (KeyInput.leftDown) {
            return new Rectangle(game.player.getX() - width, game.player.getY() - 35, width, 24);
        } else {
            return new Rectangle(game.player.getX() + 20, game.player.getY() - 35, width, 24);
        }
    }

    public void fade() {
        //Decrease velocity until we come to a standstill, then attack explodes
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                game.handler.removeAttack(returnSelf());
                game.player.isAttacking = false;
                timer.cancel();
            }
        },375,375);
    }
    private SwordAttack returnSelf() {
        return this;
    }
}