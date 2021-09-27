package RayTracer18;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Renderer {

    public void renderScene(Scene3D scene, Canvas canvas) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        PixelWriter pxw = gc.getPixelWriter();
        for (int x =0 ; x < canvas.getWidth(); x++){
            for(int y=0; y < canvas.getHeight(); y++){

                //Canvas y = 0 is the top, in 3d its the bottom.
                int useY = (int)(canvas.getHeight() - y);
                pxw.setColor(x, useY, scene.camera.getColor(x, y));
            }
    }

}
}
