import java.awt.*;

public class SwordAttack extends GameObject {

    Handler handler;
    public static int range = 25;

    public SwordAttack(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    public void tick() {
        if(Player.rightAttackFade) {
            fade();
        } else if (Player.leftAttackFade) {
            fade();
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(52, 204, 255));
        if(KeyInput.leftDown) {
            g.fillRect(Game.player.getX() - range, Game.player.getY() - 35, range, 10);
        }
        else {
            g.fillRect(Game.player.getX() + 20, Game.player.getY() - 35, range, 10);
        }
    }

    public Rectangle getBounds() {
        if(KeyInput.leftDown) {
            return new Rectangle(Game.player.getX() - range, Game.player.getY() - 35, range, 24);
        }
        else {
            return new Rectangle(Game.player.getX() + 20, Game.player.getY() - 35, range, 24);
        }
    }

    public void fade() {
        for(int i = 0; i < handler.object.size(); i++) {
            handler.object.remove(this);
        }
        Player.rightAttackFade = false;
        Player.leftAttackFade = false;
    }


}
