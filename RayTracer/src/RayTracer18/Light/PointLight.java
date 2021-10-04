package RayTracer18.Light;

import RayTracer18.Vector3;
import javafx.scene.paint.Color;

public class PointLight extends Light{

    public PointLight(Vector3 position, double intensity, Color color) {
        super(position, intensity, color);
        this.name = "Pointlight";
    }
}
