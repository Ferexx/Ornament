package Main;

import Attacks.EnergyAttack;
import Attacks.LightningAttack;
import Attacks.SwordAttack;
import Data.PlayerSettings;
import Enemies.Enemy;
import Powerups.Powerup;
import World.NPCs.NPC;
import World.WorldObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static Main.CharacterType.*;
import static World.NPCs.EssentialNPCs.Domino;

public class Player extends GameObject {
    private Handler handler;
    private Timer timer;
    private ImageIcon playerImage;
    private CharacterType characterType;

    //Godmode - infinite health, stamina, mana
    private boolean godMode = false;

    //Attacks
    public boolean canEnergyAttack = false;
        private boolean energyBlocked = false;
    public boolean canSwordAttack = true;
    public boolean canLightningAttack = false;

    //Movement
    private boolean canDoubleJump = false;
    boolean isFalling = false;
    private boolean isStanding = true;
    private boolean doubleJump = false;
    private boolean facingRight=true;

    //Players position in the level. x and y are used for position on screen
    public int absoluteX;
    public int absoluteY;

    //Attributes
    public boolean hasStamina = false;
    public boolean hasMagic = true;
    public int playerHealth = 100000;
    public int maxHealth = 100000;
    public double playerMagic = 100;
    public int maxMagic = 100;
    public int playerStamina = 100;
    public boolean isAttacking = false;
    public PlayerSettings settings;

    //Dimensions
    private int playerWidth;
    private int playerHeight;

    //Checks
    boolean touchingDomino = false;
    boolean touchingDoor = false;

    private final Object lock = new Object();

    public Player(int x, int y, ID id, Game game, CharacterType characterType) {
        super(x, y, id, game);
        absoluteX = x;
        absoluteY = y;

        this.handler = game.handler;
        this.characterType = characterType;
        File file = new File("./playerConfig.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        settings = new PlayerSettings(file);
        settings.saveSettings();
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y - playerHeight, width, height);
    }

    public void tick() {
        //Updating relative position in window and also position in level
        y += velY;
        absoluteY+=velY;
        //Calling collision before changing x values to prevent glitching through objects.
        collision();
        x += velX;
        absoluteX+=velX;

        if(velX < 0) facingRight=false;
        if(velX > 0) facingRight=true;

        //Godmode traits
        if(godMode) {
            playerMagic+=100;
            playerHealth+=100;
            playerStamina+=100;
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

        //Death case
        if (playerHealth <= 0) {
            System.out.println("You died!");
            game.gameState = STATE.Dead;
            game.addMouseListener(game.dead);
            game.addMouseMotionListener(game.dead);
            handler.removeObject(this);
        }

        //If right attacking
        if (KeyInput.rightAttack) {
            isAttacking = true;
            facingRight = true;
            rightAttack();
        }

        //if left attacking
        if (KeyInput.leftAttack) {
            isAttacking = true;
            facingRight = false;
            leftAttack();
        }
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

            //if player collides with NPC Villager
            for(int i = 0; i < handler.npcs.size(); i++) {
                NPC npc = handler.npcs.get(i);
                if(getBounds().intersects(npc.getBounds())) {
                    touchingDomino = true;
                } else {
                    touchingDomino = false;
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
            for(int i = 0; i < handler.worldObjects.size(); i++) {
                WorldObject world = handler.worldObjects.get(i);
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
                }else if (getBounds().intersects(world.getBounds())) {
                    switch (world.getID()) {
                        case Door:
                            game.player.touchingDoor = true;
                            break;
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
                        } else if(game.gameState==STATE.Game){
                            setVelY(getVelY() + 0.5);
                        }
                    }
                }, 0, 50);
            }
        }
    }

    public void render(Graphics g) {

        //If mage
        if (characterType == Mage) {
            //set dimensions
            playerWidth = 22;
            playerHeight = 52;
            //Animation stuff for attacking
            if (isAttacking) {
                if (facingRight) {
                    playerImage = new ImageIcon("assets/Player/Mage/WizardAttackingStillRight.png");
                } else {
                    playerImage = new ImageIcon("assets/Player/Mage/WizardAttackingStillLeft.png");
                }
            }
            //Basic Mage image
            else {
                if (facingRight) {
                    playerImage = new ImageIcon("assets/Player/Mage/WizardFacingRight.png");
                }
                else {
                    playerImage = new ImageIcon("assets/Player/Mage/WizardFacingLeft.png");
                }
            }
        } else if (characterType == Tank) {
            playerWidth = 50;
            playerHeight = 59;
            if (facingRight) {
                playerImage = new ImageIcon("assets/Player/Tank/ArmouredTank.png");
            }
            else {
                playerImage = new ImageIcon("assets/Player/Tank/ArmouredTankLeft.png");
            }
        } else if (characterType == Nobleman) {

            //Physical dimensions
            playerWidth = 42;
            playerHeight = 57;
            if (isAttacking) {
                if (facingRight) {
                    playerImage = new ImageIcon("assets/Player/Nobleman/NoblemanMark1RightAttack.gif");
                } else {
                    playerImage = new ImageIcon("assets/Player/Nobleman/NoblemanMark1LeftAttack.gif");
                }
            }
            else if (facingRight) {
                playerImage = new ImageIcon("assets/Player/Nobleman/NoblemanMark2.png");
            }
            else {
                playerImage = new ImageIcon("assets/Player/Nobleman/NoblemanMark2Left.png");
            }
        } else if (characterType == Archer) {

        }

        setWidth(playerImage.getIconWidth());
        setHeight(playerImage.getIconHeight());
        if (x > 630) x = 631;
        if (x < 380 && absoluteX > 380) x = 379;
        if (absoluteX < 0) absoluteX = 0;


        g.drawImage(playerImage.getImage(), x, y-height, null);
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
                        } else if(game.gameState==STATE.Game){
                            setVelY(getVelY() + 0.5);
                        }
                    }
                }, 0, 50);
            } else if (!isFalling) {
                isFalling = true;
                setVelY(-5);
                doubleJump=false;
                timer.cancel();
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (!isFalling) {
                            isStanding = true;
                            setVelY(0);
                            cancel();
                        } else if(game.gameState==STATE.Game){
                            setVelY(getVelY() + 0.5);
                        }
                    }
                }, 0, 50);
            }
        }
    }

    //right attack function
    public void rightAttack() {

        if (canEnergyAttack && !energyBlocked) {
            handler.addAttack(new EnergyAttack(x, y - 38, game, true));
            playerMagic -= EnergyAttack.energyAttackCost;
        } else if (canSwordAttack) {
            handler.addAttack(new SwordAttack(SwordAttack.width, y - 38, game, true));
        } else if (canLightningAttack) {
            handler.addAttack(new LightningAttack(x + 25, y - 38, game, true));
        } else {
            System.out.println("You cannot attack at this time");
        }
        KeyInput.rightAttack = false;
        facingRight = true;
    }

    //left attack function
    public void leftAttack() {

        if (canEnergyAttack && !energyBlocked) {
            handler.addAttack(new EnergyAttack(x - EnergyAttack.width, y - 38, game, false));
            playerMagic -= EnergyAttack.energyAttackCost;
        } else if (canSwordAttack) {
            handler.addAttack(new SwordAttack(x - SwordAttack.width, y - 38, game, false));
        } else if (canLightningAttack) {
            handler.addAttack(new LightningAttack(x - LightningAttack.width, y - 32, game, false));
            playerMagic -= LightningAttack.lightningAttackCost;
        } else {
            System.out.println("You cannot attack at this time");
        }
        KeyInput.leftAttack = false;
        facingRight=false;
    }
}