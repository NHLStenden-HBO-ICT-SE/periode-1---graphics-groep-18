package RayTracer18;

import java.util.ArrayList;

public class Vector3 {

    public double x;
    public double y;
    public double z;
    private double w;


    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3 clone(){
        return new Vector3(this.x, this.y, this.z);
    }

    public Vector3 copy(Vector3 target){
        this.x = target.x;
        this.y = target.y;
        this.z = target.z;
        return this;
    }
    public Vector3(Vector3 toCopy){
        this.x = toCopy.x;
        this.y = toCopy.y;
        this.z = toCopy.z;
    }


    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getZ(){
        return this.z;
    }



    public static Vector3 cross(Vector3 v0, Vector3 v1){
        return new Vector3(
                v1.y * v0.z - v0.z * v1.y,
                v1.z * v0.x - v0.x * v1.z,
                v1.x * v0.y - v0.y * v1.x );
    }
    public static Vector3 add(Vector3 v1, Vector3 v2){
        return new Vector3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }
    public static Vector3 sub(Vector3 v1, Vector3 v2){
        return new Vector3(v1.x - v2.x,
                v1.y - v2.y,
                v1.z - v2.z);
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
    public static Vector3 multiplyScalar(Vector3 v, double m){
        return new Vector3(
                v.x * m,
                v.y * m,
                v.z * m
        );
    }


    public Vector3 multiply(Vector3 v2){

        this.x *=  v2.x;
        this.y *=  v2.y;
        this.z *=  v2.z;
        return this;
    }
    public Vector3 multiplyScalar(double m){
        this.x *= m;
        this.y *= m;
        this.z *= m;
        return this;
    }

    public Vector3 subtract(Vector3 v2){
        this.x -= v2.x;
        this.y -= v2.y;
        this.z -= v2.z;
        return this;
    }


    public double[] toArray(){
        return new double[]{x, y, z};
    }

    public ArrayList<Double> toArrayList(){
        ArrayList<Double> list = new ArrayList<>();
        list.add(x);
        list.add(y);
        list.add(z);
        return list;
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
     * @param v Vector to calculate the dot with
     * @return The dot product
     */
    public double dot(Vector3 v){
        return this.x * v.x +
                this.y * v.y +
                this.z * v.z;
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

    public Vector3 cross(Vector3 v) {
        return new Vector3(
                this.y * v.z - this.z * v.y,
                this.z * v.x - this.x * v.z,
                this.x * v.y - this.y * v.x
        );
    }

    @Override
    public String toString() {
        return String.format("Vector3{x=(%f, y=%f, z=%f)}", x, y, z);
    }


    //de functie add1 en subtract2 niet aanpassen anders hele programma kaduk
    public Vector3 add1(Vector3 vec) {
        return new Vector3(this.x + vec.x, this.y + vec.y, this.z + vec.z);
    }

    public Vector3 subtract2(Vector3 vec) {
        return new Vector3(this.x - vec.x, this.y - vec.y, this.z - vec.z);
    }

    public Vector3 multiply(float scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3 transformVector(Matrix matrix) {
        if(this.getLength() != matrix.getColumns())
            throw new RuntimeException("Invalid input");
        int rows = matrix.getRows();
        int columns = matrix.getColumns();
        double[] listVector = new double[rows];

        for (int i = 0; i < rows; i++){
            double value = 0;
            for (int j = 0; j < columns; j++){
                value += matrix.matrix[i][j] * this.toArray()[j];
            }
            listVector[i] = value;
        }
        this.x = listVector[0];
        this.y = listVector[1];
        this.z = listVector[2];
        return this;
    }

    public Vector3 rotateZAxis(double theta){
        double[][] matrix = new double[][]{
                {Math.cos(theta), -Math.sin(theta), 0},
                {Math.sin(theta), Math.cos(theta), 0},
                {0,0,1}
        };

        double[] listVectorZ = new double[matrix.length];

        for (int i = 0; i < matrix.length; i++){
            double value = 0;
            for (int j = 0; j < matrix.length; j++){
                value += matrix[i][j] * this.toArray()[j];
            }
            listVectorZ[i] = value;
        }
        this.x = listVectorZ[0];
        this.y = listVectorZ[1];
        this.z = listVectorZ[2];
        return this;
    }

    public Vector3 rotateXAxis(double theta){
        double[][] matrix = new double[][]{
                {1, 0, 0},
                {0, Math.cos(theta), -Math.sin(theta)},
                {0, Math.sin(theta), Math.cos(theta)}
        };

        double[] listVectorX = new double[matrix.length];

        for (int i = 0; i < matrix.length; i++){
            double value = 0;
            for (int j = 0; j < matrix.length; j++){
                value += matrix[i][j] * this.toArray()[j];
            }
            listVectorX[i] = value;
        }
        this.x = listVectorX[0];
        this.y = listVectorX[1];
        this.z = listVectorX[2];
        return this;
    }

    public Vector3 rotateYAxis(double theta) {
        double[][] matrix = new double[][]{
                {Math.cos(theta), 0, Math.sin(theta)},
                {0, 1, 0},
                {-Math.sin(theta),0,Math.cos(theta)}
        };

        double[] listVectorY = new double[matrix.length];

        for (int i = 0; i < matrix.length; i++){
            double value = 0;
            for (int j = 0; j < matrix.length; j++){
                value += matrix[i][j] * this.toArray()[j];
            }
            listVectorY[i] = value;
        }
        this.x = listVectorY[0];
        this.y = listVectorY[1];
        this.z = listVectorY[2];
        return this;
    }


    public static Vector3 rotateVector(Vector3 vec, double theta){
        double x, y, z;
        double u, v, w;
        x=vec.getX();y=vec.getY();z=vec.getZ();
        u= 0 ;v= 1;w= 0;
        double xPrime = u*(u*x + v*y + w*z)*(1d - Math.cos(theta))
                + x*Math.cos(theta)
                + (-w*y + v*z)*Math.sin(theta);
        double yPrime = v*(u*x + v*y + w*z)*(1d - Math.cos(theta))
                + y*Math.cos(theta)
                + (w*x - u*z)*Math.sin(theta);
        double zPrime = w*(u*x + v*y + w*z)*(1d - Math.cos(theta))
                + z*Math.cos(theta)
                + (-v*x + u*y)*Math.sin(theta);
        return new Vector3(xPrime, yPrime, zPrime);
    }
}

