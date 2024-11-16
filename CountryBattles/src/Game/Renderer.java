package Game;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {

    Troop troop1 = new Troop(new Vector2D(300,300), "a");
    Troop troop2 = new Troop(new Vector2D(600,300), "32");
    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        troop1.update(g);
        troop2.update(g);
        troop1.target(troop2);

    }

    public void update() {
    }
}
