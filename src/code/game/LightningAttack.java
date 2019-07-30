import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LightningAttack extends GameObject {
    public static int range = 100;
    public static int lightningDamage = 2;
    private Image attackImage;

    public LightningAttack(int x, int y, ID id, Game game) {
        super(x, y, id, game);
        attackImage = new ImageIcon("assets/LightningAttack.gif").getImage();
        height = 23;
        width = 41;
    }

    public void tick() {
        if (game.player.rightAttackFade) {
            fade();
        } else if (game.player.leftAttackFade) {
            fade();
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(52, 204, 255));
        if (KeyInput.leftDown) {
            /*AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-attackImage.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            attackImage = op.filter(attackImage, null);*/
            g.drawImage(attackImage, game.player.getX() - width, game.player.getY() - 50, null);
        } else {
            g.drawImage(attackImage, game.player.getX() + 20, game.player.getY() - 50, null);
        }
    }

    public Rectangle getBounds() {
        if (KeyInput.leftDown) {
            return new Rectangle(x-width, y, width, height);
        } else {
            return new Rectangle(x, y, width, height);
        }
    }

    public void fade() {
        game.handler.removeObject(this);
        game.player.rightAttackFade = false;
        game.player.leftAttackFade = false;
    }

}
