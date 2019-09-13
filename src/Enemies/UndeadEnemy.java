package Enemies;

import Main.Game;
import Main.ID;

public abstract class UndeadEnemy extends Enemy{
    public UndeadEnemy(int x, int y, ID id, Game game, int health) {
        super(x, y, id, game, health);
    }
}
