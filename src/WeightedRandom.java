import java.util.Arrays;

public class WeightedRandom {


    int[] values;
    int[] weights;
    int denum;
    public WeightedRandom(int[] values, int[] weights) {
        if (values.length != weights.length)
            throw new ArrayIndexOutOfBoundsException();
        this.values = Arrays.copyOf(values, values.length);
        this.weights = Arrays.copyOf(weights, weights.length);
        denum = 0;
        for(int weight: weights){ //запомнить!
            denum += weight;
        }
    }

    public int generate() {
        double random = Math.random();
        double counter = 0;
        int res = 15;
        for(int i = 0; i < weights.length; i++) {
            counter += Double.valueOf(weights[i])/denum;
            if(random < counter) {
                res = values[i];
                break;
            }
        }
        return res;
    }
}
