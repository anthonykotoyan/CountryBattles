package Game;

import java.awt.*;
import java.util.ArrayList;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Troop {
    private static int size = 10;
    private Vector2D pos; // Position of the troop
    private double vel = 1; // Velocity
    private double angle = 0; // Angle in radians
    private String id;
    private boolean isAlive = true;
    private Troop target;
    private Object colors;
    public Troop(Vector2D pos, String id) {
        this.pos = pos;
        this.id = id;

        // Find the index of the country code in the data
        ArrayList<String> countryCodes = (ArrayList<String>) Main.data[1]; // Assuming column 1 is the country codes
        int index = countryCodes.indexOf(id);

        if (index != -1) {
            // Get the corresponding color
            ArrayList<String> colorList = (ArrayList<String>) Main.data[7]; // Assuming column 7 is the color codes
            String colorHex = colorList.get(index);

            // Parse the hex color to Color object
            this.colors = Color.decode(colorHex);
        } else {
            // Default color if the ID is not found
            this.colors = Color.GRAY;
        }
    }



    public void update(Graphics g) {
        if (isAlive) {
            target();
            collCheck();
            double dx = vel * Math.cos(angle);
            double dy = vel * Math.sin(angle);

            this.pos.x += dx;
            this.pos.y += dy;

            // Cast colors to Color object and assign it
            Color troopColor = (Color) this.colors; // Use the assigned color

            // Create Graphics2D object for anti-aliasing
            Graphics2D g2d = (Graphics2D) g;

            // Enable anti-aliasing
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int ovalX = (int) pos.x - size / 2;
            int ovalY = (int) pos.y - size / 2;
            int ovalWidth = size;
            int ovalHeight = size;

            // Set the outline color (black) and stroke thickness
            g2d.setColor(Color.BLACK); // Outline color
            g2d.setStroke(new BasicStroke(3)); // Set outline thickness (3 pixels)

            // Draw the outline (border) of the oval
            g2d.drawOval(ovalX, ovalY, ovalWidth, ovalHeight);

            // Now fill the oval with the troop's color
            g2d.setColor(troopColor); // Fill color
            g2d.fillOval(ovalX, ovalY, ovalWidth, ovalHeight);

            // Draw the stick representing the troop's direction
            g2d.setColor(Color.BLACK); // Color of the stick
            g2d.drawLine((int) pos.x, (int) pos.y, (int) (pos.x + dx * 10), (int) (pos.y + dy * 10)); // Multiply by 10 to make the stick longer
        }
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
    public void collCheck() {
        if (target == null || !target.isAlive()) {
            return; // No target or target is already dead
        }

        // Calculate the point at the end of the troop's movement vector
        double dx = vel * Math.cos(angle);
        double dy = vel * Math.sin(angle);
        double endX = pos.x + dx;
        double endY = pos.y + dy;

        // Check if the end point is near the target's position
        double distance1 = Math.sqrt(Math.pow(endX - target.pos.x, 2) + Math.pow(endY - target.pos.y, 2));
        if (distance1 <= size) { // If the distance is within the radius of the target troop
            target.kill();
            Main.playSound(Main.soundPath);

        }

    }


    public boolean isAlive(){
        return isAlive;
    }
    public void kill(){
        isAlive = false;
    }


    public void setTarget(Troop target) {
        this.target = target;
    }
    public Troop getTarget() {
        return this.target;
    }
}
