package RayTracer18;

public class Vector3 {

    public double x;
    public double y;
    public double z;


    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 clone(){
        return new Vector3(this.x, this.y, this.z);
    }



    public static Vector3 cross(Vector3 v0, Vector3 v1){
        return new Vector3(
                v1.z * v0.y - v0.z * v1.y,
                v1.x * v0.z - v0.x * v1.z,
                v1.y * v0.x - v0.y * v1.x );
    }
    public static Vector3 sub(Vector3 v1, Vector3 v2){
        return new Vector3(
                v2.x - v1.x,
                v2.y - v1.y,
                v2.z - v1.z
        );
    }
    public static double dot(Vector3 v1, Vector3 v2){
        double an = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
        return an;
    }
    public static Vector3 multiply(Vector3 v1, Vector3 v2){
        return new Vector3(
                v1.x * v2.x,
                v1.y*v2.y,
                v1.z*v2.z
        );
    }
    public static Vector3 multiplyScalar(Vector3 v, float m){
        return new Vector3(
                v.x * m,
                v.y * m,
                v.z * m
        );
    }



    public Vector3 multiply( Vector3 v2){

        this.x *=  v2.x;
        this.y *=  v2.y;
        this.z *=  v2.z;
        return this;


    }
    public Vector3 multiplyScalar(float m){
        this.x *= m;
        this.y *= m;
        this.z *= m;
        System.out.println(this);
        return this;
    }

    public Vector3 sub(Vector3 v2){
        this.x -= v2.x;
        this.y -= v2.y;
        this.z -= v2.z;
        return this;
    }





    /**
     *
     * @param x double to add to x
     * @param y double to add to y
     * @param z double to add to z
     * @return Mutated Vector3
     */
    public Vector3 add(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     *
     * @param vector Vector to add
     * @return Mutated vector
     */
    public Vector3 add(Vector3 vector){
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;
        return this;
    }

    /**
     * Get the length of the Vector
     * @return Length of the vector
     */
    public double getLength(){
        return Math.sqrt(x*x + y*y +z*z);
    }

    /**
     * Divide all properties by s
     * @param s double to divide all properties with
     * @return The mutated Vector3
     */
    public Vector3 divideScalar(double s){
        this.x = this.x / s;
        this.y = this.y / s;
        this.z = this.z / s;
        return this;
    }

    /**
     * Normalize the Vector
     * @return The mutated Vector3
     */
    public Vector3 normalize(){
        return this.divideScalar(this.getLength());
    }

    /**
     * Get the distance to another vector
     * @param target Vector3 of the target
     * @return The distance between the 2 Vectors
     */
    public double distanceTo(Vector3 target){
        return Math.sqrt(Math.pow(this.x - target.x, 2) + Math.pow(this.y - target.y, 2) + Math.pow(this.z - target.z, 2));
    }

    /**
     * Get the dot product
     * @param target Vector to calculate the dot with
     * @return The dot product
     */
    public double dot(Vector3 target){
        double an = this.x * target.x + this.y * target.y + this.z * target.z;
        return an;
    }

    /**
     *
     * @param target Target angle to get the angle with
     * @return the angle in RADIANS between 2 vectors
     */
    public double angleTo(Vector3 target){
        double dot = this.dot(target); // 20
        double mag1 = this.getLength();
        double mag2 = target.getLength();
        return Math.acos(dot / (mag1*mag2));
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    //TODO: All matrix functions





}

