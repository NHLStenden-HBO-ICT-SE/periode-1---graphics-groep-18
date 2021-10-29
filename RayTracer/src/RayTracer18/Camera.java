package RayTracer18;

public class Camera {

    //Position is eye point
    private final Vector3 position;
    private Vector3 direction;
    private double fov;
    private double rotation;

    private final Vector3 topRight;
    private final Vector3 bottomLeft;
    private final Vector3 topLeft;
    private final Vector3 bottomRight;
    private Vector2 projectorSize;
    private final Scene3D scene;


    public Camera(double fov, Scene3D scene) {
        this.fov = fov;
        this.position = new Vector3();
        this.scene = scene;
        this.topRight = new Vector3(0.5, 0.25, fov);
        this.topLeft = new Vector3(-0.5, 0.25, fov);
        this.bottomLeft = new Vector3(-0.5, -0.25, fov);
        this.bottomRight = new Vector3(0.5, -0.25, fov);

    }

    public double getFov() {
        return this.fov;
    }

    public void setFov(double fov) {
        this.fov = Math.max(0.1,fov);
    }

    public Vector3 getPosition() {
        return position.clone();
    }

    public void setProjectorSize(Vector2 size) {
        this.projectorSize = size;
    }

    /**
     * Get the ray hit at canvas x-y
     *
     * @param x
     * @param y
     * @return Rayhit containing the color and distance
     */
    public RayHit getRayHit(double x, double y) {

        //Calculate the worldposition of the pixel on the projection plane
        Vector3 worldPos = new Vector3(topLeft.x + (x / projectorSize.x * Vector3.sub(topRight, topLeft).getLength()), bottomRight.y + (y / projectorSize.y * Vector3.sub(topRight, bottomRight).getLength()), fov);

        //Create the ray with the direction from eye(this.position) to the worldPos
        Ray r = new Ray(this.position, Vector3.sub(worldPos, this.position.clone()).normalize(), this.scene);
        Vector2 targetPixels = new Vector2(x, y);
        r.targetPixels = targetPixels;
        RayHit rayHit = r.shoot();
        rayHit.targetPixels = targetPixels;

        return rayHit;


    }


}
