package me.rik;

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
        return this.x * target.x + this.y * target.y + this.z * target.z;
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

    //TODO: multiply(vector3)




    //TODO: All matrix functions





}
