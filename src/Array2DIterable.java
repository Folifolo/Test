import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Array2DIterable<T> implements Iterable<T> {
    T[][] array;
    int positionI, positionJ;

    Array2DIterable(T[][] array) {
        this.array = array;
        positionI = 0;
        positionJ = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Array2DIterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for (T[] subArray : array)
            for (T t : subArray)
                action.accept(t);
    }

    @Override
    public Spliterator<T> spliterator() {
        return null;
    }

    class Array2DIterator implements Iterator<T> {
        @Override
        public boolean hasNext() {
            if(array[positionI].length -1 > positionJ )
                return true;
            else return array.length - 1 > positionI;
        }

        @Override
        public T next() {
            if(!hasNext())
                throw new NoSuchElementException();
            if(array[positionI].length -1 > positionJ)
                return array[positionI][positionJ++];

            else {
                int tmpPos = positionJ;
                positionJ = 0;
                return array[positionI++][tmpPos];
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            for(int j = positionJ; j < array[positionI].length; j++)
                action.accept(array[positionI][j]);
            for(int i =positionI+1; i < array.length; i++)
                for(int j = 0; j < array[i].length; j++)
                    action.accept(array[i][j]);
        }

        @Override
        public void remove() {
            T[] res = Arrays.copyOf(array[positionI], array.length-1);
            if (array.length - 1 - positionJ >= 0)
                System.arraycopy(array[positionJ], positionJ + 1, res, positionJ, array.length - 1 - positionJ);
            array[positionI] = res;
        }
    }
}
