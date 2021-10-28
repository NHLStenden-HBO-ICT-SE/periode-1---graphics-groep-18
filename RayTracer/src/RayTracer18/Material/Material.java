package RayTracer18.Material;

import RayTracer18.Vector3;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Material {
    public Color color;
    private double reflection;

    public BufferedImage colorMap = null;
    public boolean textured = false;


    public boolean hasNormalMap = false;
    public BufferedImage normalMap = null;



    public boolean isChecker = false;
    public Material(Color c){
        this.color = c;
    }


    public void setColorMap(BufferedImage imgBuffer){
        this.colorMap = imgBuffer;
        this.textured = true;
    }


    public Color getPixel(BufferedImage img, double u, double v){
        u = u%1;
        v = v%1;
        int width = this.colorMap.getWidth();
        int height = this.colorMap.getHeight();

        int x = (int)(u*width);
        int y = (int)(v*height);
        int clr = 0;
        try{
            clr  = img.getRGB(x, y);
        }
        catch(Error r){
            System.out.println("Error while getting color at x:" + x + " y:" + y);
            return null;
        }

        int red =   (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue =   clr & 0x000000ff;
        Color c = new Color((double)red/255, (double)green/255, (double)blue/255, 1);
        return c;
    }

    public Color getColorByUv(double u, double v){
        return getPixel(this.colorMap, u, v);
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
