package Main;

import Attacks.Attack;
import Enemies.Enemy;
import Powerups.Powerup;
import World.NPCs.NPC;
import World.WorldObject;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class KeyInput extends KeyAdapter {
    private Game game;
    public static boolean aDown = false, dDown = false, rightAttack = false, leftAttack = false, rightDown = false, leftDown = false;
    private STATE previousState = STATE.Menu;
    private Spawner spawn;

    public KeyInput(Game game) {
        this.game = game;
        spawn = new Spawner(game);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        //Key events for the player
        if (key == KeyEvent.VK_SHIFT && !game.player.isFalling && (game.player.getVelX() < 5 && game.player.getVelX() > -5)) {
            game.player.setVelX(game.player.getVelX() * 2);
        }

        if (key == KeyEvent.VK_A) {
            game.player.setVelX(-3);
            aDown = true;
        }

        if (key == KeyEvent.VK_D) {
            game.player.setVelX(+3);
            dDown = true;
        }

        if (key == KeyEvent.VK_LEFT && !leftDown) {
            leftAttack = true;
            leftDown = true;
        }

        if (key == KeyEvent.VK_RIGHT && !rightDown) {
            rightAttack = true;
            rightDown = true;
        }

        if (key == KeyEvent.VK_SPACE && game.gameState != STATE.Cutscene) {
            game.player.jump();
        }

        //mainly exit menu and exit game type stuff
        if (key == KeyEvent.VK_ESCAPE) {
            //sets the previous game state before doing something
            if(game.gameState == STATE.Menu) {
                System.exit(0);
            } else if(game.gameState == STATE.Game) {
                game.addMouseListener(game.pause);
                game.addMouseMotionListener(game.pause);
                game.gameState = STATE.Pause;
            } else if (game.gameState == STATE.Pause) {
                game.removeMouseListener(game.pause);
                game.removeMouseMotionListener(game.pause);
                game.gameState = STATE.Game;
            } else if (game.gameState == STATE.ItemSelect) {
                game.removeMouseListener(game.itemMenu);
                game.removeMouseMotionListener(game.itemMenu);
                game.gameState = STATE.Game;
            } else if (game.gameState == STATE.IngameOptions) {
                if(game.player.playerHealth <= 0) {
                    game.removeMouseListener(game.inGameOptions);
                    game.removeMouseMotionListener(game.inGameOptions);
                    game.gameState = STATE.Dead;
                    game.addMouseMotionListener(game.dead);
                    game.addMouseListener(game.dead);
                } else {
                    game.removeMouseListener(game.inGameOptions);
                    game.removeMouseMotionListener(game.inGameOptions);
                    game.gameState = STATE.Pause;
                    game.addMouseListener(game.pause);
                    game.addMouseMotionListener(game.pause);
                }
            } else if (game.gameState == STATE.MenuOptions) {
                game.removeMouseListener(game.menuOptions);
                game.removeMouseMotionListener(game.menuOptions);
                game.gameState = STATE.Menu;
                game.addMouseListener(game.menu);
                game.addMouseMotionListener(game.menu);
            }
        }

        if(key == KeyEvent.VK_E) {
            if(game.player.touchingDomino) {
                previousState = game.gameState;
                game.gameState = STATE.Cutscene;
                game.cutscene = new Cutscene("assets/FirstCutscene.mp4", game);
                game.window.frame.add(game.cutscene.jfx);
                game.stop();
            } else if (game.player.touchingDoor) {
                //loopBack();
                game.handler.clearAll();
                spawn.spawn(new File("Levels/Level_1"));
            }
        }

        if(key == KeyEvent.VK_O) {
            if(game.gameState == STATE.Game) {
                game.gameState = STATE.IngameOptions;
                game.addMouseListener(game.inGameOptions);
                game.addMouseMotionListener(game.inGameOptions);
            } else if (game.gameState == STATE.Menu) {
                game.removeMouseListener(game.menu);
                game.removeMouseMotionListener(game.menu);
                game.gameState = STATE.MenuOptions;
                game.addMouseListener(game.menuOptions);
                game.addMouseMotionListener(game.menuOptions);
            } else if (game.gameState == STATE.MenuOptions) {
                game.removeMouseListener(game.inGameOptions);
                game.removeMouseMotionListener(game.inGameOptions);
                game.gameState = STATE.Menu;
                game.addMouseListener(game.menu);
                game.addMouseMotionListener(game.menu);
            } else if (game.gameState == STATE.IngameOptions) {
                if(game.player.playerHealth <= 0) {
                    game.removeMouseListener(game.inGameOptions);
                    game.removeMouseMotionListener(game.inGameOptions);
                    game.gameState = STATE.Dead;
                    game.addMouseMotionListener(game.dead);
                    game.addMouseListener(game.dead);
                } else {
                    game.removeMouseListener(game.inGameOptions);
                    game.removeMouseMotionListener(game.inGameOptions);
                    game.removeMouseListener(game.inGameOptions);
                    game.removeMouseMotionListener(game.inGameOptions);
                    game.gameState = STATE.Game;
                }
            } else if (game.gameState == STATE.Pause) {
                game.removeMouseListener(game.pause);
                game.removeMouseMotionListener(game.pause);
                game.gameState = STATE.IngameOptions;
                game.addMouseListener(game.inGameOptions);
                game.addMouseMotionListener(game.inGameOptions);
            } else if (game.gameState == STATE.Dead) {
                game.removeMouseListener(game.dead);
                game.removeMouseMotionListener(game.dead);
                game.gameState = STATE.IngameOptions;
                game.addMouseListener(game.inGameOptions);
                game.addMouseMotionListener(game.inGameOptions);
            }
        }

        //Opens item menu when in game, closes it when in menu
        if (key == KeyEvent.VK_I) {
            if(game.gameState == STATE.Game) {
                game.addMouseListener(game.itemMenu);
                game.addMouseMotionListener(game.itemMenu);
                game.gameState = STATE.ItemSelect;
            } else if (game.gameState == STATE.ItemSelect) {
                game.gameState = STATE.Game;
                game.removeMouseListener(game.itemMenu);
                game.removeMouseMotionListener(game.itemMenu);
            }
        }

        if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
            if(game.gameState==STATE.Cutscene) {
                game.gameState = previousState;
                game.window.frame.remove(game.cutscene.jfx);
                game.window.frame.add(game);
                game.createBufferStrategy(3);
                game.start();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SHIFT && !game.player.isFalling && (game.player.getVelX() > 5 || game.player.getVelX() < -5)) {
            game.player.setVelX(game.player.getVelX() / 2);
        }

        if (key == KeyEvent.VK_A) {
            if (dDown) game.player.setVelX(+3);
            else game.player.setVelX(0);
            aDown = false;
        }
        if (key == KeyEvent.VK_D) {
            if (aDown) game.player.setVelX(-3);
            else game.player.setVelX(0);
            dDown = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            game.player.isAttacking = false;
            leftDown = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            game.player.isAttacking = false;
            rightDown = false;
        }
        if (key == KeyEvent.VK_SPACE) {

        }
    }
    public void loopBack() {
        for (int i = 0; i < game.handler.objects.size(); i++) {
            GameObject tempObject = game.handler.objects.get(i);
            if (tempObject.getID() != ID.Player) {
                tempObject.setX(tempObject.getX() + game.player.absoluteX);
            }
        }
        for (int i = 0; i < game.handler.attacks.size(); i++) {
            Attack attackObject = game.handler.attacks.get(i);
            attackObject.setX(attackObject.getX() + game.player.absoluteX);
        }
        for (int i = 0; i < game.handler.enemies.size(); i++) {
            Enemy enemyObject = game.handler.enemies.get(i);
            enemyObject.setX(enemyObject.getX() + game.player.absoluteX);
        }
        for (int i = 0; i < game.handler.powerups.size(); i++) {
            Powerup powerupObject = game.handler.powerups.get(i);
            powerupObject.setX(powerupObject.getX() + game.player.absoluteX);
        }
        for (int i = 0; i < game.handler.worldObjects.size(); i++) {
            WorldObject worldObject = game.handler.worldObjects.get(i);
            worldObject.setX(worldObject.getX() + game.player.absoluteX);
        }
        for (int i = 0; i < game.handler.npcs.size(); i++) {
            NPC npc = game.handler.npcs.get(i);
            npc.setX(npc.getX() + game.player.absoluteX);
        }
    }
}
