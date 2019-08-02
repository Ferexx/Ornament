package Attacks;

import Main.Game;
import Main.ID;

import java.awt.*;

public class MeleeAttack extends Attack{

    protected int range;

    public MeleeAttack(int x, int y, int dmg, ID id, Game game, int range) {
        super(x, y, dmg, id, game);
        this.range = range;
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
