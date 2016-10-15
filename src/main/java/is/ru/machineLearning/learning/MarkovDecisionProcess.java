package is.ru.machineLearning.learning;

import java.util.Iterator;

/**
 * Created by Sverrir on 13.10.2016.
 */
public interface MarkovDecisionProcess<S extends State, A extends Action> {
    Iterator<S> getStateIterator();
    Iterator<A> getActionIterator(S state);
    double getValue(S state);
    void setValue(S state, double value);
    double getReward(StateTransition<S, A> transition);
    Iterator<StateTransition> getStateTransitionIterator(S state, A action);
}
