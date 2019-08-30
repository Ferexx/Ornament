package World;

import Main.Game;
import Main.ID;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Door extends WorldObject{

    private BufferedImage texture;

    public Door(int x, int y, ID id, String textureLocation, Game game) throws IOException {
        super(x, y, id, game, true);
        isStandable = false;

        texture = ImageIO.read(new File(textureLocation));
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(texture, x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }
}
