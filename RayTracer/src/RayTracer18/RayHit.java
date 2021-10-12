package RayTracer18;

import javafx.scene.paint.Color;

public class RayHit {

    private double distance;
    public Color color;
    public RayHit(Color c, double distance){
            this.distance = distance;
            this.color = c;
    }

    public Color getColor() {
        return color;
    }

    public double getDistance() {
        return distance;
    }
}
