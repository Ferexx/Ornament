package UI;

import Main.Game;

import java.awt.*;

public class HUD {
    //Green value is a spectrum from green to red in relation to health
    public int greenValue = 255;
    private Game game;

    public HUD(Game game) {
        this.game = game;
    }

    public void tick() {

        game.player.playerHealth = Game.clamp(game.player.playerHealth, 0, 100);
        greenValue = Game.clamp(greenValue, 0, 255);

        greenValue = game.player.playerHealth * 2;
    }

    public void render(Graphics g) {
        Main.Player player = game.player;

        //Player health bar
        g.setColor(Color.gray);
        g.fillRect(15, 15, 200, 32);
        g.setColor(new Color(75, greenValue, 0));
        g.fillRect(15, 15, game.player.playerHealth * 2, 32);
        g.setColor(Color.BLACK);
        g.drawRect(15, 15, 200, 32);

        //Player Magic/Stamina bar
        if(player.hasMagic) {
            g.setColor(Color.gray);
            g.fillRect(15, 48, 200, 16);
            g.setColor(new Color(0, 125, 255));
            g.fillRect(15, 48, (int) Math.abs(game.player.playerMagic * 2), 16);
            g.setColor(Color.black);
            g.drawRect(15, 48, 200, 16);
        }
    }
}
