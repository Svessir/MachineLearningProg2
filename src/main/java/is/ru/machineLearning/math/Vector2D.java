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
}
