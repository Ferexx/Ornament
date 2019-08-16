package World.NPCs;

import Main.Game;
import Main.GameObject;
import Main.ID;

import java.awt.*;

public abstract class NPC extends GameObject {
    protected Game game;
    protected ID id;

    public NPC(int x, int y, ID id, Game game) {
        super(x, y, id, game);
    }

    public void render(Graphics g) {

    }

    public void tick() {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
