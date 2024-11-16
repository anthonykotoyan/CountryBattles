package Game;

import javax.swing.*;

public class Window extends JFrame {
    private Renderer renderer;

    public Window() {
        // Initialize the renderer
        renderer = new Renderer();

        // Set up the JFrame
        setTitle("Game Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setResizable(false);
        setLocationRelativeTo(null);

        // Add the renderer to the JFrame
        add(renderer);

        // Make the window visible
        setVisible(true);
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void update() {
        // Update the renderer (or other game state)
        renderer.update();
    }
}
