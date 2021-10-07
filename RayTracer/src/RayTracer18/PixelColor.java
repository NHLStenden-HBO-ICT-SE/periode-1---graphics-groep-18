package RayTracer18;

public class PixelColor {
        private double red;
        private double green;
        private double blue;
        private double alpha;

        public PixelColor(double red, double green, double blue, double alpha){
                this.red = red;
                this.green = green;
                this.blue = blue;
                this.alpha = alpha;
        }

        public double getRed() {return red * 255; }

        public double getGreen() {return green * 255; }

        public double getBlue() {
                return blue * 255;
        }

        public double getAlpha() {
                return alpha;
        }

        public static PixelColor mixColor(PixelColor c0, PixelColor c1){

                double totalAlpha = c0.getAlpha() + c1.getAlpha();
                double weight0 = c0.getAlpha() / totalAlpha;
                double weight1 = c1.getAlpha() / totalAlpha;

                double r = weight0 * c0.getRed() + weight1 * c1.getRed();
                double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
                double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
                double a = totalAlpha;

                return new PixelColor(r / 255, g / 255, b / 255, a);
        }

        @Override
        public String toString() {
                return "PixelColor{" +
                        "red=" + red +
                        ", green=" + green +
                        ", blue=" + blue +
                        ", alpha=" + alpha +
                        '}';
        }
}
