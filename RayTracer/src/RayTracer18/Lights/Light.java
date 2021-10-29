package RayTracer18.Lights;

import RayTracer18.Vector3;
import javafx.scene.paint.Color;

import java.util.UUID;

public abstract class Light {
    public double intensity;
    public Color color;
    public Vector3 position;
    public String name;
    public String id;

    public Light(Vector3 position, double intensity, Color color) {
        this.intensity = intensity;
        this.color = color;
        this.position = position;
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {
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
