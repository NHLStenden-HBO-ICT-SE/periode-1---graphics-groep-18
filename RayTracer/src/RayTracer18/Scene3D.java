package RayTracer18;

import RayTracer18.Light.Light;
import RayTracer18.Primitives.Object3D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Scene3D {

    private ArrayList<Object3D> objects;
    private ArrayList<Light> lights;
    public Camera camera;
    public Color voidColor;




    public Scene3D(){
        this.objects = new ArrayList<Object3D>();
        this.camera = new Camera(0.3, this);
        this.lights = new ArrayList<Light>();
        //Color if a pixel hits nothing
        this.voidColor = Color.BLACK;

    }

    public void add(Object3D ob){
        this.objects.add(ob);
    }

    public void add(Light light){
        lights.add(light);
    }

    public ArrayList<Light> getLights(){
        //Made this function for if we want to be able to hide certain objects(return only objects with object.visible = true for example)
        return this.lights;
    }
    public ArrayList<Object3D> getObjects(){
        //Made this function for if we want to be able to hide certain objects(return only objects with object.visible = true for example)
        return objects;
    }


}
