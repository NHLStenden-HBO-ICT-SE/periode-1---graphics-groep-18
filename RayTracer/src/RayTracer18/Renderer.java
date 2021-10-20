package RayTracer18;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.*;


public class Renderer {

    public static Canvas canvas;
    public static double EPSILON = 0.001;
    public static Dictionary<String, RayHit> storage = new Hashtable<>();

    public static ArrayList<Thread> threads = new ArrayList<>();
    public static ArrayList<RenderWorker> workers = new ArrayList<>();

    private static Timer timer;
    public static void draw(int x, int y, Color c){
        canvas.getGraphicsContext2D().getPixelWriter().setColor(x, y, c);
    }


    public static void renderScene(Scene3D scene, Canvas canvas) {
        if(timer != null){
            //When a ren
            timer.cancel();

        }
        Renderer.canvas = canvas;
        int numOfThreads = Runtime.getRuntime().availableProcessors();;
        int threadStartIndex = 0;
        int widthPerThread = (int)canvas.getWidth()/numOfThreads;
        if(workers.size() == 0){
            for (int i = 0; i < numOfThreads; i++) {

                RenderWorker w1 =  new RenderWorker(threadStartIndex,threadStartIndex + widthPerThread, (int)canvas.getHeight(), scene);
                threadStartIndex += widthPerThread;
                workers.add(w1);
                Renderer.threads.add(new Thread(w1, "Worker_" + threadStartIndex));


            }
        }


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                PixelWriter pxw = gc.getPixelWriter();
                for(RenderWorker worker: workers){
                    Hashtable<Vector2, Color> data= worker.getData();
                    Enumeration<Vector2> keys = data.keys();
                    while(keys.hasMoreElements()){
                        Vector2 key = keys.nextElement();
                        pxw.setColor((int)key.x, (int)key.y, data.get(key));
                    }
                }


            }
        }, 0, 1000);
        for (Thread t: Renderer.threads) {
            t.start();
        }







    }
}
