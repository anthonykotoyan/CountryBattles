package Game;
public class Vector2D {
    public double x;
    public double y;


    public Vector2D() {
        this(0, 0);
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }



    // Vector addition
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    // Vector subtraction
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    // Scalar multiplication
    public Vector2D scale(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    // Dot product
    public double dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    // Magnitude of the vector
    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    // Normalize the vector
    public Vector2D normalize() {
        double mag = magnitude();
        if (mag == 0) {
            return new Vector2D(0, 0);
        }
        return scale(1 / mag);
    }




}
