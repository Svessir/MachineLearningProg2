package is.ru.machineLearning.learning;

/**
 * Created by Sverrir on 14.10.2016.
 */
public class StateTransition<T extends State, A extends  Action> {

    public final T state;
    public final T statePrime;
    public final A action;
    public final double transitionProbability;

    public StateTransition(T state , T statePrime, A action, double transitionProbability) {
        this.state = state;
        this.statePrime = statePrime;
        this.action = action;
        this.transitionProbability = transitionProbability;
    }
}
