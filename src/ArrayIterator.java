import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class ArrayIterator<T> implements Iterator<T> {
    T[] array;
    int pointer;

    ArrayIterator(T[] array) {
        this.array = array;
        pointer = 0;
    }

    @Override
    public boolean hasNext() {
        return pointer < array.length - 1;
    }

    @Override
    public T next() {
        if(!hasNext())
            throw new NoSuchElementException();
        return array[pointer++];
    }

    @Override
    public void remove() {
        T[] res = Arrays.copyOf(array, array.length-1);
        if (array.length - 1 - pointer >= 0)
            System.arraycopy(array, pointer + 1, res, pointer, array.length - 1 - pointer);
        array = res;
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        for(int i =pointer; i < array.length; i++)
            action.accept(array[i]);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (T t : array) res.append(" ").append(t);
        return res.toString();
    }
}
