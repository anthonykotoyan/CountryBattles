package Game;

import java.awt.*;
import java.util.ArrayList;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public abstract class Troop {
    public Color color;
    public Vector2D pos;
    public String type;

    private boolean isAlive = true;
    public double angle;
    private Troop target;

    private double  health = 1;
    private double  damage = 1;

    public Troop[] enemyTroops;

    public Troop(Vector2D pos, String type, Color color, double angle) {
        this.pos = pos;
        this.type = type;
        this.color = color;
        this.angle = angle;
    }
    public abstract void update(Graphics g);


    public abstract void draw(Graphics g);


    public void initEnemyTroops(Troop[] enemyTroops){
        this.enemyTroops = enemyTroops;
    }


    public void lookTo(double rotationSpeed) {
        double deltaX = target.pos.x - this.pos.x;
        double deltaY = target.pos.y - this.pos.y;
        double targetAngle = Math.atan2(deltaY, deltaX);
        double angleDifference = targetAngle - angle;
        angleDifference = Math.atan2(Math.sin(angleDifference), Math.cos(angleDifference));
        angle += angleDifference * rotationSpeed;
    }


    public abstract void attack();


    public abstract double getVel();

    public void takeDamage(double damage){
        health -= damage;
    }


    public void applyDamage(Troop other){
        other.takeDamage(damage);
    }

    public boolean isAlive(){
        return (health > 0);
    }



    public void setTarget(Troop target) {
        this.target = target;
    }
    public Troop getTarget() {
        return this.target;
    }

    public Vector2D getPos() {
        return pos;
    }

    public void setPos(Vector2D pos) {
        this.pos = pos;
    }

    public void setHealth(double health){
        this.health = health;
    }

    public void setDamage(double damage){
        this.damage = damage;
    }
}
