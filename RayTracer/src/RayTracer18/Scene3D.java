package RayTracer18;

import RayTracer18.Lights.Light;
import RayTracer18.Primitives.ObjLoader;
import RayTracer18.Primitives.Object3D;
import RayTracer18.Primitives.Triangle;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Scene3D {

    private ArrayList<Object3D> objects;
    private ArrayList<Light> lights;
    private ArrayList<Object3D> hierarchyObjects;
    public Camera camera;
    public Color voidColor;

    public Scene3D() {
        this.objects = new ArrayList<Object3D>();
        this.hierarchyObjects = new ArrayList<Object3D>();
        this.camera = new Camera(0.32, this);
        this.lights = new ArrayList<Light>();
        //Color if a pixel hits nothing
        this.voidColor = Color.PINK;

    }

    public void add(Object3D ob) {
        if (ob instanceof ObjLoader) {
            try {
                Triangle[] triangles = ((ObjLoader) ob).parseFile();
                for (Triangle triangle : triangles) {
                    triangle.position = ob.position;
                    triangle.applyMaterial(ob.getMaterial());
                }
                objects.addAll(List.of(triangles));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            objects.add(ob);
        }
        hierarchyObjects.add(ob);
    }


    public void add(Light light) {
        lights.add(light);
    }

    public ArrayList<Light> getLights() {
        //Made this function for if we want to be able to hide certain objects(return only objects with object.visible = true for example)
        return this.lights;
    }

    public ArrayList<Object3D> getObjects() {
        //Made this function for if we want to be able to hide certain objects(return only objects with object.visible = true for example)
        return objects;
    }

    public ArrayList<Object3D> getHiarcyObjects() {
        //Made this function for if we want to be able to hide certain objects(return only objects with object.visible = true for example)
        return hierarchyObjects;
    }


    public Object3D getObjectById(String id) {
        for (Object3D ob : this.hierarchyObjects) {
            if (ob.id.equalsIgnoreCase(id)) {
                return ob;
            }
        }
        return null;
    }

    public Light getLightById(String id) {
        for (Light l : this.lights) {
            if (l.id.equalsIgnoreCase(id)) {
                return l;
            }
        }
        return null;
    }
}
