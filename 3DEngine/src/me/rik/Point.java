package me.rik;

public class Point {
    public Vector3 position;

    public Point(Vector3 position) {
        this.position = position;
    }

    public double distance(Point p) {
        return Math.sqrt(
                (p.position.x - position.x) * (p.position.x - position.x) +
                        (p.position.y - position.y) * (p.position.y - position.y) +
                        (p.position.z - position.z) * (p.position.z - position.z));
    }
}
