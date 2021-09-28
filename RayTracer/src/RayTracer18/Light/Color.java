package RayTracer18.Light;

public class Color {

    double r,g,b,a;


    public Color(double r, double g, double b, double a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color multiply(Color c){
        this.r *= c.r;
        this.g *= c.g;
        this.b *= c.b;
        this.a *= c.a;
        return this;
    }


}
