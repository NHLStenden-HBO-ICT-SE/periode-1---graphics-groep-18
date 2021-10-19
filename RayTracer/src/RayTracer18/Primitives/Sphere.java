package RayTracer18.Primitives;

import RayTracer18.Ray;
import RayTracer18.Vector2;
import RayTracer18.Vector3;
import javafx.scene.paint.Color;

import java.util.Random;

public class Sphere extends Object3D{
    private double radius;

    public Sphere(Vector3 pos, double radius) {
        super(pos);
        this.radius = radius;
        this.name = "Sphere";
    }

    public void setScale(double scale){
        this.radius = this.radius * scale;
    }

    @Override
    public Vector3 calculateIntersection(Ray ray) {
        Vector3 oc = Vector3.sub(ray.getOrigin() , this.position);
        double a = Vector3.dot(ray.getDirection(), ray.getDirection());
        double b = 2.0 * Vector3.dot(oc, ray.getDirection());
        double c = Vector3.dot(oc,oc) - radius*radius;
        double discriminant = b*b - 4*a*c;

        double t1 = -b - Math.sqrt(discriminant) / (2.0*a);
        double t2 = -b + Math.sqrt(discriminant) / (2.0*a);


        if(discriminant < 0){
            return null;
        }
        if(Math.min(t1,t2) < 0){
            return null;
        }

        return ray.getDirection().multiplyScalar((-b - Math.sqrt(discriminant)) / (2.0*a));
    }

    @Override
    public Color getColorAt(Vector3 cords) {
        return getMaterial().color;
    }

    @Override
    public Vector3 getNormalAt(Vector3 point) {
        return Vector3.sub(point, this.position).normalize();
    }
}
