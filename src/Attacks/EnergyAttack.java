package Attacks;
import Main.Game;
import Main.ID;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;

public class EnergyAttack extends MagicAttack {
    public static int energyAttackDamage = 2;
    public static int energyAttackCost = 1;
    private Image attackImage;
    private Game game;
    private EnergyAttack self = this;

    public EnergyAttack(int x, int y, Game game, boolean facingRight) {
        super(x, y, energyAttackDamage, ID.EnergyAttack, game, energyAttackCost);
        height = 23;
        width = 41;
        this.game=game;
        rightFacing=facingRight;
        if (rightFacing) setVelX(+7);
        else setVelX(-7);
        fade();
    }

    public void tick() {
        x+=getVelX();
    }

    public void render(Graphics g) {
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

    public void fade() {
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
