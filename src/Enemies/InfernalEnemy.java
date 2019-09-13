package Enemies;

import Main.Game;
import Main.ID;

public abstract class InfernalEnemy extends Enemy {
    public InfernalEnemy(int x, int y, ID id, Game game, int health) {
        super(x, y, id, game, health);
    }
}
