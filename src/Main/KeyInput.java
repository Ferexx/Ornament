package Main;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private Game game;
    public static boolean aDown = false, dDown = false, rightAttack = false, leftAttack = false, rightDown = false, leftDown = false;
    private STATE previousState;

    public KeyInput(Game game) {
        this.game = game;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < game.handler.object.size(); i++) {
            GameObject tempObject = game.handler.object.get(i);

            if (tempObject.getID() == ID.Player) {
                //Key events for the player

                if (key == KeyEvent.VK_A) {
                    tempObject.setVelX(-3);
                    aDown = true;
                }

                if (key == KeyEvent.VK_D) {
                    tempObject.setVelX(+3);
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

                if (key == KeyEvent.VK_SHIFT) {
                    if (!game.player.isFalling && tempObject.getVelX() < 5 && tempObject.getVelX() > -5)
                        tempObject.setVelX(tempObject.getVelX() * 2);
                }
            }
        }

        //mainly exit menu and exit game type stuff
        if (key == KeyEvent.VK_ESCAPE) {
            if(game.gameState == STATE.Menu) {
                System.exit(0);
            }else if(game.gameState == STATE.Game) {
                game.gameState = STATE.Pause;
                game.addMouseListener(game.pause);
                game.addMouseMotionListener(game.pause);
            } else if (game.gameState == STATE.Pause) {
                game.gameState = STATE.Game;
                game.removeMouseListener(game.pause);
                game.removeMouseMotionListener(game.pause);
            } else if (game.gameState == STATE.ItemSelect) {
                game.gameState = STATE.Game;
                game.removeMouseListener(game.itemMenu);
                game.removeMouseMotionListener(game.itemMenu);
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

        if(key == KeyEvent.VK_C) {
            if(game.gameState!=STATE.Cutscene) {
                previousState = game.gameState;
                game.gameState = STATE.Cutscene;
                game.cutscene = new Cutscene("assets/Deag2k2.mp4", game);
                game.window.frame.add(game.cutscene.jfx);
                game.stop();
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

        for (int i = 0; i < game.handler.object.size(); i++) {
            GameObject tempObject = game.handler.object.get(i);

            if (tempObject.getID() == ID.Player) {
                //Key events for the player

                if (key == KeyEvent.VK_A) {
                    if (dDown) tempObject.setVelX(+3);
                    else tempObject.setVelX(0);
                    aDown = false;
                }

                if (key == KeyEvent.VK_D) {
                    if (aDown) tempObject.setVelX(-3);
                    else tempObject.setVelX(0);
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

                if (key == KeyEvent.VK_SHIFT) {
                    if (!game.player.isFalling && (game.player.getVelX() > 5 || game.player.getVelX() < -5)) tempObject.setVelX(tempObject.getVelX() / 2);
                }
            }
        }

    }
}
