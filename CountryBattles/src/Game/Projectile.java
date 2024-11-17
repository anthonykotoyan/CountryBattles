package Game;

import javax.swing.*;
import java.awt.*;

public class Projectile {

    private static final double GRAVITY = .2;
    private static final double MAX_VELOCITY = 1;
    private static final int time = 50;
    public Vector2D pos;
    private Vector2D vel;

    private Vector2D target;
    private double dx;
    private double dy;

    private int size;
    public boolean active = true;

    private boolean drawExplosion = false;

    public int blastRange = 30;

    public Projectile(Vector2D pos, Vector2D target, int size) {
        this.pos = new Vector2D(pos);
        this.target = new Vector2D(target);
        this.size = size;
        dx = target.x - pos.x;
        dy = target.y - pos.y;
        calculateInitialVel();
    }

    public void update(Graphics g) {
        if (active) {
            pos.x += vel.x;
            pos.y += vel.y;
            vel.y += GRAVITY;
            checkCollision();
            draw(g);
        }
        if(drawExplosion){
            drawExplosion(g);
        }
    }

    public void calculateInitialVel() {
        double distance = Math.sqrt(dx * dx + dy * dy);

        double velX = dx/time;
        double velY = dy/time - time*GRAVITY/2;

        vel = new Vector2D(velX, velY);
    }

    public void draw(Graphics g) {
        int ovalX = (int) pos.x - size / 2;
        int ovalY = (int) pos.y - size / 2;
        int ovalWidth = size;
        int ovalHeight = size;
        g.setColor(Color.BLACK);
        g.fillOval(ovalX, ovalY, ovalWidth, ovalHeight);
    }

    public void drawExplosion(Graphics g){
        int ovalX = (int) pos.x - size / 2;
        int ovalY = (int) pos.y - size / 2;
        int ovalWidth = blastRange;
        int ovalHeight = blastRange;
        g.setColor(Color.ORANGE);
        g.fillOval(ovalX, ovalY, ovalWidth, ovalHeight);
    }


    // Check if the projectile has hit the target
    public void checkCollision() {
        double distance = Math.sqrt(Math.pow(target.x - pos.x, 2) + Math.pow(target.y - pos.y, 2));
        if (distance <= blastRange) {
            Main.playSound("CountryBattles/src/Data/exp1.wav", 0.05f);
            active = false;
            drawExplosion = true;

        }
    }

    public Vector2D getVel(){
        return vel;
    }

}
