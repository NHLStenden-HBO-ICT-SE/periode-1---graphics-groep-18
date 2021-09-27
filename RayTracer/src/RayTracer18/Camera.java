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
        this.topRight = new Vector3(1, 0.5, fov);
        this.topLeft = new Vector3(-1, 0.5, fov);
        this.bottomLeft = new Vector3(-1, -0.5, fov);
        this.bottomRight = new Vector3(1, -0.5, fov);

    }
    public void setProjectorSize(Vector2 size){
        this.projectorSize = size;
    }



    public Color getColor(double x, double y){

        //Calculate the worldposition of the pixel on the projection plane
        Vector3 worldPos = new Vector3( topLeft.x + (x/projectorSize.x * Vector3.sub(topRight, topLeft).getLength()), bottomRight.y + (y/projectorSize.y * Vector3.sub(topRight, bottomRight).getLength()), fov);

        //Create the ray with the direction from eye(this.position) to the worldPos
        Ray r = new Ray(this.position, Vector3.sub(worldPos, this.position.clone()).normalize(), this.scene);
        Color res = r.shoot();

        return res;



    }










}
