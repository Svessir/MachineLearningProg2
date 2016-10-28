package is.ru.machineLearning.raceCar;

import is.ru.machineLearning.ArrayIterator;
import is.ru.machineLearning.learning.MarkovDecisionProcess;
import is.ru.machineLearning.learning.StateTransition;
import is.ru.machineLearning.math.Vector2D;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Sverrir on 14.10.2016.
 */
public class RaceCar implements MarkovDecisionProcess<RaceCarState, Vector2D> {

    private double currentValues[][][][];
    private double newValues[][][][];
    private RaceCarState[][][][] states;
    private TrackType[][] track;

    private final int MAX_VX = 4;
    private final int MIN_VX = -4;
    private final int MAX_VY = 4;
    private final int MIN_VY = -4;


    private RaceCarState currentState;

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

        currentValues = new double[track.length][track[0].length]
                [Math.abs(MAX_VX) + Math.abs(MIN_VX) + 1][Math.abs(MAX_VY) + Math.abs(MIN_VY) + 1];

        newValues = new double[track.length][track[0].length]
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
        if(track[state.position.x][state.position.y] == TrackType.FINISH)
            return viableActions.iterator();
        for(Vector2D action : actions) {
            Vector2D velocity = state.velocity.add(action);
            if(velocity.x >= MIN_VX && velocity.x <= MAX_VX &&
                    velocity.y >= MIN_VY && velocity.y <= MAX_VY)
                viableActions.add(action);
        }
        return viableActions.iterator();
    }

    /**
     * Gets a value of a state.
     *
     * @param state The state being queried for value.
     * @return The value of the state.
     */
    public double getValue(RaceCarState state) {
        return currentValues[state.position.x][state.position.y][state.velocity.x - MIN_VX][state.velocity.y - MIN_VY];
    }

    /**
     * Assigns a value to a state.
     *
     * @param state The state that is being assigned a value.
     * @param value The new value of the state.
     */
    public void setValue(RaceCarState state, double value) {
        newValues[state.position.x][state.position.y][state.velocity.x - MIN_VX][state.velocity.y - MIN_VY] = value;
    }

    /**
     * Returns the immediate reward for a given
     * transition on the track.
     *
     * @param transition The transition of the car
     * @return the immediate reward for the transition.
     */
    public double getReward(StateTransition<RaceCarState, Vector2D> transition) {
        if(isOutsideOfTrack(transition)) {
            return -5;
        }

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
        return new ArrayIterator<StateTransition>(getAllTransitions(state, action));
    }

    /**
     * Stores the values that have been set as the current values
     * of the states.
     */
    public void useValues() {
        currentValues = newValues;
        newValues = new double[newValues.length][newValues[0].length][newValues[0][0].length][newValues[0][0][0].length];
    }

    /**
     * Get the optimal action for the current state
     * of the car.
     *
     * @return the optimal action.
     */
    public Vector2D getOptimalPolicyAction() {
        if(track[currentState.position.x][currentState.position.y] == TrackType.FINISH)
            return null;

        Iterator<Vector2D> viableActions = getActionIterator(currentState);
        Vector2D optimalAction = null;
        double optimalValue = Double.NEGATIVE_INFINITY;

        while(viableActions.hasNext()) {
            Vector2D action = viableActions.next();

            Vector2D vel = currentState.velocity.add(action);
            Vector2D pos = currentState.position.add(vel);

            if(pos.x < 0)
                pos.x = 0;
            if(pos.x > track.length - 1)
                pos.x = track.length - 1;
            if(pos.y < 0)
                pos.y = 0;
            if(pos.y > track[0].length - 1)
                pos.y = track[0].length - 1;

            double val  = currentValues[pos.x][pos.y][vel.x - MIN_VX][vel.y - MIN_VY];

            if(val > optimalValue) {
                optimalValue = val;
                optimalAction = action;
            }
        }
        return optimalAction;
    }


    /**
     * Set the current state of the car.
     *
     * @param state The state being set to current state.
     */
    public void setState(RaceCarState state) {
        this.currentState = state;
    }

    /**
     * Apply an action to the car. Might
     * cause the car to transition between
     * different states.
     *
     * @param action The action being taken.
     */
    public void transition(Vector2D action) {
        Random random = new Random();
        StateTransition<RaceCarState, Vector2D>[] transitions = getAllTransitions(currentState, action);
        double probability = random.nextDouble();

        if(probability <= 0.5)
            currentState = transitions[0].statePrime;
        else if(probability <= .75)
            currentState = transitions[1].statePrime;
        else
            currentState = transitions[2].statePrime;
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
        Vector2D actualAction = velocity.length() > 0 ? velocity.add(drift.getDrift()) : velocity;
        Vector2D primePosition = state.position.add(actualAction);

        if(primePosition.x < 0)
            primePosition.x = 0;
        if(primePosition.x > track.length - 1)
            primePosition.x = track.length - 1;
        if(primePosition.y < 0)
            primePosition.y = 0;
        if(primePosition.y > track[0].length - 1)
            primePosition.y = track[0].length - 1;

        RaceCarState prime = states[primePosition.x][primePosition.y][velocity.x - MIN_VX][velocity.y - MIN_VY];
        return new StateTransition<RaceCarState, Vector2D>(state, prime, action, transitionProbability);
    }

    /**
     * @param state The current state.
     * @param action The action being taken in the current state.
     * @return All possible transitions for this state action pair.
     */
    private StateTransition[] getAllTransitions(RaceCarState state, Vector2D action) {
        StateTransition[] transitions = {
                getTransition(state, action, Drift.NO_DRIFT, 0.50),     // Normal transition
                getTransition(state, action, Drift.DRIFT_RIGHT, 0.25),  // Transition with drift to the right
                getTransition(state, action, Drift.DRIFT_UP, 0.25)      // Transition with drift up
        };

        return transitions;
    }

    /**
     * Prints out the current values of the states with velocity 0.
     */
    public void print() {
        for(int x = 0; x < track.length; x++) {
            for(int y = 0; y < track[0].length; y++) {
                System.out.printf("%.1f ", currentValues[x][y][4][4]);
                //System.out.print((int)currentValues[x][y][4][4]);
            }
            System.out.println();
        }
    }

    /**
     * @return The current state of the car in a String.
     */
    @Override
    public String toString() {
        String s = "\n";
        for(int x = 0; x < track.length; x++) {
            for(int y = 0; y < track[0].length; y++) {
                if(currentState.position.x == x && currentState.position.y == y)
                    s += "X";
                else
                    s += track[x][y];
            }
            s += "\n";
        }
        return s;
    }

    /**
     * Prints out the current position of the car.
     */
    public void printPosition() {
        if(currentState != null)
            System.out.println("currentPosition: " + currentState.position);
    }
}
