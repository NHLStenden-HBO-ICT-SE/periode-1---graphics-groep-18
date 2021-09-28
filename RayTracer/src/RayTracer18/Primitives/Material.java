package RayTracer18.Primitives;

import javafx.scene.paint.Color;

public class Material {
    public Color color;

    public Material(Color c){
        this.color = c;
    }

    //Use this later with parameters hitPoint to look up the pixel in the maps if needed
    public Color getColor(){
        return color;
    }

}
