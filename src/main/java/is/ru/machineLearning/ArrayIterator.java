package is.ru.machineLearning;

import java.util.Iterator;

/**
 * Created by Sverrir on 14.10.2016.
 */
public class ArrayIterator<T> implements Iterator<T> {

    private T[] array;
    int i = 0;

    public ArrayIterator(T[] array) {
        this.array = array;
    }
    public boolean hasNext() {
        if(i < array.length)
            return true;
        return false;
    }

    public T next() {
        return array[i++];
    }

    public void remove() {
    }
}
