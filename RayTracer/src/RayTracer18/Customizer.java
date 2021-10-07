package RayTracer18;

import RayTracer18.Light.Light;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.converter.DoubleStringConverter;

import java.util.ArrayList;

public class Customizer {


    public void changeIntensity(ArrayList<Light> lights, GridPane gridPane, int currentLight) {
        //Intensity slider
        var slider = new Slider();
        slider.setValue(lights.get(0).intensity);
        slider.setMin(0f);
        slider.setMax(10f);
        slider.setValue(2f);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(2);
        slider.setMinorTickCount(1);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
        gridPane.add(slider, 5, currentLight + 1);

        //Sets intensity of current light
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            lights.get(currentLight).setIntensity((Double) newValue);
        });
    }

    public void changeColor(ArrayList<Light> lights, GridPane gridPane, int currentLight) {
        //Colorpicker
        var colorPicker = new ColorPicker();
        colorPicker.setValue(lights.get(currentLight).color);
        gridPane.add(colorPicker, 3, currentLight + 1);

        //Sets color of current light
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            lights.get(currentLight).setColor(newValue);
        });
    }


    public void changePosition(ArrayList<Light> lights, GridPane gridPane, int currentLight) {
        //Text field for changing x
        TextField numberFieldX = new TextField();
        numberFieldX.setText(String.valueOf(lights.get(currentLight).position.getX()));

        numberFieldX.textProperty().addListener((obs, oldValue, newValue) -> {
            numberFieldX.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

            try {
                numberFieldX.getTextFormatter().getValueConverter().fromString(newValue);
                // no exception above means valid
                numberFieldX.setBorder(null);
                lights.get(currentLight).setPosition(new Vector3(Double.parseDouble(newValue), 0, 0));
            } catch (NumberFormatException e) {
                numberFieldX.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
            }
        });
        gridPane.add(numberFieldX, 6, currentLight + 1);

        //Text field for changing y
        TextField numberFieldY = new TextField();
        numberFieldY.setText(String.valueOf(lights.get(currentLight).position.getY()));

        numberFieldY.textProperty().addListener((obs, oldValue, newValue) -> {
            numberFieldY.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

            try {
                numberFieldY.getTextFormatter().getValueConverter().fromString(newValue);
                // no exception above means valid
                numberFieldY.setBorder(null);
                lights.get(currentLight).setPosition(new Vector3(0, Double.parseDouble(newValue), 0));
            } catch (NumberFormatException e) {
                numberFieldY.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
            }
        });
        gridPane.add(numberFieldY, 7, currentLight + 1);

        //Text field for changing z
        TextField numberFieldZ = new TextField();
        numberFieldZ.setText(String.valueOf(lights.get(currentLight).position.getZ()));

        numberFieldZ.textProperty().addListener((obs, oldValue, newValue) -> {
            numberFieldZ.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

            try {
                numberFieldZ.getTextFormatter().getValueConverter().fromString(newValue);
                // no exception above means valid
                numberFieldZ.setBorder(null);
                lights.get(currentLight).setPosition(new Vector3(0, 0, Double.parseDouble(newValue)));
            } catch (NumberFormatException e) {
                numberFieldZ.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
            }
        });
        gridPane.add(numberFieldZ, 8, currentLight + 1);
    }
}
