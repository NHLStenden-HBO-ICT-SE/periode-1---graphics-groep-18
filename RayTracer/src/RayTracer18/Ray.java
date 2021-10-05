package RayTracer18;


import RayTracer18.Light.Light;
import RayTracer18.Primitives.Object3D;
import RayTracer18.Primitives.Plane;
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
            Vector3 crossPoint = ob.calculateIntersection(this);
            if(crossPoint != null){

                double distanceToPoint = this.getOrigin().distanceTo(crossPoint);
                double distanceToLight = this.getOrigin().distanceTo(l.position);
                if( distanceToPoint < distanceToLight ){
                    return true;
                }

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
            double distance = Vector3.sub(scene.camera.getPosition(),crossPoint).getLength();
            if(distance < smallestDistance){
                hitObject = ob;
                smallestDistance = distance;
                hitPoint = crossPoint;
            }
        }
        if(hitObject == null){
            return scene.voidColor;
        }

        Vector3 normal = hitObject.getNormalAt(hitPoint);

        ArrayList<Light> reachAbleLights = new ArrayList<Light>();
        for (Light light:scene.getLights()) {

            Vector3 startingPoint = hitPoint.clone().add(normal.clone().multiplyScalar(0.01));
            Vector3 rayDir = Vector3.sub(light.position, startingPoint).normalize();
            Ray shadowRay = new Ray(startingPoint,rayDir , scene);

            boolean hasBlockade = shadowRay.hasBlockade(light);
            if(!hasBlockade){
                reachAbleLights.add(light);
            }

        }
        if(reachAbleLights.size() == 0){
            //No lights absolute shadow
            return Color.BLACK;
        }

        Color cur = hitObject.getMaterial().getColor();
        for (Light l : reachAbleLights){
            double strength = l.intensity / l.position.distanceTo(hitPoint);
            System.out.println(strength);

            cur = cur.interpolate(l.color,  Math.min((1/Math.pow(hitPoint.distanceTo(l.position) , 2))* l.intensity, 1 ));

        }

        Vector3 lightDir = Vector3.sub(hitPoint, this.origin).normalize();
        double prod = Vector3.dot(lightDir, normal);


        prod += 1;
        prod *=0.5;
        return cur.interpolate(Color.BLACK, prod);

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
