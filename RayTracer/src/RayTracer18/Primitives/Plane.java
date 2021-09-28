package RayTracer18.Primitives;

import RayTracer18.Ray;
import RayTracer18.Vector3;

public class Plane extends Object3D {

    public Plane(double height) {

        super(new Vector3(0, height, 0));
    }

    public Vector3 calculateIntersection(Ray r) {
        double t = (-(r.getOrigin().getLength()-position.getLength()) / r.getDirection().getLength());
        if (t > 0 && Double.isFinite(t))
        {
            return r.getOrigin().add(r.getDirection().multiplyScalar(t));
        }

        return null;
    }

    public Vector3 getNormalAt(Vector3 point) {
        return new Vector3(0, 1, 0);
    }
}
