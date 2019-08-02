import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Platform extends GameObject {

    public BufferedImage texture;

    public Platform(int x, int y, ID id, String textureLocation, Game game) throws IOException {
        super(x, y, id, game);

        texture = ImageIO.read(new File(textureLocation));
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        isStandable = true;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(texture, x, y, null);
        //g.fillRect(x,y,128,16);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 128, 16);
    }
}
