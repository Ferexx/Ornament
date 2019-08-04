package Main;

import UI.*;
import UI.Menu;

import java.awt.*;
import java.awt.image.BufferStrategy;

//TODO
// fix it so that you can't go left further than where you spawned
// Sword image held by character
// Fix movement - sprinting
// Fix holding space breaks doubleJump
// Sword swing
// Options menu
// Load button
// Fix NullPointerEx for spamming energy attacks
// Fix colliding with sides of platforms
// Make damage report above where player/mobs take damage
// fix alert disappearing after 2 seconds

public class Game extends Canvas implements Runnable {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private Thread thread;
    private HUD hud;
    public UI.Menu menu;
    public UI.ItemMenu itemMenu;
    public UI.Pause pause;
    private int fps;
    private boolean running = false;
    public Player player;
    public Handler handler;
    public Spawner spawner;
    public Alert alert;
    private String newGame;

    public STATE gameState = STATE.Menu;

    public Game() {
        newGame = "Welcome to Rahau";
        handler = new Handler();
        menu = new Menu(this);
        itemMenu = new ItemMenu(this);
        pause = new Pause(this);
        hud = new HUD(this);
        player = new Player(410, 250, ID.Player, this);
        spawner = new Spawner(this);
        alert = new Alert(this, newGame, Color.WHITE, WIDTH/2 - 90, HEIGHT / 8, 2);

        this.addKeyListener(new KeyInput(this));

        new Window(WIDTH, HEIGHT, "Budget Scrolls", this);

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
        }  else if(gameState == STATE.ItemSelect) {
            itemMenu.tick();
        } else {

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

        Graphics g = bs.getDrawGraphics();

        handler.render(g);

        if(gameState == STATE.Game) {
            hud.render(g);
            g.setFont(new Font("Verdana", 1, 16));
            g.setColor(Color.GREEN);    //FPS counter colour
            g.drawString( fps+" FPS", WIDTH-128,40);

            //alert.render(g);
        } else if (gameState == STATE.Menu){
            menu.render(g);
        } else if (gameState == STATE.Pause) {
            pause.render(g);
        } else if(gameState == STATE.ItemSelect) {
            itemMenu.render(g);
        }

        g.dispose();
        bs.show();
    }

    public static int clamp(int var, int min, int max) {
        if (var >= max) {
            return max;
        } else return Math.max(var, min);
    }

    public static void main(String[] args) {
        new Game();
    }
}