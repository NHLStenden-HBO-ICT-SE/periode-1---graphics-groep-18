package RayTracer18.Primitives;

import RayTracer18.Ray;
import RayTracer18.Vector2;
import RayTracer18.Vector3;

public class Sphere extends Object3D{
    double radius;

    public Sphere(Vector3 pos, double radius) {
        super(pos);
        this.radius = radius;

    }


    @Override
    public Vector3 calculateIntersection(Ray ray) {
        Vector3 oc = Vector3.sub(ray.origin , this.position);
        double b = Vector3.dot( oc, ray.direction );
        double c = Vector3.dot( oc, oc ) - this.radius*this.radius;
        double h = b*b - c;
        if( h<0.0 ) {

            return null; // no intersection
        }
        System.out.println("inter");
        h = Math.sqrt( h );
        System.out.println("VALUES: " + (-b-h));
        System.out.println("NR 2 " + (-b+h));
        return new Vector3( -b-h, -b+h ,0);
    }

    @Override
    public Vector3 getNormalAt(Vector3 point) {
        return Vector3.sub(point, this.position).normalize();
    }
}
