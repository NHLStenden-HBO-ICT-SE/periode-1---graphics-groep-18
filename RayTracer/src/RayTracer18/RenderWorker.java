package RayTracer18;

import RayTracer18.Main;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class RenderWorker implements Runnable
{
    int maxX;
    int maxY;
    int startX;
    Scene3D scene;
    boolean exit = false;
    public Hashtable<Vector2, Color> data = new Hashtable<>();

    public RenderWorker(int startX, int maxX, int maxY, Scene3D scene) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.scene = scene;
        this.startX = startX;

    }

    public Hashtable<Vector2, Color> getData(){
        Hashtable<Vector2, Color> sendBack = this.data;
        this.data = new Hashtable<>();

        return sendBack;
    }
    public void shutDown(){
        exit = true;
    }
    public void run()
    {
        try
        {

            for (int x =startX ; x < maxX; x++){

                for(int y=0; y < maxY; y++){
                    if(exit){
                        return;
                    }
                    //Canvas y = 0 is the top, in 3d its the bottom.
                    int useY = (int)(maxY - y);
                    RayHit rayHit = scene.camera.getRayHit(x, y);
                    Color c = rayHit.getColor();
                    if(c == null){
                        c = scene.voidColor;
                    }
                    data.put(new Vector2(x, useY), c);
                }

            }

        }
        catch (Exception e) {System.out.println(e);}
    }
}//slowthread