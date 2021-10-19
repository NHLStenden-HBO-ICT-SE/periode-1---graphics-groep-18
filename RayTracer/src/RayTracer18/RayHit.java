package RayTracer18;

import RayTracer18.Primitives.Object3D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

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

    public void setDistance(double d){
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
