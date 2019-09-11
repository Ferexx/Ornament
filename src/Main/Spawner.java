package Main;

import Enemies.Goblin;
import Powerups.DoubleJumpPowerup;
import Powerups.HealthPowerup;
import Powerups.MagicPowerup;
import UI.Background;
import World.Ground;
import World.Platform;

import java.io.File;
import java.io.IOException;

public class Spawner {
    private Game game;

    public Spawner(Game game) {
        this.game = game;
    }

    public void spawn(File level) {
        new Level(level, game);
    }

    public void spawnTestGame() throws IOException {
        Handler handler = game.handler;

        handler.addObject(new Background(game, "assets/TownBackground.png"));
        handler.addWorldObject(new Platform(400, 500, ID.Platform, "assets/GrassPlatform.png", game));


        handler.addWorldObject(new Ground(ID.Ground, game, 80000));
        for(int i = 800; i < 6001; i += 800) {
            handler.addWorldObject(new Platform(i, 400, ID.Platform, "assets/GrassPlatform.png", game));
        }

        handler.addObject(game.player);

        handler.addPowerup(new DoubleJumpPowerup(Game.WIDTH / 2, 530, ID.DoubleJumpPowerup, game));
        for (int i = 600; i < 5601; i = i + 500) {
            handler.addEnemy(new Goblin(i, 580, ID.WeakMinion, game));
            handler.addPowerup(new HealthPowerup(i, 530, ID.HealthPowerup, game));
            handler.addPowerup(new MagicPowerup(i+100, 530, ID.MagicPowerup, game));
        }
    }
}
