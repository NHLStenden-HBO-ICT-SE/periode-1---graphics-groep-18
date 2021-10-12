package RayTracer18.ObjLoader;


import RayTracer18.Primitives.Object3D;
import RayTracer18.Ray;
import RayTracer18.Vector3;

public class Triangle extends Object3D {

    public Vertex v1, v2, v3;

    public Triangle(Vertex v1, Vertex v2, Vertex v3, Material material) {
        super(material);
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.name = "Triangle";
    }

    @Override
    public Vector3 getNormalAt(Vector3 point) {
        return this.v1.normal.normalize();
    }

    @Override
    public Vector3 calculateIntersection(Ray ray) {
        // We are using the Moller-Trumbore intersection algorithm
        double epsilon = 0.0000001;

        Vector3 edge1 = this.v2.position.subtract(this.v1.position);
        Vector3 edge2 = this.v3.position.subtract(this.v1.position);

        Vector3 h = ray.direction.cross(edge2);
        double a = edge1.dot(h);

        if (a > -epsilon && a < epsilon)
            return null;

        double f = 1.0 / a;
        Vector3 s = ray.getOrigin().subtract(this.v1.position);
        double u = f * s.dot(h);

        if (u < 0.0 || u > 1.0)
            return null;

        Vector3 q = s.cross(edge1);
        double v = f * ray.direction.dot(q);

        if (v < 0.0 || u + v > 1.0)
            return null;

        double t = f * edge2.dot(q);
        if (t > epsilon)
            return ray.pointAt(t);

        return null;
    }

    public String toString() {
        return String.format("[%s, %s, %s]", this.v1, this.v2, this.v3);
    }
}
