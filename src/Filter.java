import java.util.Arrays;
import java.util.function.Function;

public interface Filter<T> {
    boolean apply(T object);
}

class Filtrate {
    static <T> T[] filtrate(T[] array, Filter<T> filter) {
        int pointer = 0;
        T[] res = Arrays.copyOf(array, array.length);
        for(int i = 0; i < res.length; i++)
            if(filter.apply(res[i])){
                res[pointer] = res[i];
                pointer++;
            }
        res = Arrays.copyOf(res, pointer);
        return res;
    }
}

class Fill {

    static <T> T[] fill(T[] array, Function<T, T> filler) {
        T[] res = Arrays.copyOf(array, array.length);
        for(int i = 0; i < res.length; i++) {
            res[i] = filler.apply(res[i]);
            }

        return res;

    }
}


