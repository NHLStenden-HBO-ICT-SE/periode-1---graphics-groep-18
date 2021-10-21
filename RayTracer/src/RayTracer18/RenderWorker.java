package RayTracer18;

import RayTracer18.Main;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public class RenderWorker implements Runnable
{
    int maxX;
    int maxY;
    int startX;
    Scene3D scene;
    boolean exit = false;
    public LinkedHashMap<Vector2, RayHit> data = new LinkedHashMap<>();

    public RenderWorker(int startX, int maxX, int maxY, Scene3D scene) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.scene = scene;
        this.startX = startX;

    }

    public LinkedHashMap<Vector2, RayHit> getData(){
        LinkedHashMap<Vector2, RayHit> sendBack = this.data;
        this.data = new LinkedHashMap<>();

        return (LinkedHashMap<Vector2, RayHit>) sendBack.clone();
    }
    public void shutDown(){
        exit = true;
    }
    public void run()
    {
        try
        {
            System.out.println(startX + " to MAX " +maxX  + " and y: " + maxY);

            for (int x =startX ; x < maxX; x++){

                for(int y=0; y < maxY; y++){
                    if(exit){
                        return;
                    }
                    //Canvas y = 0 is the top, in 3d its the bottom.
                    int useY =(maxY - y);
                    RayHit rayHit = scene.camera.getRayHit(x, y);

                    data.put(new Vector2(x, useY), rayHit);
                }

            }

        }
        catch (Exception e) {System.out.println(e);}
    }
}//slowthread