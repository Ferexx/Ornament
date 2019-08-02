package World;

import Main.Game;
import Main.GameObject;
import Main.ID;

import java.awt.*;

public abstract class WorldObject extends GameObject {

    public WorldObject(int x, int y, ID id, Game game, boolean standable) {
        super(x, y, id, game);
        isStandable = standable;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
