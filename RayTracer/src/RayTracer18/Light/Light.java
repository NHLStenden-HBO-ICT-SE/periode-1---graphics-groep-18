package RayTracer18.Light;

import RayTracer18.Vector3;
import javafx.scene.paint.Color;

public abstract class Light {
    public double intensity;
    public Color color;
    public Vector3 position;
    public String name;


    public Light(Vector3 position, double intensity, Color color){
        this.intensity = intensity;
        this.color = color;
        this.position = position;

    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return "Light{" +
                "intensity=" + intensity +
                ", color=" + color +
                ", position=" + position +
                '}';
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
