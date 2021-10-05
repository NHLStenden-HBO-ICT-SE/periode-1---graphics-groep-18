package RayTracer18.Primitives;


import RayTracer18.Ray;
import RayTracer18.Vector3;

public class Box extends Object3D {
    Vector3 min, max;

    public Box(Vector3 position, Vector3 max) {
        super(position);
        this.max = position.add1(max.multiply(0.5F));
        this.min = position.subtract2(max.multiply(0.5F));
        this.name = "Box";

    }

    @Override
    public Vector3 calculateIntersection(Ray ray) {
        double t1;
        double t2;
        double tnear = Double.NEGATIVE_INFINITY;
        double tfar = Double.POSITIVE_INFINITY;
        double temp;
        boolean intersectFlag = true;
        double[] rayDirection = ray.getDirection().toArray();
        double[] rayOrigin = ray.getOrigin().toArray();
        double[] b1 = min.toArray();
        double[] b2 = max.toArray();

        for (int i = 0; i < 3; i++) {
            if (rayDirection[i] == 0) {
                if (rayOrigin[i] < b1[i] || rayOrigin[i] > b2[i])
                    intersectFlag = false;
            } else {
                t1 = (b1[i] - rayOrigin[i]) / rayDirection[i];
                t2 = (b2[i] - rayOrigin[i]) / rayDirection[i];
                if (t1 > t2) {
                    temp = t1;
                    t1 = t2;
                    t2 = temp;
                }
                if (t1 > tnear)
                    tnear = (float) t1;
                if (t2 < tfar)
                    tfar = (float) t2;
                if (tnear > tfar)
                    intersectFlag = false;
                if (tfar < 0)
                    intersectFlag = false;
            }
        }
        if (intersectFlag)
            return ray.getOrigin().add(ray.getDirection().multiplyScalar(tnear));
        else
            return null;
    }

    @Override
    public Vector3 getNormalAt(Vector3 point) {

        double[] direction = point.subtract2(position).toArray();
        double biggestValue = Float.NaN;

        for (int i = 0; i<3; i++) {
            if (Double.isNaN(biggestValue) || biggestValue < Math.abs(direction[i])) {
                biggestValue =  Math.abs(direction[i]);
            }
        }

        if (biggestValue == 0) {
            return new Vector3(0, 0, 0);
        } else {
            for (int i = 0; i<3; i++) {
                if (Math.abs(direction[i]) == biggestValue) {
                    float[] normal = new float[] {0,0,0};
                    normal[i] = direction[i] > 0 ? 1 : -1;

                    return new Vector3(normal[0], normal[1], normal[2]);
                }
            }
        }

        return new Vector3(0, 0, 0);
    }
}