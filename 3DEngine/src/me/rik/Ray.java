package me.rik;


public class Ray {
    public Point origin;
    public Vector3 direction;
    public double t;

    public Ray(Point origin, Vector3 direction){
        this.t = Double.POSITIVE_INFINITY;
        this.direction = direction.normalize();

        this.origin = origin;
    }

    public Double intersects(Point p){
        return Double.POSITIVE_INFINITY;
    }

//    public Point getEnd (double t){
//        //TODO: get end of ray
//    }

//    public Point getEnd(){
//        return getEnd(this.t);
//    }

    @Override
    public String toString() {
        return "Ray{" +
                "origin=" + origin +
                ", direction=" + direction +
                ", t=" + t +
                '}';
    }
}
