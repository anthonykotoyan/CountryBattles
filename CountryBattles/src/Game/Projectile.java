package Game;

import javax.swing.*;
import java.awt.*;

public class Projectile {

    private double gravity;
    private int time;
    public Vector2D pos;
    private Vector2D vel;

    public boolean ifBall = true;

    private Vector2D target;
    private double dx;
    private double dy;

    private int size;
    public boolean active = true;

    public boolean drawImpact = true;
    private boolean drawExplosion = false;
    private boolean hitSound;

    public int blastRange;
    private Color color;
    public Projectile(Vector2D pos, Vector2D target, int size, double gravity,int time, int blastRange, Color color, boolean hitSound) {
        this.pos = new Vector2D(pos);
        this.target = new Vector2D(target);
        this.size = size;
        this.gravity = gravity;
        dx = target.x - pos.x;
        dy = target.y - pos.y;
        this.color = color;
        this.time = time;
        this.blastRange = blastRange;
        this.hitSound =hitSound;


        calculateInitialVel();
    }

    public void update(Graphics g) {
        if (active) {
            pos.x += vel.x;
            pos.y += vel.y;
            vel.y += gravity;
            checkCollision();
            draw(g);
        }
        if(drawExplosion){
            if(drawImpact){drawExplosion(g);}
        }
    }

    public void calculateInitialVel() {


        double velX = dx/time;
        double velY = dy/time - time*gravity/2;

        vel = new Vector2D(velX, velY);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        if (ifBall) {
            int ovalX = (int) pos.x - size / 2;
            int ovalY = (int) pos.y - size / 2;
            int ovalWidth = size;
            int ovalHeight = size;

            g.fillOval(ovalX, ovalY, ovalWidth, ovalHeight);
        }else{
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(
                    (int) pos.x,
                    (int) pos.y,
                    (int) (pos.x + vel.x),
                    (int) (pos.y + vel.y)
            );

        }
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
            if (hitSound){
            Main.playSound("CountryBattles/src/Data/exp1.wav", 0.05f);}
            active = false;
            drawExplosion = true;

        }
    }

    public Vector2D getVel(){
        return vel;
    }

}
