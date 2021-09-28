package RayTracer18.Primitives;

import RayTracer18.Ray;
import RayTracer18.Vector3;

public abstract class Object3D {
    public Vector3 position;
    private double rotation;
    private Material material;

    public Object3D(Vector3 pos){
        this.position = pos;
    }

    public void rotate() {

    }


    public Vector3 getNormalAt(){
        return null;
    }

    public Material getMaterial(){
        return this.material;
    }

    public void applyMaterial(Material m){
        this.material = m;
    }


    public Vector3 getNormalAt(Vector3 pos){
        //TODO:implement function ;)
        return null;
    };

    public Vector3 calculateIntersection(Ray ray) {
        System.out.println("Using default intersection function. remove this");
        return null;
    }

    @Override
    public String toString() {
        return "Object3D{" +
                "position=" + position +
                ", rotation=" + rotation +
                ", material=" + material +
                '}';
    }
}
