package Game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Soldier extends Troop{

    private Color color;
    private Vector2D pos;
    private String type;


    private int size = 10;

    private Random random = new Random();
    private double vel = Math.random()*.5 +.75;
    private double rotSpeed = (Math.random()/100)*4 + .03;


    private int swordLength = 10;

    public Soldier(Vector2D pos, String type, Color color, double angle) {
        super(pos, type, color, angle);
        this.color = super.color;
        this.pos = super.pos;
        this.type = super.type;



    }



    @Override
    public void update(Graphics g) {
        if (isAlive()) {
            lookTo(.05);
            attack();
            double dx = vel * Math.cos(angle);
            double dy = vel * Math.sin(angle);
            this.pos.x += dx;
            this.pos.y += dy;

            draw(g);
        }

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


        g2d.drawLine((int) pos.x, (int) pos.y, (int) (pos.x + swordLength*Math.cos(angle) ), (int) (pos.y + swordLength*Math.sin(angle) ));
    }

    public void attack() {
        if (getTarget() == null || !getTarget().isAlive()) {
            return;
        }


        double dx = vel * Math.cos(angle);
        double dy = vel * Math.sin(angle);
        double endX = pos.x + dx;
        double endY = pos.y + dy;


        double distance1 = Math.sqrt(Math.pow(endX - getTarget().pos.x, 2) + Math.pow(endY - getTarget().pos.y, 2));
        if (distance1 <= size) {

            applyDamage(getTarget());
            Main.playSound(Main.soundPath, .75f);
            System.out.println(type + " hit " + getTarget().type);
            if (getTarget().type == "h") {
                takeDamage(getHealth());

            }
        }

    }
    @Override
    public double getVel() {
        return vel;
    }


}
