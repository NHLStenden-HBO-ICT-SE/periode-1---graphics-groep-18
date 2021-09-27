package RayTracer18;

public abstract class Object3D {
    public Vector3 position;
    private float rotation;
    private int material;

    public Object3D(Vector3 pos){

    }

    public void rotate() {

    }

    public void intersectsRay(Ray ray) {

    }
    public Vector3 getNormalAt(Vector3 pos){
        //TODO:implement function ;)
        return null;
    };

    public Vector3 calculateIntersection(Ray ray) {
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
