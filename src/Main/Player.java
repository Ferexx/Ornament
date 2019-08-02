package Main;

import Attacks.EnergyAttack;
import Attacks.SwordAttack;
import Enemies.Enemy;
import Powerups.Powerup;
import World.WorldObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Player extends GameObject {
    Handler handler;
    private Timer timer;
    public BufferedImage playerImage;

    public boolean canDoubleJump = false;
    public boolean isFalling = false;
    public boolean isStanding = true;
    public boolean doubleJump = false;
    public boolean canLightningAttack = true;
    public boolean canSwordAttack = false;
    public int playerHealth = 100;
    public int playerWidth = 22;
    public int playerHeight = 54;

    private final Object lock = new Object();

    public Player(int x, int y, ID id, Handler handler, Game game) {
        super(x, y, id, game);

        this.handler = handler;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y - playerHeight, width, height);
    }

    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x, 0, Game.WIDTH - 32);

        if (playerHealth <= 0) {
            System.out.println("You died!");
            handler.removeObject(this);
        }

        if (KeyInput.rightAttack) {
            rightAttack();
        }

        if (KeyInput.leftAttack) {
            leftAttack();
        }

        collision();
    }

    private void collision() {
        synchronized (lock) {
            isStanding = false;

            //If player collides with enemy
            for(int i = 0; i < handler.enemies.size(); i++) {
                Enemy enemy = handler.enemies.get(i);
                if(getBounds().intersects(enemy.getBounds())) {
                    playerHealth-=2;
                }
            }

            //If player collides with powerup
            for(int i = 0; i < handler.powerups.size(); i++) {
                Powerup powerup = handler.powerups.get(i);
                if(getBounds().intersects(powerup.getBounds())) {
                    switch(powerup.getPowerupName()) {
                        case "doubleJump":
                            this.canDoubleJump = true;
                            handler.powerups.remove(powerup);
                            break;
                        case "health":
                            playerHealth+=25;
                            handler.powerups.remove(powerup);
                            break;
                    }
                }
            }

            //If player collides with world
            for(int i = 0; i < handler.world.size(); i++) {
                WorldObject world = handler.world.get(i);
                if(world.isStandable) {
                    if(getBounds().intersects(world.getBounds())) {
                        //If player hits top of object
                        if(getY() < world.getY() + 16) {
                            setVelY(0);
                            setY(world.getY() + 1);
                            isFalling = false;
                            isStanding = true;
                        }
                        //If player hits left side of object
                        else if (getX() < world.getX() && getY() > world.getY()+1) setVelX(0);
                        //If player hits right side of object
                        else if(getX() > world.getX() + world.getWidth()-10 && getY() > world.getY() + 1) setVelX(0);
                        //If player hits underside of object
                        else if (getY() > world.getY() + world.getHeight()) setVelY(getVelY() * -1);
                    }
                }
            }

            /*for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getID() == ID.EnergyAttack) {
                    if (handler.object.get(4).getBounds().intersects(tempObject.getBounds())) {
                        System.out.println("Hit!");
                        WeakMinion.WEAK_MINION_HEALTH -= EnergyAttack.energyAttackDamage;
                        tempObject.setVelX(0);
                        if (WeakMinion.WEAK_MINION_HEALTH <= 0) {
                            System.out.println("You killed a weak minion");
                            handler.object.remove(handler.object.get(4));
                        }
                    }
                }
                if (tempObject.getID() == ID.SwordAttack) {
                    if (handler.object.get(4).getBounds().intersects(tempObject.getBounds())) {
                        System.out.println("Hit!");
                        WeakMinion.WEAK_MINION_HEALTH -= SwordAttack.swordDamage;
                        if (WeakMinion.WEAK_MINION_HEALTH <= 0) {
                            System.out.println("You killed a weak minion");
                            handler.object.remove(handler.object.get(4));
                        }
                    }
                }
            }*/

            if (!isStanding && !isFalling) {
                isFalling = true;
                isStanding = true;
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (!isFalling) {
                            doubleJump = false;
                            setVelY(0);
                            timer.cancel();
                        } else {
                            setVelY(getVelY() + 0.5);
                        }
                    }
                }, 0, 50);
            }
        }
    }

    public void render(Graphics g) {
        try {
            playerImage = ImageIO.read(new File("assets/Knight.png"));
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
            System.exit(0);
        }
        setWidth(playerImage.getWidth());
        setHeight(playerImage.getHeight());
        if (x > 630) x = 631;
        if (x < 380) x = 379;
        if (this.velX < 0) {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-playerImage.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            playerImage = op.filter(playerImage, null);
            g.drawImage(playerImage, x, y - height, null);
        } else {
            g.drawImage(playerImage, x, y - height, null);
        }
    }

    public void jump() {
        synchronized (lock) {
            if (isFalling && canDoubleJump && !doubleJump) {
                doubleJump = true;
                setVelY(-5);
                timer.cancel();
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                        if (!isFalling) {
                            isStanding = true;
                            doubleJump = false;
                            setVelY(0);
                            cancel();
                        } else {
                            setVelY(getVelY() + 0.5);
                        }
                    }
                }, 0, 50);
            } else if (!isFalling) {
                isFalling = true;
                setVelY(-5);
                timer.cancel();
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (!isFalling) {
                            isStanding = true;
                            setVelY(0);
                            cancel();
                        } else {
                            setVelY(getVelY() + 0.5);
                        }
                    }
                }, 0, 50);
            }
        }
    }

    public void leftAttack() {
        if (canLightningAttack) {
            handler.addAttack(new EnergyAttack(x - EnergyAttack.width, y - 38, game, false));
        } else if (canSwordAttack) {
            handler.addAttack(new SwordAttack(x - SwordAttack.range, y - 38, ID.SwordAttack, game));
        } else {
            System.out.println("You cannot attack at this time");
        }
        KeyInput.leftAttack = false;
    }

    public void rightAttack() {
        if (canLightningAttack) {
            handler.addAttack(new EnergyAttack(x, y - 38, game, true));
        } else if (canSwordAttack) {
            handler.addAttack(new SwordAttack(SwordAttack.range, y - 38, ID.SwordAttack, game));
        } else {
            System.out.println("You cannot attack at this time");
        }
        KeyInput.rightAttack = false;
    }
}