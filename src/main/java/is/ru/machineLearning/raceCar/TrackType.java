package is.ru.machineLearning.raceCar;

/**
 * Created by Sverrir on 15.10.2016.
 */
public enum TrackType {
    OUTSIDE("O"),
    START("S"),
    INSIDE("I"),
    FINISH("F");

    private String value;

    private TrackType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
