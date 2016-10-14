package is.ru.machineLearning.learning;

/**
 * Created by Sverrir on 14.10.2016.
 */
public class StateTransition {

    public final State statePrime;
    public final double transitionProbability;

    public StateTransition(State statePrime, double transitionProbability) {
        this.statePrime = statePrime;
        this.transitionProbability = transitionProbability;
    }
}
