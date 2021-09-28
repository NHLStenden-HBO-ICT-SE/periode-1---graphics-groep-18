package RayTracer18.Primitives;

import RayTracer18.Ray;
import RayTracer18.Vector3;

public class Plane extends Object3D {
    public Vector3 normal;



    public Plane(Vector3 normal) {

        super(new Vector3(0, 0, 5));
        this.normal = normal;
    }

    private Vector3 calculateIntersection(Ray r, Vector3 planeNormal, Vector3 planePoint) {
        Vector3 diff = Vector3.sub(r.origin, planePoint);
        double prod1 = Vector3.dot(diff, planeNormal);
        double prod2 = Vector3.dot(r.direction, planeNormal);
        double prod3 = prod1 / prod2;
        return r.origin.subtract(r.direction.multiplyScalar(prod3));
    }

//    public Vector3 normalize(Vector3 a, Vector3 b, Vector3 c){
//        double ax = a.x - c.x;
//        double ay = a.y - c.y;
//        double az = a.z - c.z;
//        double bx = b.x - c.x;
//        double by = b.y - c.y;
//        double bz = b.z - c.z;
//        return new Vector3(ay * bz - az * by, az * bx - ax * bz, ax * by
//                - ay * bx);
//    }

    public Vector3 getNormalAt(Vector3 point) {
        return normal;
    }
}
