package Main;

import UI.HUD;
import UI.Menu;

import java.awt.*;
import java.awt.image.BufferStrategy;

//TODO
// fix it so that you can't go left further than where you spawned
// Sword image held by character
// Fix movement - sprinting
// Fix holding space breaks doubleJump
// Level parsing
// Sword swing
// Options menu
// Load button
// Mana bar
// Fix NullPointerEx for spamming energy attacks
// Fix colliding with sides of platforms

public class Game extends Canvas implements Runnable {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private Thread thread;
    private HUD hud;
    private UI.Menu menu;
    private int fps;
    private boolean running = false;
    public Player player;
    public Handler handler;
    public Spawner spawner;

    public STATE gameState = STATE.Menu;

    public Game() {
        handler = new Handler();
        menu = new Menu(this);
        hud = new HUD(this);
        spawner = new Spawner(this);

        this.addKeyListener(new KeyInput(this));
        this.addMouseListener(menu);
        this.addMouseMotionListener(menu);

        new Window(WIDTH, HEIGHT, "Budget Scrolls", this);

        player = new Player(410, 250, ID.Player, handler, this);
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
        handler.tick();

        if(gameState == STATE.Game) {
            hud.tick();
        } else if (gameState == STATE.Menu){
            menu.tick();
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
        } else if (gameState == STATE.Menu){
            menu.render(g);
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