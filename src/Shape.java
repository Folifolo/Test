import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

interface Shape extends Comparable<Shape>{

    double getVolume();

    @Override
    default int compareTo(Shape o) { //запомнить! default
        return Double.compare(getVolume(), o.getVolume()); //запомнить!
    }
}

abstract class SolidOfRevolutionSimple implements   Shape {
    double radius;

    SolidOfRevolutionSimple(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}

class Cylinder extends SolidOfRevolutionSimple {
    private double height;

    Cylinder(double height, double radius) {
        super(radius);
        this.height = height;
    }

    @Override
    public double getVolume() {
        return Math.PI * radius * radius * height;
    }
}

class Ball extends SolidOfRevolutionSimple {

    Ball(double radius) {
        super(radius);
    }

    @Override
    public double getVolume() {
        return (4.0/3.0)* Math.PI * radius*radius*radius;
    }
}

class SolidOfRevolutionFreeForm implements Shape {
    Function<Double, Double> func;
    double a, b;
    double STEP_SIZE = 0.0001;

    SolidOfRevolutionFreeForm(Function<Double, Double> func, double a, double b) {
        this.func = func;
        this.a = a;
        this.b = b;
    }

    @Override
    public double getVolume() {
        double integral = 0;
        for(double i = a; i < b; i+=STEP_SIZE) {
            integral += func.apply((i+i+STEP_SIZE)/2.0)*func.apply((i+i+STEP_SIZE)/2.0)*STEP_SIZE;
        }
        return Math.PI*integral;
    }
}

class Pyramid implements Shape {
    private double s;
    private double h;

    Pyramid (double s, double h) {
        this.s = s;
        this. h = h;
    }

    @Override
    public double getVolume() {
        return (1.0/3.0)*h*s;
    }
}

class Box implements Shape {
    private double volume;
    private double freeSpace;
    private ArrayList<Shape> shapes = new ArrayList<Shape>();

    Box(double volume) {
        this.volume = volume;
        freeSpace = volume;
    }

    public void sortItems() {
        Collections.sort(shapes); //запомнить!
    }

    boolean add(Shape shape) {
        if (freeSpace >= shape.getVolume()) {
            freeSpace -= shape.getVolume();
            shapes.add(shape);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String res = "The box contains: ";
        for(Shape shape : shapes) {
            res +=  "\n" + shape.getClass().getSimpleName() + " with a volume " + shape.getVolume();
        }
        return res;
    }

    @Override
    public double getVolume() {
        return volume;
    }
}