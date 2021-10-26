package RayTracer18.Material;

import RayTracer18.Vector3;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Material {
    public Color color;
    private double reflection;

    public BufferedImage colorMap = null;
    public boolean isChecker = false;
    public Material(Color c){
        this.color = c;
    }


    public void setColorMap(BufferedImage imgBuffer){
        this.colorMap = imgBuffer;
    }

    public Color getColorByUv(double u, double v){
        u = u%1;
        v = v%1;
        int width = this.colorMap.getWidth();
        int height = this.colorMap.getHeight();

        int x = (int)(u*width);
        int y = (int)(v*height);
        //System.out.println("X: " + x + " Y: " + y);
        int clr = 0;
        try{
           clr  = this.colorMap.getRGB(x, y);

        }
        catch(Error r){
            System.out.println("Could not get Color");
            return null;
        }

        int red =   (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue =   clr & 0x000000ff;
        //System.out.println("R: " + red + " G:" + green + " B:" + blue);
        Color c = new Color((double)red/255, (double)green/255, (double)blue/255, 1);
        return c;
    }

    public void setReflection(double v){
        this.reflection = v;
    }

    //Use this later with parameters hitPoint to look up the pixel in the maps if needed
    public Color getColorAt(){
        return color;
    }
    public Color getColorAt(Vector3 point){
        return color;
    }

    public double getReflection(){
        return reflection;
    }

    @Override
    public String toString() {
        return "Material{" +
                "color=" + color +
                '}';
    }
}
