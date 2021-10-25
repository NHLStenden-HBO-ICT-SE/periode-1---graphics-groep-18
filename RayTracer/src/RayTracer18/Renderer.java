package RayTracer18;


import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Renderer extends AnimationTimer {

    public static Canvas canvas;
    public static double EPSILON = 0.001;
    public static Dictionary<String, RayHit> storage = new Hashtable<>();

    public static ArrayList<Thread> threads = new ArrayList<>();
    public static ArrayList<RenderWorker> workers = new ArrayList<>();

    private static boolean started = false;

    public static int pixelWritten = 0;

    public static Scene3D scene;

    public static void renderScene(Scene3D scene, Canvas canvas) {
        Renderer.scene = scene;
        Renderer.canvas = canvas;
        int numKeepFreeThreads = 1;
        int numOfThreads = Runtime.getRuntime().availableProcessors() - numKeepFreeThreads;
        System.out.println("Working with " + numOfThreads + " threads 🔥");
        System.out.println("Scene contains " + scene.getObjects().size() + " objects.");
        System.out.println("Canvas size: width: " + Math.round(canvas.getWidth()) + "px  height:" + Math.round(canvas.getHeight())+"px");
        int threadStartIndex = 0;
        int widthPerThread = (int)canvas.getWidth()/numOfThreads;


        if(workers.size() == 0){
            for (int i = 0; i < numOfThreads; i++) {
                int bonus = 0;
                if(i == numOfThreads-1){
                    bonus = (int)canvas.getWidth() % numOfThreads;
                }
                RenderWorker w1 =  new RenderWorker(threadStartIndex,threadStartIndex + widthPerThread + bonus, (int)canvas.getHeight(), scene);
                threadStartIndex += widthPerThread;
                workers.add(w1);
                Renderer.threads.add(new Thread(w1, "Worker_" + threadStartIndex));


            }
        }

        System.out.println("TIMERR");

        for (Thread t: Renderer.threads) {
            t.start();
        }
        started = true;

    }

    @Override
    public void handle(long l) {
        if(!started){
            return;
        }
        GraphicsContext gc = canvas.getGraphicsContext2D();
        PixelWriter pxw = gc.getPixelWriter();
        for(RenderWorker worker: workers){
            ArrayList<RayHit> data= worker.getData();
            if(data == null || data.size() == 0){
                continue;
            }
            Iterator<RayHit> it = data.iterator();
            while(it.hasNext()){
                RayHit rayHit = it.next();
                Color c = rayHit.getColor();
                if(c == null){
                    c = scene.voidColor;
                }
                pixelWritten += 1;
                pxw.setColor((int)rayHit.targetPixels.x, (int)rayHit.targetPixels.y, c);

            }
            Main.progressBar.setProgress(pixelWritten / (canvas.getWidth() * canvas.getHeight()));
            if(pixelWritten == (canvas.getWidth() * canvas.getHeight())){
                //Render is done....
                Main.progressBar.setStyle("-fx-accent: green");

            }
    }}
}
