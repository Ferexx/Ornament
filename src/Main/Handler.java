package Main;

import Attacks.Attack;
import Enemies.Enemy;
import Powerups.Powerup;
import Sound.SoundPlayer;
import World.NPCs.NPC;
import World.WorldObject;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    //Linked Lists for storing every object in the game
    public LinkedList<GameObject> objects = new LinkedList<>();
    public LinkedList<Attack> attacks = new LinkedList<>();
    public LinkedList<Enemy> enemies = new LinkedList<>();
    public LinkedList<Powerup> powerups = new LinkedList<>();
    public LinkedList<WorldObject> worldObjects = new LinkedList<>();
    public LinkedList<NPC> npcs = new LinkedList<>();
    public LinkedList<SoundPlayer> sounds = new LinkedList<>();

    Game game;

    public Handler(Game game) {
        this.game=game;
    }
    public void tick() {
        for (int i = 0; i < objects.size(); i++) {
            GameObject tempObject = objects.get(i);
            tempObject.tick();
        }
        for (int i = 0; i < attacks.size(); i++) {
            Attack tempAttack = attacks.get(i);
            tempAttack.tick();
        }
        for (int i = 0; i < enemies.size(); i++) {
            Enemy tempEnemy = enemies.get(i);
            tempEnemy.tick();
        }
        for (int i = 0; i < powerups.size(); i++) {
            Powerup powerup = powerups.get(i);
            powerup.tick();
        }
        for (int i = 0; i < worldObjects.size(); i++) {
            WorldObject world = worldObjects.get(i);
            world.tick();
        }
        for (int i = 0; i < npcs.size(); i++) {
            NPC npc = npcs.get(i);
            npc.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject tempObject = objects.get(i);
            tempObject.render(g);
        }
        for (int i = 0; i < attacks.size(); i++) {
            Attack tempAttack = attacks.get(i);
            tempAttack.render(g);
        }
        for (int i = 0; i < enemies.size(); i++) {
            Enemy tempEnemy = enemies.get(i);
            tempEnemy.render(g);
        }
        for (int i = 0; i < powerups.size(); i++) {
            Powerup powerup = powerups.get(i);
            powerup.render(g);
        }
        for (int i = 0; i < worldObjects.size(); i++) {
            WorldObject world = worldObjects.get(i);
            world.render(g);
        }
        for (int i = 0; i < npcs.size(); i++) {
            NPC npc = npcs.get(i);
            npc.render(g);
        }
        game.player.render(g);
    }

    public void addAttack(Attack attack) {
        this.attacks.add(attack);
    }

    public void removeAttack(Attack attack) {
        this.attacks.remove(attack);
    }

    public void addObject(GameObject object) {
        this.objects.add(object);
    }

    public void removeObject(GameObject object) {
        this.objects.remove(object);
    }

    public void addEnemy(Enemy enemy) {this.enemies.add(enemy);}

    public void removeEnemy(Enemy enemy) {this.enemies.remove(enemy);}

    public void addPowerup(Powerup powerup) {this.powerups.add(powerup);}

    public void removePowerup(Powerup powerup) {this.powerups.remove(powerup);}

    public void addWorldObject(WorldObject world) {this.worldObjects.add(world);}

    public void removeWorldObject(WorldObject world) {this.worldObjects.remove(world);}

    public void addNPC(NPC npc) {this.npcs.add(npc);}

    public void removeNPC(NPC npc) {this.npcs.remove(npc);}

    public void addSound(SoundPlayer sound) {this.sounds.add(sound);}

    public void removeSound(SoundPlayer sound) {this.sounds.remove(sound);}
    public void removeSound(String soundName) {
        for(int i = 0; i < sounds.size(); i++) {
            if(sounds.get(i).name.equals(soundName))
                sounds.remove(sounds.get(i));
        }
    }
    public void changeVolume(float newVolume) {
        for(int i = 0; i < sounds.size(); i++) {
            sounds.get(i).setVolume(newVolume);
        }
    }

    public void clearAll() {
        objects = new LinkedList<>();
        attacks = new LinkedList<>();
        enemies = new LinkedList<>();
        powerups = new LinkedList<>();
        worldObjects = new LinkedList<>();
        npcs = new LinkedList<>();
        sounds = new LinkedList<>();
    }
}
