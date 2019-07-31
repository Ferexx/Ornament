import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;

public class EnergyAttack extends GameObject {
    public static int range = 100;
    public static int energyDamage = 2;
    private Image attackImage;
    private boolean rightFacing=true;
    private EnergyAttack self = this;

    public EnergyAttack(int x, int y, ID id, Game game, boolean facingRight) {
        super(x, y, id, game);
        height = 23;
        width = 41;
        rightFacing=facingRight;
        if (rightFacing) setVelX(+7);
        else setVelX(-7);
        fade();
    }

    public void tick() {
        x+=getVelX();
    }

    public void render(Graphics g) {
        g.setColor(new Color(52, 204, 255));
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
            return new Rectangle(x-width, y, width, height);
        } else {
            return new Rectangle(x, y, width, height);
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
                    game.handler.removeObject(self);
                    timer.cancel();
                }
            }
        },0,150);
    }

}
