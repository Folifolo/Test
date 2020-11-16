public class Bisection {

    static double findZero(Function func, double leftBorder, double rightBorder, double accuracy) {
        double error = 1;
        double mid = -1;
        while(error > accuracy)
        {
            mid = (leftBorder+rightBorder )/2;
            leftBorder = func.getValue(mid)*func.getValue(rightBorder) < 0 ? mid : leftBorder;
            rightBorder= func.getValue(mid)*func.getValue(leftBorder) < 0 ? mid : rightBorder;
            error = Math.abs(func.getValue(mid));
        }
        return mid;
    }
}

interface Function{
    double getValue(double x);
        }