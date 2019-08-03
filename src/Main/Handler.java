package Main;

import Attacks.Attack;
import Enemies.Enemy;
import Powerups.Powerup;
import World.WorldObject;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    //Linked Lists for storing every object in the game
    public LinkedList<GameObject> object = new LinkedList<>();
    public LinkedList<Attack> attacks = new LinkedList<>();
    public LinkedList<Enemy> enemies = new LinkedList<>();
    public LinkedList<Powerup> powerups = new LinkedList<>();
    public LinkedList<WorldObject> world = new LinkedList<>();

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
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
        for (int i = 0; i < world.size(); i++) {
            WorldObject worlds = world.get(i);
            worlds.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
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
        for (int i = 0; i < world.size(); i++) {
            WorldObject worlds = world.get(i);
            worlds.render(g);
        }
    }

    public void addAttack(Attack attack) {
        this.attacks.add(attack);
    }

    public void removeAttack(Attack attack) {
        this.attacks.remove(attack);
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

    public void addEnemy(Enemy enemy) {this.enemies.add(enemy);}

    public void removeEnemy(Enemy enemy) {this.enemies.remove(enemy);}

    public void addPowerup(Powerup powerup) {this.powerups.add(powerup);}

    public void removePowerup(Powerup powerup) {this.powerups.remove(powerup);}

    public void addWorldObject(WorldObject worlds) {this.world.add(worlds);}

    public void removeWorldObject(WorldObject worlds) {this.world.remove(worlds);}
}
