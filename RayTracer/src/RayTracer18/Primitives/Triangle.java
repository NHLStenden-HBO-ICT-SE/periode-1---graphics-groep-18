package RayTracer18.Primitives;

import RayTracer18.Ray;
import RayTracer18.Vector3;

import static RayTracer18.Vector3.*;

public class Triangle extends Object3D{

    private Vector3 normal;
    public Vector3 p1, p2, p3;

    public Triangle(Vector3 pos, Vector3 p1, Vector3 p2, Vector3 p3){

        super(pos);

        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
<<<<<<< HEAD
<<<<<<< HEAD
        calculateNormal();
=======
>>>>>>> parent of fb08be8 (Merge branch 'main' of https://github.com/NHLStenden-HBO-ICT-SE/periode-1---graphics-groep-18 into main)
=======
>>>>>>> parent of fb08be8 (Merge branch 'main' of https://github.com/NHLStenden-HBO-ICT-SE/periode-1---graphics-groep-18 into main)
    }

    //Möller–Trumbore intersection algorithm.

    /**
     * @param r Where to check intersections with
     * @return Point where it hit.
     */
    @Override
    public Vector3 calculateIntersection(Ray r) {

        Vector3 v0 = this.p1;
        Vector3 v1 = this.p2;
        Vector3 v2 = this.p3;



        Vector3 v0v1 = sub(v1, v0);
        Vector3 v0v2 = sub(v2,v0);

        Vector3 normal = Vector3.cross(v0v2, v0v1).normalize();



        Vector3 oc = Vector3.sub(r.getOrigin() , this.position);
        Vector3 pvec = r.getDirection().cross(v0v2);
        double det = v0v1.dot(pvec);
        if (det < 0.000001)
            return null;

        double invDet = (1.0 / det);
        Vector3 tvec = sub(r.getOrigin(),v0);
        double u = tvec.dot(pvec) * invDet;

        if (u < 0 || u > 1)
            return null;

        Vector3 qvec = tvec.cross(v0v1);
        double v = r.getDirection().dot(qvec) * invDet;

        if (v < 0 || u + v > 1)
            return null;

        double distance =  (v0v2.dot(qvec) * invDet);
        Vector3 endpoint = new Vector3(r.getOrigin());
        Vector3 towards = r.getDirection().multiplyScalar(distance);
        endpoint.add(towards);



        return endpoint;
    }

    @Override
    public Vector3 getNormalAt(Vector3 point) {
        return this.normal.clone();
    }
    private void calculateNormal(){
        Vector3 a = Vector3.sub(p2, p1);
        Vector3 b = Vector3.sub(p3, p1);
        Vector3 res = a.cross(b).normalize();
        this.normal = res;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                '}';
    }
}
