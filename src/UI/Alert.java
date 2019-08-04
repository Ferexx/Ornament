package UI;

import Main.Game;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Alert {

    private String message;
    private Color text;
    private int x, y;
    private int t;
    private Game game;

    public Alert(Game game, String string, Color color, int textX, int textY, int duration) {
        this.game = game;
        this.message = string;
        this.text = color;
        this.x = textX;
        this.y = textY;
        this.t = duration;

        fade();
    }

    public void render(Graphics g) {
        g.setColor(text);
        g.setFont(new Font("Verdana", Font.ITALIC + Font.BOLD, 24));
        g.drawString(message, x, y);

    }

    public void tick() {

    }

    public void fade() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                    game.alert = null;
            }
        },0,t);
    }

}
