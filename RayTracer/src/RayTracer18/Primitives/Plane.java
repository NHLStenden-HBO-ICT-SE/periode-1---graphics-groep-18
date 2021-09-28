package RayTracer18.Primitives;

import RayTracer18.Ray;
import RayTracer18.Vector3;

public class Plane extends Object3D {

    public Plane(float height) {

        super(new Vector3(0, height, 0));
    }

    public Vector3 calculateIntersection(Ray r) {
        float t = (float) (-(r.getOrigin().getY()-position.getY()) / r.getDirection().getY());
        if (t > 0 && Float.isFinite(t))
        {
            return r.getOrigin().add(r.getDirection().multiplyScalar(t));
        }

        return null;
    }

    public Vector3 getNormalAt(Vector3 point) {
        return new Vector3(0, 1, 0);
    }
}
