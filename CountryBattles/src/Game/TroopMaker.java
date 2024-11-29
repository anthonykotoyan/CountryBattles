package Game;

import java.awt.*;
import java.util.Random;

public class TroopMaker {
    private static final Random rand = new Random();

    public static Troop createTroop(String type, Vector2D position, Color color, double direction) {
        return switch (type) {
            case "s" -> new Soldier(position, "s", color, direction);
            case "a" -> new Archer(position, "a", color, direction);
            case "t" -> new Tank(position, "t", color, direction);
            case "h" -> new Helicopter(position, "h", color, direction);
            default -> throw new IllegalArgumentException("Unknown troop type: " + type);
        };
    }
}
