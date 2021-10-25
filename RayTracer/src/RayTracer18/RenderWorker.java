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
import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderWorker implements Runnable
{
    int maxX;
    int maxY;
    int startX;
    Scene3D scene;
    boolean exit = false;
    public ConcurrentLinkedQueue<RayHit> data = new ConcurrentLinkedQueue<>();

    public RenderWorker(int startX, int maxX, int maxY, Scene3D scene) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.scene = scene;
        this.startX = startX;

    }

    public ConcurrentLinkedQueue<RayHit> getData(){
        ConcurrentLinkedQueue<RayHit> sendBack = this.data;
        this.data = new ConcurrentLinkedQueue<>();
        if(this.data.size() > 0){
            System.out.println('l');
        }
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
                    int useY =(maxY - y);
                    RayHit rayHit = scene.camera.getRayHit(x, y);
                    rayHit.targetPixels = new Vector2(x, useY);
                    data.add(rayHit);
                }

            }

        }
        catch (Exception e) {System.out.println(e);}
    }
}//slowthread