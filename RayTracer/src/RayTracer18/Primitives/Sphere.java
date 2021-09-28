package RayTracer18.Primitives;

import RayTracer18.Ray;
import RayTracer18.Vector2;
import RayTracer18.Vector3;

import java.util.Random;

public class Sphere extends Object3D{
    double radius;

    public Sphere(Vector3 pos, double radius) {
        super(pos);
        this.radius = radius;

    }


    @Override
    public Vector3 calculateIntersection(Ray ray) {
        Vector3 oc = Vector3.sub(ray.getOrigin() , this.position);
        double a = Vector3.dot(ray.getDirection(), ray.getDirection());
        double b = 2.0 * Vector3.dot(oc, ray.getDirection());
        double c = Vector3.dot(oc,oc) - radius*radius;
        double discriminant = b*b - 4*a*c;
        if(discriminant < 0){
            return null;
        }
        else{
            return ray.getDirection().multiplyScalar((-b - Math.sqrt(discriminant)) / (2.0*a));
        }
    }


    @Override
    public Vector3 getNormalAt(Vector3 point) {
        return Vector3.sub(point, this.position).normalize();
    }
}
