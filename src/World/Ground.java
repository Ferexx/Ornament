package World;

import Main.Game;
import Main.ID;

import java.awt.*;

public class Ground extends WorldObject {
    public Ground(int x, int y, ID id, Game game) {
        super(x, y, id, game, true);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 80000, 5);
    }

    public void tick() {

    }

    public void render(Graphics g) {

    }
}
