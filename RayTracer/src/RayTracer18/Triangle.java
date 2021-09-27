package RayTracer18;

import static RayTracer18.Vector3.*;
import static java.lang.Float.NEGATIVE_INFINITY;
import static java.lang.Math.*;
import static java.lang.System.currentTimeMillis;
public class Triangle {


    public Vector3 p1, p2, p3;

    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }





    float intersect(Ray r) {

        Vector3 v0 = this.p1;
        Vector3 v1 = this.p2;
        Vector3 v2 = this.p3;

        Vector3 v0v1 = sub(v1, v0);
        Vector3 v0v2 = sub(v2,v0);
        Vector3 pvec = r.direction.cross(v0v2);
        double det = v0v1.dot(pvec);

        if (det < 0.000001)
            return NEGATIVE_INFINITY;

        float invDet = (float) (1.0 / det);
        Vector3 tvec = sub(r.origin,v0);
        double u = tvec.dot(pvec) * invDet;

        if (u < 0 || u > 1)
            return NEGATIVE_INFINITY;

        Vector3 qvec = tvec.cross(v0v1);
        double v = r.direction.dot(qvec) * invDet;

        if (v < 0 || u + v > 1)
            return NEGATIVE_INFINITY;

        return (float) (v0v2.dot(qvec) * invDet);
    }
}
