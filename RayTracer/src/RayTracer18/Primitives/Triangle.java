package RayTracer18.Primitives;

import RayTracer18.Ray;
import RayTracer18.Vector2;
import RayTracer18.Vector3;
import javafx.scene.paint.Color;

import static RayTracer18.Vector3.addVectors;
import static RayTracer18.Vector3.sub;

public class Triangle extends Object3D {


    public Vector3 p1, p2, p3;


    public boolean hasVertexNormals = false;

    public Triangle(Vector3 pos, Vector3 p1, Vector3 p2, Vector3 p3) {

        super(pos);
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.name = "Triangle";
    }

    public static Vector3 calculateCenter(Vector3 vec1, Vector3 vec2, Vector3 vec3) {
        double newX = (vec1.getX() + vec2.getX() + vec3.getX()) / 3;
        double newY = (vec1.getY() + vec2.getY() + vec3.getY()) / 3;
        double newZ = (vec1.getZ() + vec2.getZ() + vec3.getZ()) / 3;
        Vector3 centerVec = new Vector3(newX, newY, newZ);
        return centerVec;
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
        Vector3 v0v2 = sub(v2, v0);

        Vector3 normal = Vector3.cross(v0v2, v0v1).normalize();

        Vector3 pvec = r.getDirection().cross(v0v2);
        double det = v0v1.dot(pvec);


        double invDet = (1.0 / det);
        Vector3 tvec = sub(r.getOrigin(), v0);
        double u = tvec.dot(pvec) * invDet;

        if (u < 0 || u > 1)
            return null;
        //Uncomment for backface culling > Rays will pass trough the backside of triangles
//        if (det < Renderer.)
//            return null;
        Vector3 qvec = tvec.cross(v0v1);
        double v = r.getDirection().dot(qvec) * invDet;

        if (v < 0 || u + v > 1)
            return null;

        double distance = (v0v2.dot(qvec) * invDet);
        //Return if the ray was shot backwards, the opposite of direction
        if (distance < 0) {
            return null;
        }
        Vector3 endpoint = new Vector3(r.getOrigin());
        Vector3 towards = r.getDirection().multiplyScalar(distance);
        endpoint.add(towards);
        endpoint.setUv(u, v);
        return endpoint;
    }

    @Override
    public Color getColorAt(Vector3 cords) {

        Vector2 uv = cords.getUv();
        if (getMaterial().textured) {
            double u = this.p1.textureCords.x + uv.x * (this.p2.textureCords.x - this.p1.textureCords.x) + uv.y * (this.p3.textureCords.x - this.p1.textureCords.x);
            double v = this.p1.textureCords.y + uv.x * (this.p2.textureCords.y - this.p1.textureCords.y) + uv.y * (this.p3.textureCords.y - this.p1.textureCords.y);

            //Some calculations resulted in -0.0000000005

            u = Math.max(0, u);
            v = Math.max(0, v);
            //u = 1-u;

            //return new Color(cords.u, cords.v, Math.max(0,1-cords.v-cords.u), 1);
            return getMaterial().getColorByUv(u, v);
        }

        return getMaterial().color;
    }

    @Override
    public Vector3 getNormalAt(Vector3 point) {
        //Point was used for interpolated normals but removed later on
        Vector3 a = Vector3.sub(p2, p1);
        Vector3 b = Vector3.sub(p3, p1);
        Vector3 res = a.cross(b).normalize();
        return res;
        //Has vertex normals
//        Vector3 p1n = this.p1.getNormal();
//        Vector3 p2n = this.p2.getNormal();
//        Vector3 p3n = this.p3.getNormal();
//        double u  = point.uv.x;
//        double v = point.uv.y;
//        double w = 1- u - v;
//        return Vector3.addVectors(p1n.multiplyScalar(v), p2n.multiplyScalar(w)).add(p3n.multiplyScalar(u));
    }

