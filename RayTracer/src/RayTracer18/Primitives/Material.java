package RayTracer18.Primitives;

import javafx.scene.paint.Color;

public class Material {
    public Color color;


    public Material(Color c){
        this.color = c;
    }


    public Color getColor(){
        return color;
    }

}
