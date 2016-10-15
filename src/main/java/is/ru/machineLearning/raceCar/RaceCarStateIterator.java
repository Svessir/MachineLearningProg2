package is.ru.machineLearning.raceCar;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;

/**
 * Created by Sverrir on 14.10.2016.
 */
public class RaceCarStateIterator implements Iterator<RaceCarState>{

    private RaceCarState[][][][] states;
    private RaceCarState currentState = null;
    private final int MIN_VX;
    private final int MIN_VY;

    /**
     * @param states The states iterated over by the iterator.
     * @param MIN_VX The minimum value for velocity.x
     * @param MIN_VY The minimum value for velocity.y
     */
    public RaceCarStateIterator(RaceCarState[][][][] states, int MIN_VX, int MIN_VY) {
        this.states = states;
        this.MIN_VX = MIN_VX;
        this.MIN_VY = MIN_VY;
    }

    /**
     * Checks if there are any more states left to iterate.
     *
     * @return true if the iterator hasn't iterated over all the states else false.
     */
    public boolean hasNext() {
        if(currentState != null && isLastX(currentState) && isLastY(currentState) &&
                isLastVX(currentState) && isLastVY(currentState))
            return false;
        return true;
    }

    /**
     * Retrieves the next race car state.
     *
     * @return the next state.
     */
    public RaceCarState next() {
        if(currentState == null)
            currentState = states[0][0][0][0];
        else if(!isLastVY(currentState))
            currentState = states[currentState.position.x][currentState.position.y]
                    [currentState.velocity.x - MIN_VX][currentState.velocity.y - MIN_VY + 1];
        else if(!isLastVX(currentState))
            currentState = states[currentState.position.x][currentState.position.y]
                    [currentState.velocity.x - MIN_VX + 1][0];
        else if(!isLastY(currentState))
            currentState = states[currentState.position.x][currentState.position.y + 1][0][0];
        else if(!isLastX(currentState)) {
            currentState = states[currentState.position.x + 1][0][0][0];
        }

        return currentState;
    }

    public void remove() {
        throw new NotImplementedException();
    }

    /**
     * Checks if the state is at the highest x value on the track.
     *
     * @param state The state being checked.
     * @return true if the state has the highest x value.
     */
    private boolean isLastX(RaceCarState state) {
        return !(state.position.x < states.length - 1);
    }

    /**
     * Checks if the state is at the highest y value on the track.
     *
     * @param state The state being checked.
     * @return true if the state has the highest y value.
     */
    private boolean isLastY(RaceCarState state) {
        return !(state.position.y < states[0].length - 1);
    }

    /**
     * Checks if the state has the highest velocity x value possible.
     *
     * @param state The state being checked.
     * @return true if the state has the highest velocity x value.
     */
    private boolean isLastVX(RaceCarState state) {
        return !(state.velocity.x - MIN_VX < states[0][0].length - 1);
    }

    /**
     * Checks if the state has the highest velocity y value possible.
     *
     * @param state The state being checked.
     * @return true if the state has the highest velocity y value.
     */
    private boolean isLastVY(RaceCarState state) {
        return !(state.velocity.y - MIN_VY < states[0][0][0].length - 1);
    }

    public String toString() {
        return "" + (states.length * states[0].length * states[0][0].length * states[0][0][0].length);
    }
}
