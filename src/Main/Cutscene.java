package Main;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.MalformedURLException;

public class Cutscene {

    public Cutscene(String cutsceneFile, Game game) {
        File file = new File(cutsceneFile);
        try {
            String url = file.toURI().toURL().toString();

            JFXPanel jfx = new JFXPanel();
            game.window.frame.getContentPane().add(jfx);
            game.window.frame.setVisible(true);

            Media media = new Media(url);
            MediaPlayer player = new MediaPlayer(media);
            player.setAutoPlay(true);

            Group root = new Group();
            Scene scene = new Scene(root);

            MediaView viewer = new MediaView(player);
            ((Group)scene.getRoot()).getChildren().add(viewer);
            jfx.setScene(scene);
        }
        catch(MalformedURLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
