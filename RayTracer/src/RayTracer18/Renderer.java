package RayTracer18;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;


public class Renderer {


    public static double EPSILON = 0.001;


    public static void renderScene(Scene3D scene, Canvas canvas) {

        //BufferedImage im = new BufferedImage(700, 350, BufferedImage.TYPE);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        PixelWriter pxw = gc.getPixelWriter();
        for (int x =0 ; x < canvas.getWidth(); x++){
            System.out.println("Progress: " + x/ canvas.getWidth()*100);

            for(int y=0; y < canvas.getHeight(); y++){
                //Canvas y = 0 is the top, in 3d its the bottom.
                int useY = (int)(canvas.getHeight() - y);
                RayHit rayHit = scene.camera.getRayHit(x, y);
                Color c = rayHit.getColor();
                if(c == null){
                    c = scene.voidColor;

                }
                pxw.setColor(x, useY, c);




            }

        }



    }
}
