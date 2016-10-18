package is.ru.machineLearning.raceCar;

import is.ru.machineLearning.ArrayIterator;
import is.ru.machineLearning.learning.MarkovDecisionProcess;
import is.ru.machineLearning.learning.StateTransition;
import is.ru.machineLearning.math.Vector2D;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Sverrir on 14.10.2016.
 */
public class RaceCar implements MarkovDecisionProcess<RaceCarState, Vector2D> {

    private float currentValues[][][][];
    private float newValues[][][][];
    private RaceCarState[][][][] states;
    private TrackType[][] track;

    private final int MAX_VX = 4;
    private final int MIN_VX = -4;
    private final int MAX_VY = 4;
    private final int MIN_VY = -4;

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

    /**
     * @param track The track the car is driving on.
     */
    public RaceCar(TrackType[][] track) {
        this.track = track;

        // Initialise the state data structure
        states = new RaceCarState[track.length][track[0].length]
                [Math.abs(MAX_VX) + Math.abs(MIN_VX) + 1][Math.abs(MAX_VY) + Math.abs(MIN_VY) + 1];

        currentValues = new float[track.length][track[0].length]
                [Math.abs(MAX_VX) + Math.abs(MIN_VX) + 1][Math.abs(MAX_VY) + Math.abs(MIN_VY) + 1];

        newValues = new float[track.length][track[0].length]
                [Math.abs(MAX_VX) + Math.abs(MIN_VX) + 1][Math.abs(MAX_VY) + Math.abs(MIN_VY) + 1];

        for(int x = 0; x < states.length; x++) {
            for(int y = 0; y < states[0].length; y++) {
                for(int vx = 0; vx < states[0][0].length; vx++) {
                    for(int vy = 0; vy < states[0][0][0].length; vy++) {
                        states[x][y][vx][vy] = new RaceCarState(new Vector2D(x,y), new Vector2D(vx + MIN_VX, vy + MIN_VY));
                        currentValues[x][y][vx][vy] = 0;
                        newValues[x][y][vx][vy] = 0;
                    }
                }
            }
        }
    }

    /**
     * @return an Iterator over all the states for the race car.
     */
    public Iterator<RaceCarState> getStateIterator() {
        return new RaceCarStateIterator(states, MIN_VX, MIN_VY);
    }

    /**
     * @return an Iterator over all the actions of the race car.
     */
    public Iterator<Vector2D> getActionIterator(RaceCarState state) {
        ArrayList<Vector2D> viableActions = new ArrayList<Vector2D>();
        for(Vector2D action : actions) {
            Vector2D velocity = state.velocity.add(action);
            if(velocity.x >= MIN_VX && velocity.x <= MAX_VX &&
                    velocity.y >= MIN_VY && velocity.y <= MAX_VY)
                viableActions.add(action);
        }
        return viableActions.iterator();
    }

    public double getValue(RaceCarState state) {
        return state.getValue();
    }

    public void setValue(RaceCarState state, double value) { state.setValue(value);}

    /**
     * Returns the immediate reward for a given
     * transition on the track.
     *
     * @param transition The transition of the car
     * @return the immediate reward for the transition.
     */
    public double getReward(StateTransition<RaceCarState, Vector2D> transition) {
        TrackType primeTrackType = getStateTrackType(transition.statePrime);

        if(isOutsideOfTrack(transition))
            return -5;
        else if(primeTrackType == TrackType.FINISH)
            return 5;

        return -1;
    }

    /**
     * Returns an iterator over all the state transition possible
     * given a state and an action.
     *
     * @param state The state being transitioned from.
     * @param action The action being taken in the state.
     * @return An iterator over all the state transitions.
     */
    public Iterator<StateTransition> getStateTransitionIterator(RaceCarState state, Vector2D action) {
        StateTransition[] transitions = {
                getTransition(state, action, Drift.NO_DRIFT, 0.50),     // Normal transition
                getTransition(state, action, Drift.DRIFT_RIGHT, 0.25),  // Transition with drift to the right
                getTransition(state, action, Drift.DRIFT_UP, 0.25)      // Transition with drift up
        };
        return new ArrayIterator<StateTransition>(transitions);
    }

    /**
     * Stores the values that have been set as the current values
     */
    public void useValues() {

    }

    /**
     * Get the optimal action for the current state
     * of the car.
     *
     * @return the optimal action.
     */
    public Vector2D getOptimalPolicyAction() {
        return null;
    }


    /**
     * Set the current state of the car.
     *
     * @param state The state being set to current state.
     */
    public void setState(RaceCarState state) {

    }

    /**
     * Apply an action to the car. Might
     * cause the car to transition between
     * different states.
     *
     * @param action The action being taken.
     */
    public void transition(Vector2D action) {

    }

    /**
     * Determines whether a position is out of the track bounds.
     *
     * @param position The position being tested.
     * @return true if out of bounds else false.
     */
    private boolean isOutOfBounds(Vector2D position) {
        return position.x < 0 || position.x >= track.length || position.y < 0 || position.y >= track[0].length;
    }

    /**
     * @param state A state of the race car.
     * @return The track type this state is positioned in: {OUTSIDE, INSIDE, START, FINISH}.
     */
    private TrackType getStateTrackType(RaceCarState state) {
        if(isOutOfBounds(state.position))
            return TrackType.OUTSIDE;
        return track[state.position.x][state.position.y];
    }

    /**
     * Determines whether the state of the car is outside of the track
     * the or collided with a wall.
     *
     * @param transition A state transition of the car
     * @return true if the car is outside of the track or collided to a wall else false
     */
    private boolean isOutsideOfTrack(StateTransition<RaceCarState, Vector2D> transition) {

        if(getStateTrackType(transition.statePrime) == TrackType.OUTSIDE ||
                (transition.state.velocity.add(transition.action).length() > 0
                        && transition.state.equals(transition.statePrime)))
            return true;
        return false;
    }

    /**
     * Creates the transition given a state, action and a drift factor.
     *
     * @param state The state being transitioned from.
     * @param action The action being taken.
     * @param drift The drift that happens during the action.
     * @param transitionProbability The probability of the transition.
     * @return The transition that includes the state that is transitioned to.
     */
    private StateTransition<RaceCarState, Vector2D> getTransition(RaceCarState state, Vector2D action,
                                                                  Drift drift, double transitionProbability) {
        Vector2D velocity = state.velocity.add(action);
        Vector2D actualAction = velocity.add(drift.getDrift());
        Vector2D primePosition = state.position.add(actualAction);
        RaceCarState prime = isOutOfBounds(primePosition) ? state :
                states[primePosition.x][primePosition.y][velocity.x - MIN_VX][velocity.y - MIN_VY];
        return new StateTransition<RaceCarState, Vector2D>(state, prime, action, transitionProbability);
    }
}
