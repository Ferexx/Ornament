package World.NPCs;

import Main.Game;
import Main.ID;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class Villager extends NPC {

    private BufferedImage villagerImage;

    public Villager(int x, int y, ID id, String textureLocation, Game game) {
        super(x, y, id, game);

        try {
            System.out.println(textureLocation);
            villagerImage = ImageIO.read(new File(textureLocation));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(villagerImage, x, y - height, null);
    }
}
