package Main;

import UI.*;
import UI.Menu;

import java.awt.*;
import java.awt.image.BufferStrategy;

//TODO - add anything at any time. This is not a suggestions space, this is for genuine priority features
// ------------------------ Graphics needed: ------------------------------
// Bow
// Melee
// Archer
// Soldier
// Nobleman
// Cutscene imagery
// ------------------------ Gameplay: -------------------------------------
// Melee gameplay
// Bow gameplay
// cutscenes and dialogue
// Upgradable items / new ability ideas
// ------------------------ Misc: -----------------------------------------
// Death screen and shit (respawn at level start)
// Options screen and image resolution scaling
// Timed messages on screen
// ------------------------ Known Bugs: ------------------------------------
// NullPointer when energy attacking - not reproducible yet
// Teleporting on top of platforms when hitting the sides sometimes
// Hitting edge of platform fully stops movement until key is repressed
// Wild bounce off screen when switching back to gameState Game
// ---------------------------- Tweaks: ------------------------------------
// Letting go of shift produces unnatural/uncomfortable behaviour

public class Game extends Canvas implements Runnable {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private Thread thread;
    private HUD hud;
    public UI.Menu menu;
    public UI.Dead dead;
    public UI.ItemMenu itemMenu;
    public UI.Pause pause;
    private int fps;
    private boolean running = false;
    public Player player;
    public Handler handler;
    public Spawner spawner;
    public Alert alert;
    private String newGame;
    public Window window;
    public Cutscene cutscene;

    public STATE gameState = STATE.Menu;

    public Game() {
        newGame = "Welcome to Rahau";
        handler = new Handler();
        menu = new Menu(this);
        itemMenu = new ItemMenu(this);
        dead = new Dead(this);
        pause = new Pause(this);
        hud = new HUD(this);
        player = new Player(410, 250, ID.Player, this);
        spawner = new Spawner(this);
        alert = new Alert(this, newGame, Color.WHITE, WIDTH/2 - 90, HEIGHT / 8, 2);

        this.addKeyListener(new KeyInput(this));

        window = new Window(WIDTH, HEIGHT, "Budget Scrolls", this);

    }

    public synchronized void start() {
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    public void stop() {
        try {
            running = false;
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 50.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running) {
            requestFocus();
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                fps = frames;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

    public void tick() {
        //Paused game state stops handler from ticking
        if (gameState == STATE.Pause) {
            pause.tick();
        //Else makes the game tick, or the menu tick (menu tick only happens at game launch)
        } else if(gameState == STATE.ItemSelect) {
            itemMenu.tick();
        } else if(gameState == STATE.Dead) {
            dead.tick();
        } else if (gameState == STATE.Game){
            handler.tick();

            if(gameState == STATE.Game) {
                hud.tick();
            } else if (gameState == STATE.Menu){
                menu.tick();
            }
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        if(gameState!=STATE.Cutscene) {
            Graphics g = bs.getDrawGraphics();

            handler.render(g);

            if (gameState == STATE.Game) {
                hud.render(g);
                g.setFont(new Font("Verdana", 1, 16));
                g.setColor(Color.GREEN);    //FPS counter colour
                g.drawString(fps + " FPS", WIDTH - 128, 40);
            } else if (gameState == STATE.Menu) {
                menu.render(g);
            } else if (gameState == STATE.Pause) {
                pause.render(g);
            } else if (gameState == STATE.ItemSelect) {
                itemMenu.render(g);
            } else if (gameState == STATE.Dead) {
                dead.render(g);
            }

            g.dispose();
            bs.show();
        }
    }

    public static int clamp(int var, int min, int max) {
        if (var >= max) {
            return max;
        } else return Math.max(var, min);
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}