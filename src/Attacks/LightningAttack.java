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
    public static int lightningAttackDamage = 2;
    public static int lightningAttackCost = 1;
    private Image attackImage;
    private Game game;
    private LightningAttack self = this;

    public LightningAttack(int x, int y, Game game, boolean facingRight) {
        super(x, y, lightningAttackDamage, ID.LightningAttack, game, lightningAttackCost);
        height = 16;
        width = 154;
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
            attackImage = new ImageIcon("assets/Attacks/lightningAttack.gif").getImage();
            g.drawImage(attackImage, game.player.getX() - width, game.player.getY()-38, null);
        } else {
            attackImage = new ImageIcon("assets/Attacks/lightningAttack.gif").getImage();
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

