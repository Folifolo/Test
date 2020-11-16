import java.util.Arrays;

public class Main {

    private static void getArrayCharacteristics(double[] array) {


        double max = array[0];
        double min = array[0];
        double mean = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] > max)
                max = array[i];
            if (array[i] < min)
                min = array[i];
            mean += array[i];
        }
        mean /= array.length;
        System.out.println("max: " + max + " min: " + min + " mean: " + mean);
    }

    private static double[] generateRandomArray(int size) {
        double[] arr = new double[size];
        for (int i = 0; i < arr.length; i++)
            arr[i] = Math.random();
        return arr;
    }

    private static double[] bubbleSort(double[] array) {
        boolean isArrayChange;
        double tmp;
        int counter = 0;
        do {
            isArrayChange = false;
            for(int i = 0; i < array.length-1; i++) {
                if (array[i] > array[i+1]) {
                    isArrayChange = true;
                    tmp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = tmp;
                }
            }
            counter++;
        } while (isArrayChange);
        System.out.println(counter);
        return array;
    }
    private static void printArray(double[] array) {
        for (int i = 0; i < array.length; i++)
            System.out.println(array[i]);
    }

    private static void primeNumbers(int size) {
        boolean isPrime;
        for(int i = 2; i < size; i++) {
            isPrime = true;
            for(int j = 2; j < i; j++)
            {
                if(i%j == 0){
                    isPrime = false;
                    break;}
            }
            if(isPrime) System.out.println(i);
        }
    }

    private static double[] deleteElement(double[] array, double element) {
        int pointer = 0;
        double[] res = new double[array.length];
        for(int i = 0; i < array.length; i ++) {
            if(array[i] != element)
                res[pointer++] = array[i];
        }
        return Arrays.copyOf(res, pointer); //запомнить!
    }

    static int binarySearch (double value, double[] arr) {
        int rightBorder = arr.length;
        int leftBorder = 0;
        int mid;
        while (Math.abs(rightBorder - leftBorder) > 1) {
            mid = (rightBorder+leftBorder)/2;
            if(value < arr[mid]){
                rightBorder = mid;
            }
            else if (value > arr[mid]) {
                leftBorder = mid;
            }
            else
                return mid;
        }
        return -1;

    }

    static int linearSearch(double value, double[] arr) {
        for(int i = 0; i<arr.length; i++){
            if (arr[i] == value)
                return i;
        }
        return -1;
    }
    
    public static void main(String[] args) {
        UndoStringBuilder str = new UndoStringBuilder("abc");
        str.listenerSetter(new MyListener());
        str.append("waed");
        str.insert(3, "12414");
        str.undoOperation();
    }
}
