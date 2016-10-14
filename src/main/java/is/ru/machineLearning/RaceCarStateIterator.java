package is.ru.machineLearning;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;

/**
 * Created by Sverrir on 14.10.2016.
 */
public class RaceCarStateIterator implements Iterator<RaceCarState>{

    private RaceCarState[][][][] states;
    private RaceCarState currentState = null;

    public RaceCarStateIterator(RaceCarState[][][][] states) {
        this.states = states;
    }

    public boolean hasNext() {
        //TODO: implement
        return false;
    }

    public RaceCarState next() {
        //TODO: implement
        return null;
    }

    public void remove() {
        throw new NotImplementedException();
    }
}
