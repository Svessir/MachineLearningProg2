package is.ru.machineLearning.raceCar;

import is.ru.machineLearning.ArrayIterator;
import is.ru.machineLearning.learning.MarkovDecisionProcess;
import is.ru.machineLearning.learning.StateTransition;
import is.ru.machineLearning.math.Vector2D;

import java.util.Iterator;

/**
 * Created by Sverrir on 14.10.2016.
 */
public class RaceCar implements MarkovDecisionProcess<RaceCarState, Vector2D> {

    private RaceCarState[][][][] states;

    private Vector2D[] actions =
            {
                    new Vector2D(0, 1),
                    new Vector2D(1, 0),
                    new Vector2D(0, -1),
                    new Vector2D(-1, 0),
                    new Vector2D(0, 0),
                    new Vector2D(1, 1),
                    new Vector2D(1, -1),
                    new Vector2D(-1, 1),
                    new Vector2D(-1, -1),
            };

    // TODO: supply race car with a track through the constructor
    public RaceCar() {

    }

    /**
     * @return an Iterator over all the states for the race car.
     */
    public Iterator<RaceCarState> getStateIterator() {
        return new RaceCarStateIterator(states);
    }

    /**
     * @return an Iterator over all the actions of the race car.
     */
    public Iterator<Vector2D> getActionIterator() {
        return new ArrayIterator<Vector2D>(actions);
    }

    public double getValue(RaceCarState state) {
        return 0;
    }

    public double setValue(RaceCarState state, double value) {
        return 0;
    }

    /**
     * Returns the immediate reward of a given
     * race car state.
     *
     * @param state a state of the race car.
     * @return The immediate reward for the state.
     */
    public double getReward(RaceCarState state) {
        return 0;
    }

    /**
     * Returns an iterator over all the state transition possible
     * given a state and an action
     *
     * @param state The state being transitioned from.
     * @param action The action being taken in the state.
     * @return An iterator over all the state transitions.
     */
    public Iterator<StateTransition> getStateTransitionIterator(RaceCarState state, Vector2D action) {
        StateTransition[] transitions = { // TODO: change states[][][][]
                new StateTransition(states[0][0][0][0], 0.5),   // Normal transition
                new StateTransition(states[0][0][0][0], 0.25),  // Transition with drift to the right
                new StateTransition(states[0][0][0][0], 0.25)   // Transition with drift up
        };
        return new ArrayIterator<StateTransition>(transitions);
    }
}
