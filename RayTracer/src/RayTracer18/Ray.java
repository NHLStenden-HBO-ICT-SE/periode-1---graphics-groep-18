package RayTracer18;


import RayTracer18.Primitives.Object3D;
import RayTracer18.Primitives.Triangle;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Ray {
    public Vector3 origin;
    public Vector3 direction;
    public Scene3D scene;
    public double t;
    public ArrayList<Vector3> hits;
    public Color color;

    public Ray(Vector3 origin, Vector3 direction, Scene3D scene){
        this.t = 1;
        this.direction = direction.normalize();
        this.scene = scene;
        this.origin = origin;
        this.hits = new ArrayList<Vector3>();

    }


    public Vector3 getOrigin(){
        return this.origin.clone();
    }
    public Vector3 getDirection(){
        return this.direction.clone();
    }

    public Color shoot(){
        //Loop through all objects in the scene to see if it intersects with the current ray
        for(Object3D ob : this.scene.getObjects()){

                Vector3 hit = ob.calculateIntersection(this);
                if(hit != null){
                    //Add it to a list of hitted objects
                    hits.add(hit);

                    return ob.getMaterial().getColor();
            }
        }
        return scene.voidColor;
    }


    @Override
    public String toString() {
        return "Ray{" +
                "origin=" + origin +
                ", direction=" + direction +
                ", t=" + t +
                '}';
    }
}
