package RayTracer18;


import javafx.scene.paint.Color;

public class Ray {
    public Vector3 origin;
    public Vector3 direction;
    public Scene scene;
    public double t;


    public Color color;

    public Ray(Vector3 origin, Vector3 direction){
        this.t = 1;
        this.direction = direction.normalize();

        this.origin = origin;

    }

    public void shoot(){
        for(Object3D ob : this.scene.getObjects()){
            System.out.println(ob);
        }
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
