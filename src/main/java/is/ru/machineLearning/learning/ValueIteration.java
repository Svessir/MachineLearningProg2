package is.ru.machineLearning.learning;

import java.util.Iterator;

/**
 * Created by Sverrir on 14.10.2016.
 */
public class ValueIteration {

    private MarkovDecisionProcess markovDecisionProcess;
    private double gamma;
    private double threshold;

    /**
     *
     * @param markovDecisionProcess The markov decision process that needs to be solved.
     *
     * @param gamma The gamma value for the value iteration between 0 and 1. The gamma value
     *              determines how much the agent values future reward over immediate rewards.
     *              With a gamma value of 0 the agent dismisses all future rewards while with a
     *              gamma value of 1 the agent gives all rewards equal weight.
     *
     * @param threshold A small positive value that determines when to stop the value iteration.
     *                  The value iteration stops if the maximum change of value in the whole state
     *                  space drops beneath this threshold.
     *
     * @throws IllegalArgumentException If the markovDecisionProcess is null or if the gamma is not within the right range.
     */
    public ValueIteration(MarkovDecisionProcess markovDecisionProcess, double gamma, double threshold) throws IllegalArgumentException {
        if(gamma < 0 || gamma > 1)
            throw gamma < 0 ? new IllegalArgumentException("Gamma value to low.") : new IllegalArgumentException("Gamma value to high.");
        if(markovDecisionProcess == null)
            throw new IllegalArgumentException("markovDecisionProcess cannot be null");

        this.markovDecisionProcess = markovDecisionProcess;
        this.gamma = gamma;
        this.threshold = threshold;
    }

    /**
     * Starts the value iteration.
     */
    public void solve() {
        double delta;

        do {
            delta = 0;
            Iterator<State> stateIterator = markovDecisionProcess.getStateIterator();
            //System.out.println("number of States: " + stateIterator);
            int i = 0;
            while(stateIterator.hasNext()) {
                i++;
                State state = stateIterator.next();

                double value = markovDecisionProcess.getValue(state);
                Iterator<Action> actions = markovDecisionProcess.getActionIterator(state);
                double newValue = -Double.MAX_VALUE;

                while (actions.hasNext()) {
                    Action action = actions.next();

                    Iterator<StateTransition> transitions =
                            markovDecisionProcess.getStateTransitionIterator(state, action);

                    while (transitions.hasNext()) {
                        StateTransition transition = transitions.next();
                        newValue = Math.max(newValue,
                                transition.transitionProbability * (markovDecisionProcess.getReward(transition)
                                + gamma * markovDecisionProcess.getValue(transition.statePrime)));
                    }
                }

                // Update the state with the new value
                markovDecisionProcess.setValue(state, newValue);

                // Update delta if the change in value for this state is the highest
                delta = Math.max(delta, Math.abs(value - newValue));
            }
            //System.out.println("states iterated over: " + i);
            System.out.println("Delta: " + delta);
        }while(delta > threshold);
    }
}
