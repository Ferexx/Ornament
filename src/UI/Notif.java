package UI;

import Main.Game;
import Main.ID;
import World.WorldObject;

import java.awt.*;

public class Notif extends WorldObject {

    private int messageNumber;
    public String message;
    private int duration;
    private int x, y;

    public Notif(int x, int y, int messageNumber, int duration, Game game) {
        super(x, y, ID.Notif, game, false);
        this.y = y;
        this.messageNumber = messageNumber;
        this.x = x;
        this.duration = duration;
        new CountDown(duration);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        super.render(g);
        while(!CountDown.finished) {
            g.setFont(new Font("Verdana", 1, 16));
            g.setColor(Color.GREEN);
            g.drawString(message, x, y);
        }
    }

    public void reader (int messageNumber){
        if(messageNumber == 0)
            message = "Welcome to Rahau - use A and D to move";
    }
}
