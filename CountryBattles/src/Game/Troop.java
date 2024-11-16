package Game;

import java.awt.*;

public class Troop {
    private static int size = 10;
    private Vector2D pos; // Position of the troop
    private double vel = 0; // Velocity
    private double angle = 0; // Angle in radians
    private String id; // Troop ID

    public Troop(Vector2D pos, String id) {
        this.pos = pos;
        this.id = id;
    }

    public void update(Graphics g) {
        // Update position based on velocity and angle
        double dx = vel * Math.cos(angle);
        double dy = vel * Math.sin(angle);

        pos.x += dx;
        pos.y += dy;

        // Draw the troop
        g.setColor(Color.RED);
        g.fillOval((int) pos.getX() - size / 2, (int) pos.getY() - size / 2, size, size);
    }

    public void target(Troop other) {

        double deltaX = other.pos.getX() - this.pos.getX();
        double deltaY = other.pos.getY() - this.pos.getY();
        angle = Math.atan2(deltaY, deltaX);
    }
}