package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

public class Tank extends Troop{

    private Color color;
    private Vector2D pos;
    private String type;
    private long lastAttackTime;

    private int barrelLength = 30;
    private Vector2D dir;

    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    private double vel = .25;
    private int size = 30;

    private boolean readyToShoot = false;


    private int coolDownTime;

    private Random random = new Random();



    public Tank(Vector2D pos, String type, Color color, double angle) {
        super(pos, type, color, angle);
        this.color = super.color;
        this.pos = super.pos;
        this.type = super.type;
        setHealth(2);
        setDamage(2);
        dir = new Vector2D(Math.cos(angle), Math.sin(angle));

        coolDownTime = random.nextInt(1001) + 3000;
        lastAttackTime = System.currentTimeMillis();
    }



    @Override
    public void update(Graphics g) {
        if (isAlive()) {
            lookTo(.024);
            attack();


            double dx = vel * Math.cos(angle);
            double dy = vel * Math.sin(angle);
            this.pos.x += dx;
            this.pos.y += dy;

            draw(g);
        }
        updateProjectiles(g);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform originalTransform = g2d.getTransform();


        g2d.setColor(color);


        int rectWidth = size;
        int rectHeight = size/2;
        int rectX = (int) pos.x - rectWidth / 2;
        int rectY = (int) pos.y - rectHeight / 2;


        g2d.rotate(angle, pos.x, pos.y);


        g2d.fillRect(rectX, rectY, rectWidth, rectHeight);


        g2d.setTransform(originalTransform);

        g2d.setColor(Color.BLACK);
        Vector2D barrelEndPos = new Vector2D((pos.x + barrelLength * dir.x),
                (pos.y + barrelLength * dir.y));
        g2d.drawLine(
                (int) pos.x,
                (int) pos.y,
                (int) barrelEndPos.x,
                (int) barrelEndPos.y
        );
    }

    @Override
    public void attack() {
        long currentTime = System.currentTimeMillis();

        // Check if cooldown time has passed
        if (currentTime - lastAttackTime >= coolDownTime) {
            Projectile p = new Projectile(pos, getTarget().getPos(), 10);
            dir = (new Vector2D(p.getVel())).normalize();

            projectiles.add(p);
            Main.playSound("CountryBattles/src/Data/explosionHit.wav", .05f);

            lastAttackTime = currentTime;
            coolDownTime = random.nextInt(3001) + 5000;
        }
    }

    public void updateProjectiles(Graphics g){
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            if (projectile.active) {
                projectile.update(g);
            } else {
                for (Troop enemy : enemyTroops) {
                    double dist = Math.sqrt(Math.pow(enemy.pos.x - projectile.pos.x, 2) + Math.pow(enemy.pos.y - projectile.pos.y, 2));
                    if (dist < projectile.blastRange) {
                        applyDamage(enemy);
                        System.out.println(type + " hit " + getTarget().type);
                    }
                }
                projectiles.remove(i);
                i--;
            }
        }
    }
    @Override
    public double getVel() {
        return vel;
    }



}
