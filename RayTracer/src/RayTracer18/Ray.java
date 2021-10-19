package RayTracer18;


import RayTracer18.Light.Light;
import RayTracer18.Primitives.Object3D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Ray {




    public Vector3 origin;
    public Vector3 direction;
    public Scene3D scene;
    public double t;
    public int bounces;
    public static int MAX_BOUNCES = 10;

    public double distance = 0;

    public Vector2 targetPixels;


    public Color currentColor;

    public Object3D from = null;

    public Ray(Vector3 origin, Vector3 direction, Scene3D scene){
        this.t = 1;
        this.direction = direction.normalize();
        this.scene = scene;
        this.origin = origin;
        this.bounces = 0;


    }


    public void incrementBounces(){
        this.bounces +=1;

    }

    public Vector3 pointAt(double t) {
        return this.origin.add(this.direction.multiply((float) t));
    }

    public Vector3 getOrigin(){
        return this.origin.clone();
    }
    public Vector3 getDirection(){
        return this.direction.clone();
    }



    public Vector3 hasBlockade(Light l){
        for (Object3D ob: this.scene.getObjects()) {
            Vector3 crossPoint = ob.calculateIntersection(this);
            if(crossPoint != null){
                double distanceToPoint = this.getOrigin().distanceTo(crossPoint);
                double distanceToLight = this.getOrigin().distanceTo(l.position);

                if( distanceToPoint < distanceToLight ){

                    return crossPoint;
                }

            }
        }
        return null;

    }





    public RayHit shoot(){
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

            return new RayHit(scene.voidColor, 1000);

        }

        this.distance = hitPoint.distanceTo(this.getOrigin());

        Vector3 normal = hitObject.getNormalAt(hitPoint);

        double reflectionAmount = hitObject.getMaterial().getReflection();
        if(reflectionAmount > 0 && this.bounces < this.MAX_BOUNCES){

            Vector3 reflectionEquation = normal.clone().multiplyScalar(Vector3.dot(this.getDirection(), normal.clone())*2);
            Vector3 direction = Vector3.sub(this.getDirection(), reflectionEquation);
            Vector3 startingPoint = hitPoint.add(direction.clone().multiplyScalar(Renderer.EPSILON));

            Ray reflectionRay = new Ray(startingPoint, direction, scene);
            reflectionRay.incrementBounces();
            reflectionRay.from = hitObject;

            RayHit refHit = reflectionRay.shoot();
            currentColor = hitObject.getMaterial().getColor().interpolate(refHit.color, reflectionAmount);
            refHit.color = currentColor;
            return refHit;

        }


        ArrayList<Light> reachAbleLights = new ArrayList<Light>();

        for (Light light:scene.getLights()) {

            Vector3 rayDir = Vector3.sub(light.position, hitPoint).normalize();
            Vector3 startingPoint = hitPoint.clone().add(rayDir.clone().multiplyScalar(0.01));

            Ray shadowRay = new Ray(startingPoint,rayDir , scene);
            shadowRay.bounces = bounces;
            Vector3 blockadePosition = shadowRay.hasBlockade(light);
            //If not blocked, add it to reachable
            if(blockadePosition == null){
                reachAbleLights.add(light);
            }
            if(this.bounces > 0 && blockadePosition != null){
                Object3D x = this.from;
            }

        }




        if(reachAbleLights.size() == 0){
            //No lights absolute shadow
            return new RayHit(Color.BLACK, this.distance);
        }

        Color cur = hitObject.getMaterial().getColor();


        int totalWeight = 0;
        for(Light l: reachAbleLights){
            totalWeight += l.intensity;
        }
        for (Light l : reachAbleLights){
           cur = cur.interpolate(l.color, 1/Math.pow(hitPoint.distanceTo(l.position) , 2) * l.intensity);
        }


        Vector3 lightDir = Vector3.sub(hitPoint, this.origin).normalize();
        double prod = Vector3.dot(lightDir, normal);
        prod += 1;
        prod *=0.5;
        Color returnColor = cur.interpolate(Color.BLACK, prod);
        return new RayHit(returnColor, this.distance);

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
