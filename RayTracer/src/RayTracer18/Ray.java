package RayTracer18;


import RayTracer18.Primitives.Triangle;
import javafx.scene.paint.Color;

public class Ray {
    public Vector3 origin;
    public Vector3 direction;
    public Scene3D scene;
    public double t;
    public Vector3 hitpoint;

    public Color color;

    public Ray(Vector3 origin, Vector3 direction, Scene3D scene){
        this.t = 1;
        this.direction = direction.normalize();
        this.scene = scene;
        this.origin = origin;

    }


    public Vector3 getOrigin(){
        return this.origin.clone();
    }
    public Vector3 getDirection(){
        return this.direction.clone();
    }

    public Color shoot(){
        for(Object3D ob : this.scene.getObjects()){
            if(ob instanceof Triangle){

                Triangle c = ((Triangle)ob);
                Vector3 hit = c.calculateIntersection(this);
                if(hit != null){
                    hitpoint = hit;



                    return Color.GREEN;
                }


            }
        }
        return Color.BLACK;
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
