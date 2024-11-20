package Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Helicopter extends Troop{

    private Color color;
    private Vector2D pos;
    private String type;
    private long lastAttackTime;

    private double attackDist = 500;
    private boolean drawExplosion = false;
    private int barrelLength = 30;
    private Vector2D dir;

    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    private double vel = 1.25;
    private int size = 30;

    private double propellerAngle = 0;
    private double propellerSpeed = 1;

    private boolean readyToShoot = false;


    private int coolDownTime = 100;

    private Random random = new Random();



    public Helicopter(Vector2D pos, String type, Color color, double angle) {
        super(pos, type, color, angle);
        this.color = super.color;
        this.pos = super.pos;
        this.type = super.type;
        setHealth(1);
        setDamage(.2);
        dir = new Vector2D(Math.cos(angle), Math.sin(angle));


        lastAttackTime = System.currentTimeMillis();
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
            draw(g);
        }if(drawExplosion){
            drawExplosion(g);
            drawExplosion = false;
        }
        updateProjectiles(g);
    }

    @Override
    public void draw(Graphics g){


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

        g2d.drawLine((int) (pos.x + size*Math.cos(angle)/2) , (int) (pos.y + size*Math.sin(angle)/2) , (int) (pos.x + barrelLength*Math.cos(angle) ), (int) (pos.y + barrelLength*Math.sin(angle) ));

        // draw propeller

        propellerAngle += propellerSpeed;

        g2d.drawLine(
                (int) (pos.x) ,
                (int) (pos.y) ,
                (int) (pos.x + barrelLength*Math.cos(propellerAngle) ),
                (int) (pos.y + barrelLength*Math.sin(propellerAngle) ));
        g2d.drawLine(
                (int) (pos.x) ,
                (int) (pos.y) ,
                (int) (pos.x + barrelLength*Math.cos(propellerAngle+Math.PI/2) ),
                (int) (pos.y + barrelLength*Math.sin(propellerAngle+Math.PI/2) ));
        g2d.drawLine(
                (int) (pos.x) ,
                (int) (pos.y) ,
                (int) (pos.x + barrelLength*Math.cos(propellerAngle+Math.PI) ),
                (int) (pos.y + barrelLength*Math.sin(propellerAngle+Math.PI) ));
        g2d.drawLine(
                (int) (pos.x) ,
                (int) (pos.y) ,
                (int) (pos.x + barrelLength*Math.cos(propellerAngle+Math.PI*3/2) ),
                (int) (pos.y + barrelLength*Math.sin(propellerAngle+Math.PI*3/2) ));
    }

    @Override
    public void attack() {
        long currentTime = System.currentTimeMillis();


        if (currentTime - lastAttackTime >= coolDownTime) {
            Projectile p = new Projectile(pos, getTarget().getPos(), 4, 0);
            dir = (new Vector2D(p.getVel())).normalize();

            projectiles.add(p);
            Main.playSound("CountryBattles/src/Data/explosionHit.wav", .05f);

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
    public void drawExplosion(Graphics g){
        int ovalX = (int) pos.x - size / 2;
        int ovalY = (int) pos.y - size / 2;
        int ovalWidth = 20;
        int ovalHeight = 20;
        g.setColor(Color.ORANGE);
        g.fillOval(ovalX, ovalY, ovalWidth, ovalHeight);
    }


}
