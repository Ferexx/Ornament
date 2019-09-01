package UI;

import Main.Game;
import Main.Handler;
import Main.ID;
import World.Ground;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AlertEngine {

    private Handler handler;
    private Game game;

    public AlertEngine(File levelFolder, Game game) {
        this.game = game;
        this.handler = game.handler;
        File notificationsFile = new File(levelFolder + "/Notifications.csv");
        parseNotifications(notificationsFile);
    }

    //Reads the Notifications.csv file
    private void parseNotifications(File notificationsFile) {
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(notificationsFile));
            while((line = br.readLine())!=null) {
                String[] values = line.split(",");
                //do things
            }
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


}
