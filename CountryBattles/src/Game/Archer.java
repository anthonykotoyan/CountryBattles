package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

public class Archer extends Troop{

    private Color color;
    private Vector2D pos;
    private String type;
    private long lastAttackTime;
    private double attackDist = 600;

    private int barrelLength = 30;
    private Vector2D dir;

    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    private double vel = 1;
    private int size = 15;

    private boolean readyToShoot = false;


    private int coolDownTime = 1500;

    private Random random = new Random();



    public Archer(Vector2D pos, String type, Color color, double angle) {
        super(pos, type, color, angle);
        this.color = super.color;
        this.pos = super.pos;
        this.type = super.type;
        setHealth(2);
        setDamage(.75);
        dir = new Vector2D(Math.cos(angle), Math.sin(angle));


    }



    @Override
    public void update(Graphics g) {
        if (isAlive()) {
            lookTo(.05);


            double dist = (pos.subtract(getTarget().pos)).magnitude();
            if (dist> attackDist) {

                double dx = vel * Math.cos(angle);
                double dy = vel * Math.sin(angle);
                this.pos.x += dx;
                this.pos.y += dy;
            }else{
                attack();
            }

        }
        updateProjectiles(g);
        draw(g);
    }

    @Override
    public void draw(Graphics g){
        if (isAlive()) {

            Graphics2D g2d = (Graphics2D) g;


            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int ovalX = (int) pos.x - size / 2;
            int ovalY = (int) pos.y - size / 2;
            int ovalWidth = size;
            int ovalHeight = size;


            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(3));


            g2d.drawOval(ovalX, ovalY, ovalWidth, ovalHeight);


            g2d.setColor(color);
            g2d.fillOval(ovalX, ovalY, ovalWidth, ovalHeight);


            g2d.setColor(Color.BLACK);

            Vector2D barrelEndPos = new Vector2D((pos.x + barrelLength * dir.x),
                    (pos.y + barrelLength * dir.y));
            g2d.drawLine(
                    (int) pos.x,
                    (int) pos.y,
                    (int) barrelEndPos.x,
                    (int) barrelEndPos.y
            );

            int bowX = (int)pos.x - 20;
            int bowY = (int)pos.y - 20;
            int bowDiameter = size/2 + 40;
            int startAngle = -(int)Math.toDegrees(angle)-45;
            int arcAngle = 90;
            g2d.drawArc(bowX, bowY, bowDiameter, bowDiameter, startAngle, arcAngle);


        }
    }

    @Override
    public void attack() {
        long currentTime = System.currentTimeMillis();


        if (currentTime - lastAttackTime >= coolDownTime) {
            Projectile p = new Projectile(pos, getTarget().getPos(), 2, .2,30, 30,Color.white, false);
            p.ifBall = false;
            p.drawImpact = false;
            dir = (new Vector2D(p.getVel())).normalize();

            projectiles.add(p);
            Main.playSound("CountryBattles/src/Data/bow.wav", .05f);

            lastAttackTime = currentTime;

        }
    }

    public void updateProjectiles(Graphics g){
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            if (projectile.active) {
                projectile.update(g);
            } else {
                double dist = Math.sqrt(Math.pow(getTarget().pos.x - projectile.pos.x, 2) + Math.pow(getTarget().pos.y - projectile.pos.y, 2));
                if (dist < projectile.blastRange) {
                    applyDamage(getTarget());
                    System.out.println(type + " hit " + getTarget().type);
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
