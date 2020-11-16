import java.util.Random;

public class Vector {

    private double x, y, z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector() {
        this(0,0,0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double abs() {
        return Math.sqrt(x*x+y*y+z*z);
    }


    public double dot(Vector v) {
        return v.getX()*x+v.getY()*y+v.getZ()*z;
    }

    public Vector cross(Vector v) {
        double resX,resY,resZ;
        resX = y*v.getZ() - z*v.getY();
        resY = z*v.getX() - x*v.getZ();
        resZ = x*v.getY() - y*v.getX();
        return new Vector(resX, resY, resZ);
    }

    public double angle(Vector v) {
        double res = this.dot(v)/this.abs()*v.abs();
        return Math.acos(res);
    }

    public Vector sum(Vector v) {
        return new Vector(x + v.getX(), y+v.getY(), z+v.getZ());
    }

    public Vector dif(Vector v) {
        return new Vector(x - v.getX(), y-v.getY(), z-v.getZ());
    }

    public static Vector[] generateRandomVectors(int n) {
        Vector[] res = new Vector[n];
        for(int i = 0; i < n; i++)
            res[i] = new Vector( Math.random(),  Math.random(),  Math.random());
        return res;
    }


    @Override
    public String toString() { //запомнить!
        return this.getX() + ", " + this.getY()+ ", " + this.getZ();
    }
}
