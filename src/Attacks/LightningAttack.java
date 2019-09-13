package Attacks;

import Enemies.Enemy;
import Main.Game;
import Main.ID;
import Main.Player;
import World.WorldObject;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static Main.KeyInput.*;

public class LightningAttack extends MagicAttack{
    //TODO make these not static and have reference to them from Enemy
    public static int lightningAttackDamage = 3;
    public static int lightningAttackCost = 1;
    private Image attackImage;
    private Game game;
    private LightningAttack self = this;
    private int level;

    public LightningAttack(int x, int y, int level, Game game, boolean facingRight) {
        super(x, y, lightningAttackDamage, level, ID.LightningAttack, game, lightningAttackCost);
        this.level = level;
        height = 16;
        if(level == 1) {
            lightningAttackDamage = 1;
            width = 154;
        } else if(level == 2) {
            lightningAttackDamage = 2;
            width = 256;
        } else if(level == 3) {
            lightningAttackDamage = 3;
            width = 512;
        }
        this.game=game;
        rightFacing=facingRight;
    }

    public void tick() {

        collision();
        game.player.playerMagic -= LightningAttack.lightningAttackCost;
        if(game.player.playerMagic < lightningAttackCost) {
            fade();
        } else if(!leftDown && !rightDown) {
            fade();
        }

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
        //Standard attack animation
        if (!rightFacing) {
            if(level == 1) {
                attackImage = new ImageIcon("assets/Attacks/lightningAttack.gif").getImage();
            } else if(level == 2) {
                attackImage = new ImageIcon("assets/Attacks/lightningAttackExtended.gif").getImage();
            } else {
                attackImage = new ImageIcon("assets/Attacks/lightningAttackMaxLevel.gif").getImage();
            }

            g.drawImage(attackImage, game.player.getX() - width, game.player.getY()-38, null);
        } else {
            if(level == 1) {
                attackImage = new ImageIcon("assets/Attacks/lightningAttack.gif").getImage();
            } else if(level == 2) {
                attackImage = new ImageIcon("assets/Attacks/lightningAttackExtended.gif").getImage();
            } else {
                attackImage = new ImageIcon("assets/Attacks/lightningAttackMaxLevel.gif").getImage();
            }

            g.drawImage(attackImage, game.player.getX() + 25, game.player.getY()-38, null);
        }
    }

    public Rectangle getBounds() {
        if (!rightFacing) {
            return new Rectangle(game.player.getX() - width, game.player.getY()-38, width, height);
        } else {
            return new Rectangle(game.player.getX() + 25, game.player.getY()-38, width, height);
        }
    }

    private void fade() {
        //hold until gif finished
        game.handler.removeAttack(this);

    }
}

