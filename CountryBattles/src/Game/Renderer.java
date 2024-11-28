package Game;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {
    private LevelManager levelManager;
    private Level[] levels = {
            new Level(new int[]{2, 0, 0}, 30),
            new Level(new int[]{3, 2, 0}, 80),
            new Level(new int[]{3, 2, 2}, 140),
            new Level(new int[]{5, 3, 2}, 200),
            new Level(new int[]{10, 5, 3}, 300),
            new Level(new int[]{100, 30, 10}, 1500),
            new Level(new int[]{1, 5, 5}, 230),


    };
    public Renderer() {


        setLayout(new BorderLayout()); // Use BorderLayout to organize components
        setBackground(new Color(0, 100, 0)); // Background color
        levelManager = new LevelManager(levels);
        levelManager.setNewLevel(this);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        levelManager.playCurrLevel(g,this);
    }


}
