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

        public double getAlpha() {
                return alpha;
        }

        public double getRed() {
                return red;
        }

        public double getGreen() {
                return green;
        }

        public double getBlue() {
                return blue;
        }

        public static PixelColor mixColor(PixelColor c0, PixelColor c1, double weight0, double weight1){

                double r = weight0 * c0.getRed() + weight1 * c1.getRed();
                double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
                double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
                double a = Math.max(c0.getAlpha(), c1.getAlpha());

                return new PixelColor((int) r, (int) g, (int) b, (int) a);

        }
}
