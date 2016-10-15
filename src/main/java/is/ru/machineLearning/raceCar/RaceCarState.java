package is.ru.machineLearning.raceCar;

import is.ru.machineLearning.learning.State;
import is.ru.machineLearning.math.Vector2D;

/**
 * Created by Sverrir on 14.10.2016.
 */
public class RaceCarState extends State {
    public final Vector2D position;
    public final Vector2D velocity;

    public RaceCarState(Vector2D position, Vector2D velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof RaceCarState))
            return false;
        RaceCarState other = (RaceCarState) obj;
        return position.equals(other.position) && velocity.equals(other.velocity);
    }
}
