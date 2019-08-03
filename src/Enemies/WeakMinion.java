package Enemies;

import Main.Game;
import Main.ID;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeakMinion extends Enemy {

    private BufferedImage minionImage;

    public WeakMinion(int x, int y, ID id, Game game) {
        super(x, y, id, game, 100);

        velX = 2;
        velY = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y - 32, width, height);
    }

    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Game.HEIGHT - 140) velY *= -1;

        if (x <= 0 || x >= Game.WIDTH - 42) velX *= -1;

    }

    public void render(Graphics g) {
        try {
            minionImage = ImageIO.read(new File("assets/WeakMinion.png"));
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
            System.exit(0);
        }
        setWidth(minionImage.getWidth());
        setHeight(minionImage.getHeight());
        super.render(g);

        //Flip image if minion is traveling RTL
        if (this.velX < 0) {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-minionImage.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            minionImage = op.filter(minionImage, null);
            g.drawImage(minionImage, x, y - height, null);
        } else {
            g.drawImage(minionImage, x, y - height, null);
        }
    }
}
