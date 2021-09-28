package RayTracer18;


import RayTracer18.Light.Light;
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
    public RayType type;


    public Object3D lastobject;

    public Ray(Vector3 origin, Vector3 direction, Scene3D scene){
        this.t = 1;
        this.direction = direction.normalize();
        this.scene = scene;
        this.origin = origin;
        this.hits = new ArrayList<Vector3>();
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





    public Color shoot(){
        //Loop through all objects in the scene to see if it intersects with the current ray
        for(Object3D ob : this.scene.getObjects()){
                Vector3 hit = ob.calculateIntersection(this);
                if(hit != null){
                    //Add it to a list of hitted objects
                    hits.add(hit);
                    lastobject = ob;
                    if(this.type == RayType.NORMAL){
                        for (Light light: this.scene.getLights()) {
                            Vector3 dir = Vector3.sub(light.position, hit).normalize();
                            Ray shadowRay = new Ray(hit, dir, this.scene);
                            shadowRay.setType(RayType.SHADOW);
                            Color res = shadowRay.shoot();
                            if(res == null){
                                Vector3 normal = ob.getNormalAt(hit);
                                Vector3 lightDir = Vector3.sub(hit, this.origin).normalize();
                                double prod = normal.dot( lightDir);
                                prod = Math.abs(prod);
                                Color c = ob.getMaterial().getColor().interpolate(Color.BLACK, (1-prod)* 1/light.intensity);
                                return c;

                            }
                            return res;
                        }
                    }
                    if(this.type == RayType.SHADOW){

                        return Color.ORANGE;
                    }


                    return ob.getMaterial().getColor();
            }
        }
        return null;
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
