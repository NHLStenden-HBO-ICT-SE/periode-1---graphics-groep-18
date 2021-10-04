package RayTracer18.Material;

public class PixelColor {

    float r;
    float g;
    float b;

    public PixelColor(float r, float g, float b){
        this.r = r;
        this.g = g;
        this.b = b;
    }


    public void multiply(float v){
        this.r *= v;
        this.g *= b;
        this.b *= b;
    }


    public void add(float s){
        this.r += s;
        this.g += s;
        this.b += s;
    }







}
