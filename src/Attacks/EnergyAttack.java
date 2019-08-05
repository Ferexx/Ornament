package Attacks;
import Enemies.Enemy;
import Main.Game;
import Main.ID;
import World.WorldObject;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;

public class EnergyAttack extends MagicAttack {
    public static int energyAttackDamage = 20;
    public static int energyAttackCost = 10;
    private Image attackImage;
    private Game game;
    private EnergyAttack self = this;

    public EnergyAttack(int x, int y, Game game, boolean facingRight) {
        super(x, y, energyAttackDamage, ID.EnergyAttack, game, energyAttackCost);
        height = 23;
        width = 41;
        this.game=game;
        rightFacing=facingRight;
        //Start attack moving, then slow it down gradually
        if (rightFacing) setVelX(+7);
        else setVelX(-7);
        fade();
    }

    public void tick() {

        x+=getVelX();
        collision();
    }

    private void collision() {
        //If attack collides with an enemy
        for(int i = 0; i < game.handler.enemies.size(); i++) {
            Enemy tempEnemy = game.handler.enemies.get(i);
            if(getBounds().intersects(tempEnemy.getBounds())) {
                tempEnemy.setHealth(tempEnemy.getHealth()-this.getDmg());
                setDmg(0);
                setVelX(0);
                if(tempEnemy.getHealth()<1) {
                    game.handler.removeEnemy(tempEnemy);
                }
            }
        }

        //If attack collides with a world object
        for(int i = 0; i < game.handler.world.size(); i++) {
            WorldObject worldObject = game.handler.world.get(i);
            if(worldObject.isStandable && getBounds().intersects(worldObject.getBounds())) {
                setVelX(0);
            }
        }
    }

    public void render(Graphics g) {
        //If the attack has reached the end of its life
        if(getVelX()<1&&getVelX()>=0&&rightFacing) {
            attackImage = new ImageIcon("assets/EnergyAttackEnd.png").getImage();
            g.drawImage(attackImage, getX()+15, getY()-3, null);
            return;
        }
        else if(getVelX()>-1&&getVelX()<=0&&!rightFacing) {
            attackImage = new ImageIcon("assets/EnergyAttackEndReverse.png").getImage();
            g.drawImage(attackImage, getX(), getY()-3, null);
            return;
        }

        //Standard attack animation
        if (!rightFacing) {
            attackImage = new ImageIcon("assets/EnergyAttackReverse.gif").getImage();
            g.drawImage(attackImage, getX(), getY(), null);
        } else {
            attackImage = new ImageIcon("assets/EnergyAttack.gif").getImage();
            g.drawImage(attackImage, getX() + 20, getY(), null);
        }
    }

    public Rectangle getBounds() {
        if (!rightFacing) {
            return new Rectangle(x, y, width, height);
        } else {
            return new Rectangle(x+20, y, width, height);
        }
    }

    private void fade() {
        //Decrease velocity until we come to a standstill, then attack explodes
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(getVelX()!=0&&rightFacing) setVelX(getVelX()-0.5);
                else if(getVelX()!=0) setVelX(getVelX()+0.5);
                else {
                    game.handler.removeAttack(self);
                    timer.cancel();
                }
            }
        },0,150);
    }

}