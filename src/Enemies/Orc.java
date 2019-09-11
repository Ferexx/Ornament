package Enemies;

import Main.Game;
import Main.ID;

public class Orc extends Enemy {
    public Orc(int x, int y, ID id, Game game, int health) {
        super(x, y, id, game, 1000);
    }
}
