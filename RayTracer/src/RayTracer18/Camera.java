package RayTracer18;

import javafx.scene.paint.Color;

public class Camera {
    private Vector3 position;
    private Vector3 direction;
    private double fov;
    private float rotation;
    private Vector3 center;

    private Vector3 topRight;
    private Vector3 bottomLeft;
    private Vector3 topLeft;
    private Vector3 bottomRight;
    private Vector2 projectorSize;
    private Scene3D scene;

    public void moveCamera() {

    }




    public Camera(double fov, Scene3D scene){
        this.fov = fov;
        this.position = new Vector3();
        this.scene = scene;
        this.fov = 0.3;
        this.topRight = new Vector3(1, 0.5, fov);
        this.topLeft = new Vector3(-1, 0.5, fov);
        this.bottomLeft = new Vector3(-1, -0.5, fov);
        this.bottomRight = new Vector3(1, -0.5, fov);

    }
    public void setProjectorSize(Vector2 size){
        this.projectorSize = size;
    }



    public Color getColor(double x, double y){
        double cury = bottomRight.y;
        double perc = y/projectorSize.y;
        double length = Vector3.sub(topRight, bottomRight).getLength();
        double yc = cury + (perc * length);
        Vector3 worldPos = new Vector3( topLeft.x + (x/projectorSize.x * Vector3.sub(topRight, topLeft).getLength()), yc, fov);


        Ray r = new Ray(this.position, Vector3.sub(worldPos, this.position.clone()).normalize(), this.scene);
        Color res = r.shoot();

        return res;



    }










}
