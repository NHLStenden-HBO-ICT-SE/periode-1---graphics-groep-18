package RayTracer18;


import RayTracer18.Light.Light;
import RayTracer18.Primitives.Object3D;
import RayTracer18.Primitives.Sphere;
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
    public RayType type;



    public Ray(Vector3 origin, Vector3 direction, Scene3D scene){
        this.t = 1;
        this.direction = direction.normalize();
        this.scene = scene;
        this.origin = origin;
        this.type = RayType.NORMAL;

    }
    public void setType(RayType r){
        this.type = r;
    }


    public Vector3 getOrigin(){
        return this.origin.clone();
    }
    public Vector3 getDirection(){
        return this.direction.clone();
    }



    public boolean hasBlockade(Light l){
        for (Object3D ob: this.scene.getObjects()) {
            this.origin.add(this.getDirection().multiplyScalar(0.001));
            Vector3 crossPoint = ob.calculateIntersection(this);
            if(crossPoint != null){
                if(this.getOrigin().distanceTo(crossPoint) > this.getOrigin().distanceTo(l.position)){
                    continue;
                }

                    return true;

            }
        }
        return false;

    }





    public Color shoot(){
        //Loop through all objects in the scene to see if it intersects with the current ray
        Object3D hitObject = null;
        Vector3 hitPoint = new Vector3();
        double smallestDistance = Double.POSITIVE_INFINITY;


        for (Object3D ob: this.scene.getObjects()) {
            Vector3 crossPoint = ob.calculateIntersection(this);
            if(crossPoint == null){
                continue;
            }
            double distance = Vector3.sub(new Vector3(),crossPoint).getLength();
            if(distance < smallestDistance){
                hitObject = ob;
                smallestDistance = distance;
                hitPoint = crossPoint;
            }
        }
        if(hitObject == null){
            return scene.voidColor;
        }

        for (Light light:scene.getLights()) {

            Ray shadowRay = new Ray(hitPoint, Vector3.sub(light.position, hitPoint).normalize(), scene);
            boolean hasBlockade = shadowRay.hasBlockade(light);
            if(hasBlockade){
                return hitObject.getMaterial().getColor().interpolate(Color.BLACK, 0.99);
            }

        }


        Vector3 normal = hitObject.getNormalAt(hitPoint);

        Vector3 lightDir = Vector3.sub(hitPoint, this.origin).normalize();
        double prod = normal.dot( lightDir);
        prod = Math.abs(prod);
        Color c = hitObject.getMaterial().getColor().interpolate(Color.BLACK, (1-prod));
        return c;

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
