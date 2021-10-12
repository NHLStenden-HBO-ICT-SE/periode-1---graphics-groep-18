package RayTracer18.Primitives;

import RayTracer18.Material.Material;
import RayTracer18.Ray;
import RayTracer18.Vector3;

import java.util.UUID;

public abstract class Object3D {


    public Vector3 position;
    private double rotation;
    private Material material;
    public String name;
    public String id;

    public Material oldMaterial;


    public Object3D(Vector3 pos){
        this.position = pos;
        this.id = UUID.randomUUID().toString();
    }

    public void rotate() {

    }

    public void restoreMaterial(){
        this.material = this.oldMaterial;
    }

    public Vector3 getNormalAt(){
        return null;
    }

    public Material getMaterial(){
        return this.material;
    }

    public void applyMaterial(Material m){
        this.oldMaterial = this.material;
        this.material = m;
    }

    public String getName(){
        return this.name;
    }

    public Vector3 getNormalAt(Vector3 pos){
        //TODO:implement function ;)
        return null;
    };

    public void setPosition(Vector3 position) {
        this.position = position;
    }


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
