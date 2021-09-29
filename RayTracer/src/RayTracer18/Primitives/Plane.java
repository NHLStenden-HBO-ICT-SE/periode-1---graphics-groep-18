package RayTracer18.Primitives;

import RayTracer18.Ray;
import RayTracer18.Vector3;

public class Plane extends Object3D{

    private Triangle t1;
    private Triangle t2;


    public Plane(Vector3 pos, Vector3 corner1, Vector3 corner2, Vector3 corner3, Vector3 corner4) {
        super(pos);
        this.t1 = new Triangle(
                pos,
                corner1, corner3, corner4
        );
        this.t2 = new Triangle(
                pos,
                corner3, corner2, corner4
        );
    }

    @Override
    public Vector3 calculateIntersection(Ray ray) {
        Vector3 intersection = t1.calculateIntersection(ray);
        if(intersection == null){
            return t2.calculateIntersection(ray);
        }
        return intersection;

    }

    @Override
    public Vector3 getNormalAt(Vector3 pos) {
        return t1.getNormalAt(pos);
    }
}
