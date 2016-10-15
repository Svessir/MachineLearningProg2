package is.ru.machineLearning.learning;

/**
 * Created by Sverrir on 13.10.2016.
 */
public abstract class State {
    private double value = 0;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
