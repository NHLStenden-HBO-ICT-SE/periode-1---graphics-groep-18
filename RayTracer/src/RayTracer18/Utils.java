package RayTracer18;

import javafx.scene.paint.Color;

public class Utils {


    public static Color mixColors(Color c1, Color c2) {
        return new Color(c1.getRed() * c2.getRed(), c1.getGreen() * c2.getGreen(), c1.getBlue() * c2.getBlue(), 1);
    }

    public static Color addColor(Color c1, Color adder, double factor) {
        factor = Math.min(factor, 1);
        return new Color(
                c1.getRed() * (1 - factor) + (adder.getRed() * factor),
                c1.getGreen() * (1 - factor) + (adder.getGreen() * factor),
                c1.getBlue() * (1 - factor) + (adder.getBlue() * factor)
                , 1

        );
    }


}
