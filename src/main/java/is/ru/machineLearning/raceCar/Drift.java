package is.ru.machineLearning.raceCar;

import is.ru.machineLearning.math.Vector2D;

/**
 * Created by Sverrir on 15.10.2016.
 */
public enum Drift {
    NO_DRIFT (new Vector2D(0, 0)),
    DRIFT_RIGHT (new Vector2D(1, 0)),
    DRIFT_UP (new Vector2D(0, 1));

    private final Vector2D drift;

    Drift(Vector2D drift) {
        this.drift = drift;
    }

    public final Vector2D getDrift() {
        return drift;
    }
}
