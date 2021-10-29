package RayTracer18;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderWorker implements Runnable
{
    int maxX;
    int maxY;
    int startX;
    Scene3D scene;
    boolean wait = false;
    public ConcurrentLinkedQueue<RayHit> data1 = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<RayHit> data2 = new ConcurrentLinkedQueue<>();
    private boolean useData1 = true;

    public RenderWorker(int startX, int maxX, int maxY, Scene3D scene) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.scene = scene;
        this.startX = startX;

    }

    /**
     * Get the calculated data that the thread has made so far
     * @return
     */
    public ArrayList<RayHit> getData(){
        //Need so switch lists because will we lose some data in the returning process
        ArrayList<RayHit> result = new ArrayList<>();
        if(useData1){
             useData1 = false;
             result.addAll(this.data1);
             this.data1.clear();

        }
        else{
            useData1 = true;
            result.addAll(this.data2);
            this.data2.clear();

        }
        return result;
    }

    public void run()
    {

            try
            { for (int x =startX ; x < maxX; x++){

                for(int y=0; y < maxY; y++){


                    //Canvas y = 0 is the top, in 3d its the bottom.
                    int useY =(maxY - y);
                    RayHit rayHit = scene.camera.getRayHit(x, y);
                    rayHit.targetPixels = new Vector2(x, useY);
                    if(useData1){
                        data1.add(rayHit);

                    }
                    else{
                        data2.add(rayHit);
                    }
                }

            }
                wait = true;

            }
            catch (Exception e) {System.out.println(e);}
        }


}