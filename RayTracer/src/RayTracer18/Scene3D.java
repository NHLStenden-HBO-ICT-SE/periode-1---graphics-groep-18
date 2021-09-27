package RayTracer18;

import java.util.ArrayList;

public class Scene3D {

    private ArrayList<Object3D> objects;
    public Camera camera;

    public boolean testhit = false;


    public Scene3D(){
        this.objects = new ArrayList<Object3D>();
        this.camera = new Camera(0.5, this);


    }

    public void add(Object3D ob){
        this.objects.add(ob);
    }

    public ArrayList<Object3D> getObjects(){
        //Made this function for if we want to be able to hide certain objects(return only objects with object.visible = true for example)
        return objects;
    }


}
