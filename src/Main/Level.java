package Main;


import Enemies.WeakMinion;
import Powerups.DoubleJumpPowerup;
import Powerups.HealthPowerup;
import Powerups.MagicPowerup;
import UI.Background;
import World.Ground;
import World.NPCs.NPC;
import World.NPCs.Villager;
import World.Platform;

import java.io.*;

import static Main.ID.NPC;

public class Level {

    private File levelFolder, enemiesFile, worldFile, powerupsFile, playerFile, backgroundFile, npcFile;
    private Handler handler;
    private Game game;
    
    public Level(File levelFolder, Game game) {
        this.game = game;
        this.handler = game.handler;
        this.levelFolder = levelFolder;
        this.backgroundFile = new File(levelFolder+"/Background.csv");
        parseBackground(this.backgroundFile);
        this.playerFile = new File(levelFolder+"/Player.csv");
        parsePlayer(playerFile);
        this.enemiesFile = new File(levelFolder+"/Enemies.csv");
        parseEnemies(enemiesFile);
        this.worldFile = new File(levelFolder+"/worldObjects.csv");
        parseWorldObjects(worldFile);
        this.powerupsFile = new File(levelFolder+"/Powerups.csv");
        parsePowerups(powerupsFile);
        this.npcFile = new File(levelFolder+"/NPCs.csv");
        parseNPCs(npcFile);
    }

    private void parseBackground(File backgroundFile) {
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(backgroundFile));
            while((line = br.readLine())!=null) {
                String[] values = line.split(",");
                handler.addWorldObject(new Ground(ID.Ground, game, Integer.parseInt(values[0])));
                handler.addObject(new Background(game, values[1]));
            }
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void parsePlayer(File backgroundFile) {
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(backgroundFile));
            while((line = br.readLine())!=null) {
                String[] values = line.split(",");
                handler.addObject(game.player);
                game.player.setX(Integer.parseInt(values[1]));
                game.player.setY(Integer.parseInt(values[2]));
            }
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void parseEnemies(File enemiesFile) {
        String line= "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(enemiesFile));
            while((line = br.readLine())!=null) {
                String[] values = line.split(",");
                switch(values[0]) {
                    case "ID.WeakMinion":
                        handler.addEnemy(new WeakMinion(Integer.parseInt(values[1]), Integer.parseInt(values[2]), ID.WeakMinion, game));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseWorldObjects(File worldFile) {
        String line= "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(worldFile));
            while((line = br.readLine())!=null) {
                String[] values = line.split(",");
                switch(values[0]) {
                    case "ID.Platform":
                        handler.addWorldObject(new Platform(Integer.parseInt(values[1]), Integer.parseInt(values[2]), ID.Platform, values[3], game));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parsePowerups(File powerupsFile) {
        String line= "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(powerupsFile));
            while((line = br.readLine())!=null) {
                String[] values = line.split(",");
                switch(values[0]) {
                    case "ID.DoubleJumpPowerup":
                        handler.addPowerup(new DoubleJumpPowerup(Integer.parseInt(values[1]), Integer.parseInt(values[2]), ID.DoubleJumpPowerup, game));
                        break;
                    case "ID.HealthPowerup":
                        handler.addPowerup(new HealthPowerup(Integer.parseInt(values[1]), Integer.parseInt(values[2]), ID.HealthPowerup, game));
                        break;
                    case "ID.MagicPowerup":
                        handler.addPowerup(new MagicPowerup(Integer.parseInt(values[1]), Integer.parseInt(values[2]), ID.MagicPowerup, game));
                        break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseNPCs(File npcFile) {
        String line= "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(npcFile));
            while((line = br.readLine())!=null) {
                String[] values = line.split(",");
                switch(values[0]) {
                    case "ID.Villager":
                        handler.addNPC(new Villager(Integer.parseInt(values[1]), Integer.parseInt(values[2]), ID.NPC, values[3], game));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
