package RayTracer18;

import java.util.ArrayList;

public class Scene {

    private ArrayList<Object3D> objects;
    public Camera camera;


    public Scene(){
        this.objects = new ArrayList<Object3D>();
        this.camera = new Camera();


    }

    public ArrayList<Object3D> getObjects(){
        //Made this function for if we want to be able to hide certain objects(return only objects with object.visible = true for example)
        return objects;
    }


}
