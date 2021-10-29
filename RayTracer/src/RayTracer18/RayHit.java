package RayTracer18;

import javafx.scene.paint.Color;

public class RayHit {

    public Color color;
    public Vector2 targetPixels;
    private double distance;

    public RayHit(Color c, double distance) {
        this.distance = distance;
        this.color = c;
    }

    public Color getColor() {
        return color;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double d) {
        this.distance = d;
    }

    @Override
    public String toString() {
        return "RayHit{" +
                "distance=" + distance +
                ", color=" + color +
                '}';
    }
}
