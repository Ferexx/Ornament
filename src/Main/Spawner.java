package Main;

import Enemies.WeakMinion;
import Powerups.DoubleJumpPowerup;
import Powerups.HealthPowerup;
import UI.Background;
import World.Ground;
import World.Platform;

import java.io.IOException;

public class Spawner {
    private Game game;

    public Spawner(Game game) {
        this.game = game;
    }

    public void spawn() {
        //will be used to spawn in map loading and additional objects
    }

    public void spawnTestGame() throws IOException {
        Handler handler = game.handler;

        handler.addObject(new Background(game));
        handler.addWorldObject(new Platform(400, 500, ID.Platform, "assets/GrassPlatform.png", game));


        handler.addWorldObject(new Ground(0, 580, ID.Ground, game));
        for(int i = 800; i < 6001; i += 800) {
            handler.addWorldObject(new Platform(i, 400, ID.Platform, "assets/GrassPlatform.png", game));
        }

        handler.addObject(game.player);

        handler.addPowerup(new DoubleJumpPowerup(Game.WIDTH / 2, 530, ID.DoubleJumpPowerup, game, "doubleJump"));
        for (int i = 600; i < 5601; i = i + 500) {
            handler.addEnemy(new WeakMinion(i, 580, ID.WeakMinion, game));
            handler.addPowerup(new HealthPowerup(i, 530, ID.HealthPowerup, game, "health"));
        }
    }
}
