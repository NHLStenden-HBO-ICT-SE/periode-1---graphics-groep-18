package RayTracer18.Primitives;

import RayTracer18.Ray;
import RayTracer18.Renderer;
import RayTracer18.Vector3;
import javafx.scene.paint.Color;

public class Plane extends Object3D{


    private Vector3 normal;

    public Plane(Vector3 pos, Vector3 normal) {
        super(pos);
        this.normal = normal;
        this.name = "Plane";
    }

    @Override
    public Vector3 calculateIntersection(Ray ray) {
        double dem = Vector3.dot(this.normal, ray.getDirection());

        if(Math.abs(dem) > Renderer.EPSILON){
            Vector3 dif = Vector3.sub(this.position, ray.getOrigin());
            double t = Vector3.dot(dif, this.normal) / dem;
            if(t < Renderer.EPSILON){
                return null;
            }
            Vector3 endpoint = ray.getOrigin().add(ray.getDirection().multiplyScalar(t));


            return endpoint;
        }
        return null;

    }


    @Override
    public Color getColorAt(Vector3 cords) {
        if(Math.sin(cords.getX()*4) <0){
            if(Math.cos(cords.getZ()*4) > 0){
                return Color.WHITE;
            }
            return Color.GRAY;
        }
        if(Math.cos(cords.getZ()*4) > 0){
            return Color.GRAY;
        }
        return Color.WHITE;
    }

    @Override
    public Vector3 getNormalAt(Vector3 pos) {

        return this.normal;
    }
}
