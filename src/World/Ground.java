package World;

import Main.Game;
import Main.ID;

import java.awt.*;

public class Ground extends WorldObject {
    public Ground(ID id, Game game, int width) {
        super(0, 580, id, game, true);
        setWidth(width);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, 5);
    }

    public void tick() {

    }

    public void render(Graphics g) {

    }
}
