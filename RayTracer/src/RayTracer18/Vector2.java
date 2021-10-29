package RayTracer18;

public class Vector2 {

    public double x;
    public double y;


    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {

    }

    public static Vector2 sub(Vector2 v1, Vector2 v2) {
        return new Vector2(
                v1.x - v2.x,
                v2.y - v2.y
        );
    }

    public Vector2 clone() {
        return new Vector2(this.x, this.y);
    }

    public Vector2 add(Vector2 v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector2 multiplyScalar(double m) {
        this.x *= m;
        this.y *= m;
        return this;
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
