package RayTracer18;

public class PixelColor {
        private float red;
        private float green;
        private float blue;
        private float alpha;

        public PixelColor(float red, float green, float blue, float alpha){
                this.red = red;
                this.green = green;
                this.blue = blue;
                this.alpha = alpha;
        }

        public float getRed() {return red * 255; }

        public float getGreen() {return green * 255; }

        public float getBlue() {
                return blue * 255;
        }

        public float getAlpha() {
                return alpha;
        }

        public static PixelColor mixColor(PixelColor c0, PixelColor c1){

                float totalAlpha = c0.getAlpha() + c1.getAlpha();
                float weight0 = c0.getAlpha() / totalAlpha;
                float weight1 = c1.getAlpha() / totalAlpha;

                float r = weight0 * c0.getRed() + weight1 * c1.getRed();
                float g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
                float b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
                float a = totalAlpha;

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
