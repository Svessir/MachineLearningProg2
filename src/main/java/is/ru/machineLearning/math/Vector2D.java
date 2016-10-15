package is.ru.machineLearning.math;

import is.ru.machineLearning.learning.Action;

/**
 * Created by Sverrir on 12.10.2016.
 */
public class Vector2D implements Action {
    public int x;
    public int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    /**
     * Adds this vector to the supplied vector.
     *
     * @param other The vector to added to this vector.
     * @return The product of the addition
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    /**
     * @param obj The object being tested for equality
     * @return true if obj is a vector with the same x and y values.
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Vector2D))
            return false;
        Vector2D other = (Vector2D) obj;
        return x == other.x && y == other.y;
    }

    /**
     * @return The vector as a string: "(x, y)"
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