    public void rotateZ(double angle) {
        Vector3 dVec1 = sub(this.p1, this.position);
        dVec1.rotateZAxis(angle);
        Vector3 newP1 = addVectors(dVec1, this.position);

        Vector3 dVec2 = sub(this.p2, this.position);
        dVec1.rotateZAxis(angle);
        Vector3 newP2 = addVectors(dVec2, this.position);

        Vector3 dVec3 = sub(this.p3, this.position);
        dVec3.rotateZAxis(angle);
        Vector3 newP3 = addVectors(dVec3, this.position);

        this.p1 = newP1;
        this.p2 = newP2;
        this.p3 = newP3;
    }

    public void rotateY(double angle) {
        Vector3 dVec1 = sub(this.p1, this.position);
        dVec1.rotateYAxis(angle);
        Vector3 newP1 = addVectors(dVec1, this.position);

        Vector3 dVec2 = sub(this.p2, this.position);
        dVec2.rotateYAxis(angle);
        Vector3 newP2 = addVectors(dVec2, this.position);

        Vector3 dVec3 = sub(this.p3, this.position);
        dVec3.rotateYAxis(angle);
        Vector3 newP3 = addVectors(dVec3, this.position);

        this.p1.copy(newP1);
        this.p2.copy(newP2);
        this.p3.copy(newP3);
    }

    public void rotateX(double angle) {
        Vector3 dVec1 = sub(this.p1, this.position);
        dVec1.rotateXAxis(angle);
        Vector3 newP1 = addVectors(dVec1, this.position);

        Vector3 dVec2 = sub(this.p2, this.position);
        dVec2.rotateXAxis(angle);
        Vector3 newP2 = addVectors(dVec2, this.position);

        Vector3 dVec3 = sub(this.p3, this.position);
        dVec3.rotateXAxis(angle);
        Vector3 newP3 = addVectors(dVec3, this.position);

        this.p1.copy(newP1);
        this.p2.copy(newP2);
        this.p3.copy(newP3);
    }

    public void rotateXAround(Object3D target, double angle) {
        Vector3 dVec1 = sub(this.p1, target.position);
        dVec1.rotateXAxis(angle);
        Vector3 newP1 = addVectors(dVec1, target.position);

        Vector3 dVec2 = sub(this.p2, target.position);
        dVec2.rotateXAxis(angle);
        Vector3 newP2 = addVectors(dVec2, target.position);

        Vector3 dVec3 = sub(this.p3, target.position);
        dVec3.rotateXAxis(angle);
        Vector3 newP3 = addVectors(dVec3, target.position);

        this.p1.copy(newP1);
        this.p2.copy(newP2);
        this.p3.copy(newP3);
    }

    public void rotateYAround(Object3D target, double angle) {
        Vector3 dVec1 = sub(this.p1, target.position);
        dVec1.rotateYAxis(angle);
        Vector3 newP1 = addVectors(dVec1, target.position);

        Vector3 dVec2 = sub(this.p2, target.position);
        dVec2.rotateYAxis(angle);
        Vector3 newP2 = addVectors(dVec2, target.position);

        Vector3 dVec3 = sub(this.p3, target.position);
        dVec3.rotateYAxis(angle);
        Vector3 newP3 = addVectors(dVec3, target.position);

        this.p1.copy(newP1);
        this.p2.copy(newP2);
        this.p3.copy(newP3);
    }

    public void rotateZAround(Object3D target, double angle) {
        Vector3 dVec1 = sub(this.p1, target.position);
        dVec1.rotateZAxis(angle);
        Vector3 newP1 = addVectors(dVec1, target.position);

        Vector3 dVec2 = sub(this.p2, target.position);
        dVec2.rotateZAxis(angle);
        Vector3 newP2 = addVectors(dVec2, target.position);

        Vector3 dVec3 = sub(this.p3, target.position);
        dVec3.rotateZAxis(angle);
        Vector3 newP3 = addVectors(dVec3, target.position);

        this.p1.copy(newP1);
        this.p2.copy(newP2);
        this.p3.copy(newP3);
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
