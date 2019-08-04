package Main;

import Attacks.EnergyAttack;
import Attacks.LightningAttack;
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
    private Handler handler;
    private Timer timer;
    private BufferedImage playerImage;

    private boolean godMode = true;

    //Attacks
    private boolean canEnergyAttack = true;
        private boolean energyBlocked = false;
    private boolean canSwordAttack = false;
    private boolean canLightningAttack = false;

    private boolean canDoubleJump = false;
    boolean isFalling = false;
    private boolean isStanding = true;
    private boolean doubleJump = false;
    public boolean hasStamina = false;
    public boolean hasMagic = true;
    public int playerHealth = 100000;
    public int maxHealth = 100000;
    public double playerMagic = 100;
    public int maxMagic = 100;
    public int playerStamina = 100;
    private int playerWidth = 22;
    private int playerHeight = 52;
    public boolean isAttacking = false;

    private final Object lock = new Object();

    public Player(int x, int y, ID id, Game game) {
        super(x, y, id, game);

        this.handler = game.handler;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y - playerHeight, width, height);
    }

    public void tick() {
        x += velX;
        y += velY;

        if(godMode) {
            playerMagic+=1000;
            playerHealth+=1000;
            playerStamina+=1000;
        }

        //Magic regen
        if(playerMagic < maxMagic) {
            playerMagic += 0.1;
        } else {
            playerMagic = maxMagic;
        }

        //Have to have 2 booleans here or else either energy attack never gets set to true again, or energy attack always actives over other attacks
        if(canEnergyAttack) {
            if(playerMagic < EnergyAttack.energyAttackCost) {
                energyBlocked = true;
            } else {
                energyBlocked = false;
            }
        }

        x = Game.clamp(x, 0, Game.WIDTH - 32);

        if (playerHealth <= 0) {
            System.out.println("You died!");
            handler.removeObject(this);
        }

        if (KeyInput.rightAttack) {
            isAttacking = true;
            rightAttack();
        }

        if (KeyInput.leftAttack) {
            isAttacking = true;
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
                    switch(powerup.getID()) {
                        case DoubleJumpPowerup:
                            this.canDoubleJump = true;
                            handler.powerups.remove(powerup);
                            break;
                        case HealthPowerup:
                            playerHealth+=25;
                            handler.powerups.remove(powerup);
                            break;
                        case MagicPowerup:
                            playerMagic+=50;
                            if(playerMagic > 100) {
                                playerMagic = 100;
                            }
                            handler.powerups.remove(powerup);
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
                        else if (getX() < world.getX() + 10) {
                            setVelX(0);
                        }
                        //If player hits right side of object
                        else if(getX() > world.getX() + world.getWidth() - 10) {
                            setVelX(0);
                        }
                        //If player hits underside of object
                        else if (getY() > world.getY() + world.getHeight()) {
                            setVelY(getVelY() * -1);
                        }
                    }
                }
            }

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

        if(isAttacking) {
            try {
            playerImage = ImageIO.read(new File("assets/WizardAttackingStillRight.png"));
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
            System.exit(0);
        }

        } else try {
            playerImage = ImageIO.read(new File("assets/WizardImage.png"));
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
            System.exit(0);
        }

        setWidth(playerImage.getWidth());
        setHeight(playerImage.getHeight());
        if (x > 630) x = 631;
        if (x < 380) x = 379;
        //Flip image if we are walking backwards
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

        if (canEnergyAttack && !energyBlocked) {
            handler.addAttack(new EnergyAttack(x - EnergyAttack.width, y - 38, game, false));
            playerMagic -= EnergyAttack.energyAttackCost;
        } else if (canSwordAttack) {
            handler.addAttack(new SwordAttack(x - SwordAttack.range, y - 38, ID.SwordAttack, game));
        } else if (canLightningAttack) {
            handler.addAttack(new LightningAttack(x - LightningAttack.width, y - 32, game, false));
            playerMagic -= LightningAttack.lightningAttackCost;
        } else {
            System.out.println("You cannot attack at this time");
        }
        KeyInput.leftAttack = false;
    }

    public void rightAttack() {

        if (canEnergyAttack && !energyBlocked) {
            handler.addAttack(new EnergyAttack(x, y - 38, game, true));
            playerMagic -= EnergyAttack.energyAttackCost;
        } else if (canSwordAttack) {
            handler.addAttack(new SwordAttack(SwordAttack.range, y - 38, ID.SwordAttack, game));
        } else if (canLightningAttack) {
            handler.addAttack(new LightningAttack(x + 25, y - 38, game, true));
        } else {
            System.out.println("You cannot attack at this time");
        }
        KeyInput.rightAttack = false;
    }
}