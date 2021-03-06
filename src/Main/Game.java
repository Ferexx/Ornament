package Main;

import Data.PlayerSettings;
import Sound.SoundPlayer;
import UI.*;
import UI.Menu;
import javafx.scene.control.Alert;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

public class Game extends Canvas implements Runnable {
    //Window
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public Window window;

    //UI
    public UI.Menu menu;
    public UI.Dead dead;
    public UI.ItemMenu itemMenu;
    public UI.Pause pause;
    public IngameOptions inGameOptions;
    public MenuOptions menuOptions;
    private HUD hud;

    //Performance
    private Thread thread;
    private int fps;
    private boolean running = false;

    //Game engine
    public Player player;
    public Handler handler;
    public Spawner spawner;
    public PlayerSettings settings;

    //Video
    public Cutscene cutscene;

    //Input output
    public KeyInput keyInput = new KeyInput(this);

    //Initialised the gameState to the main menu
    public STATE gameState = STATE.Menu;
    public int characterType;

    public Game() {
        //Initialising Game requirements
        menu = new Menu(this);
        handler = new Handler(this);
        spawner = new Spawner(this);

        //Creating player settings file if one doesn't already exist
        File file = new File("./playerConfig.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        settings = new PlayerSettings(file);
        settings.saveSettings();

        handler.addSound(new SoundPlayer(new File("assets/Music/TitleConcept.wav"), this, "titleMusic"));

        //UI initialisations
        itemMenu = new ItemMenu(this);
        inGameOptions = new IngameOptions(this);
        menuOptions = new MenuOptions(this);
        dead = new Dead(this);
        pause = new Pause(this);
        hud = new HUD(this);

        //Key input is added to all aspects of the game
        this.addKeyListener(keyInput);

        //Initialise window and music player
        window = new Window(WIDTH, HEIGHT, "Budget Scrolls", this);
    }

    //Starts the game thread
    public synchronized void start() {
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    //Stops the game thread
    public void stop() {
        try {
            running = false;
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Runs the game
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
                frames = 0;
            }
        }
    }

    //Tick happens X amount of times per second - defined in run()
    public void tick() {
        //Paused game state stops handler from ticking
        if (gameState == STATE.Pause) {
            pause.tick();
        //Else makes the game tick, or the menu tick (menu tick only happens at game launch)
        } else if(gameState == STATE.ItemSelect) {
            itemMenu.tick();
        } else if(gameState == STATE.Dead) {
            dead.tick();
        } else if (gameState == STATE.Game) {
            handler.tick();
            hud.tick();
        } else if (gameState == STATE.Menu) {
            menu.tick();
        } else if(gameState == STATE.IngameOptions) {
            inGameOptions.tick();
        } else if (gameState == STATE.MenuOptions) {
            menuOptions.tick();
        }
    }

    //Render loads everything onto the screen
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        if(gameState!=STATE.Cutscene) {
            Graphics g = bs.getDrawGraphics();
            handler.render(g);
            if (gameState == STATE.Game) {
                hud.render(g);

                //FPS counter
                g.setFont(new Font("Verdana", 1, 16));
                g.setColor(Color.GREEN);
                g.drawString(fps + " FPS", WIDTH - 128, 40);

            } else if (gameState == STATE.Menu) {
                menu.render(g);
            } else if (gameState == STATE.Pause) {
                pause.render(g);
            } else if (gameState == STATE.ItemSelect) {
                itemMenu.render(g);
            } else if (gameState == STATE.Dead) {
                dead.render(g);
            } else if (gameState == STATE.IngameOptions) {
                inGameOptions.render(g);
            } else if (gameState == STATE.MenuOptions) {
                menuOptions.render(g);
            }

            g.dispose();
            bs.show();
        }
    }

    //Clamp function is used for limiting things, stopping movement or progression
    public static int clamp(int var, int min, int max) {
        if (var >= max) {
            return max;
        } else return Math.max(var, min);
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}