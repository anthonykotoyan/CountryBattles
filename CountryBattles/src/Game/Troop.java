package Game;

import java.awt.*;

public class Troop {
    private static int size = 10;
    private Vector2D pos; // Position of the troop
    private double vel = 1; // Velocity
    private double angle = 0; // Angle in radians
    private String id;
    private boolean isAlive;
    private Troop target;

    public Troop(Vector2D pos, String id) {
        this.pos = pos;
        this.id = id;
    }

    public void update(Graphics g) {

        target();

        double dx = vel * Math.cos(angle);
        double dy = vel * Math.sin(angle);

        this.pos.x += dx;
        this.pos.y += dy;


        // Draw the troop
        g.setColor(Color.RED);
        g.fillOval((int) pos.x - size / 2, (int)this.pos.y - size / 2, size, size);
    }

    public void target() {
        if (target == null){
            vel = 0;
        } else{
            vel = 1;
            double deltaX = target.pos.x- this.pos.x;

            double deltaY = target.pos.y - this.pos.y;

            angle = Math.atan2(deltaY, deltaX);
        }

    }
    public void collCheck(){

    }

    public boolean isAlive(){
        return isAlive;
    }

    public void setTarget(Troop target) {
        this.target = target;
    }
    public Troop getTarget() {
        return this.target;
    }
}
