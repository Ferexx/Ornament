package Main;


import Enemies.WeakMinion;
import UI.Background;
import World.Ground;

import java.io.*;

public class Level {

    File levelFolder, enemiesFile, worldFile, powerupsFile, playerFile, backgroundFile;
    Handler handler;
    Game game;
    
    public Level(String levelFolder, Game game) {
        this.game = game;
        this.handler = game.handler;
        this.levelFolder = new File("levelFolder");
        this.backgroundFile = new File(levelFolder+"\\Background.csv");
        parseBackground(this.backgroundFile);
        this.playerFile = new File(levelFolder+"\\Player.csv");
        parsePlayer(playerFile);
        this.enemiesFile = new File(levelFolder+"\\Enemies.csv");
        parseEnemies(enemiesFile);
    }

    public void parseBackground(File backgroundFile) {
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

    public void parsePlayer(File backgroundFile) {
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
                for(int i=0;i<values.length;i++) {
                    switch(values[0]) {
                        case "ID.WeakMinion":
                            handler.addEnemy(new WeakMinion(Integer.parseInt(values[1]), Integer.parseInt(values[2]), ID.WeakMinion, game));
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
