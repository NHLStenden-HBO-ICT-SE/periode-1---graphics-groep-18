package RayTracer18;

import RayTracer18.Light.Light;
import RayTracer18.Primitives.Box;
import RayTracer18.Primitives.Object3D;
import RayTracer18.Primitives.Sphere;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.util.converter.DoubleStringConverter;

import java.util.ArrayList;

public class Customizer {

    Slider slider = new Slider();
    Slider sliderScale = new Slider();
    ColorPicker colorPicker = new ColorPicker();
    TextField numberFieldX = new TextField();
    TextField numberFieldY = new TextField();
    TextField numberFieldZ = new TextField();

    public void generateCustomizer(GridPane gridPane){
        gridPane.add(slider, 0, 4);
        Label labelColorPicker = new Label();
        labelColorPicker.setText("Color: ");
        gridPane.add(labelColorPicker, 0, 5);
        gridPane.add(colorPicker, 0, 6);

        Label labelScale = new Label();
        labelScale.setText("Scale: ");
        gridPane.add(labelScale, 0, 7);
        gridPane.add(sliderScale, 0, 8);

        Label labelX = new Label();
        labelX.setText("X: ");
        gridPane.add(labelX, 0, 9);
        gridPane.add(numberFieldX, 0, 10);
        Label labelY = new Label();
        labelY.setText("Y: ");
        gridPane.add(labelY, 0, 11);
        gridPane.add(numberFieldY, 0, 12);
        Label labelZ = new Label();
        labelZ.setText("Z: ");
        gridPane.add(labelZ, 0, 13);
        gridPane.add(numberFieldZ, 0, 14);


    }

    public void lightCustomizer(Light light) {
        //Intensity slider
        slider.setValue(light.intensity);
        slider.setMin(0f);
        slider.setMax(10f);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(2);
        slider.setMinorTickCount(1);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);

        //Colorpicker
        colorPicker.setValue(light.color);


        //Text field for changing x
        numberFieldX.setText(String.valueOf(light.position.getX()));

        numberFieldX.textProperty().addListener((obs, oldValue, newValue) -> {
            numberFieldX.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

            try {
                numberFieldX.getTextFormatter().getValueConverter().fromString(newValue);
                // no exception above means valid
                numberFieldX.setBorder(null);
                light.setPosition(new Vector3(Double.parseDouble(newValue), 0, 0));
            } catch (NumberFormatException e) {
                numberFieldX.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
            }
        });

        //Text field for changing y
        numberFieldY.setText(String.valueOf(light.position.getY()));

        numberFieldY.textProperty().addListener((obs, oldValue, newValue) -> {
            numberFieldY.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

            try {
                numberFieldY.getTextFormatter().getValueConverter().fromString(newValue);
                // no exception above means valid
                numberFieldY.setBorder(null);
                light.setPosition(new Vector3(0, Double.parseDouble(newValue), 0));
            } catch (NumberFormatException e) {
                numberFieldY.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
            }
        });

        //Text field for changing z
        numberFieldZ.setText(String.valueOf(light.position.getZ()));

        numberFieldZ.textProperty().addListener((obs, oldValue, newValue) -> {
            numberFieldZ.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

            try {
                numberFieldZ.getTextFormatter().getValueConverter().fromString(newValue);
                // no exception above means valid
                numberFieldZ.setBorder(null);
                light.setPosition(new Vector3(0, 0, Double.parseDouble(newValue)));
            } catch (NumberFormatException e) {
                numberFieldZ.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
            }
        });

        //Sets intensity of current light
        slider.valueProperty().addListener((observable, oldValue, newValue) -> light.setIntensity((Double) newValue));
        //Sets color of current light
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> light.setColor(newValue));
    }

    public void objectCustomizer(Object3D object) {
        //TODO: fix the recalling of applyMaterial of selecting another object by changing the value of the fields
        //Colorpicker
        colorPicker.setValue(object.getMaterial().getColor());

        //Reflectivity slider
        slider.setValue(object.getMaterial().getReflection());
        slider.setMin(0f);
        slider.setMax(1f);
        slider.setBlockIncrement(.1);
        slider.setMajorTickUnit(.2);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);

        //Scale
        sliderScale.setValue(1f);
        sliderScale.setMin(0f);
        sliderScale.setMax(10f);
        sliderScale.setBlockIncrement(.5);
        sliderScale.setMajorTickUnit(1);
        sliderScale.setShowTickLabels(true);
        sliderScale.setSnapToTicks(true);

        //Text field for changing x
        numberFieldX.setText(String.valueOf(object.position.getX()));

        numberFieldX.textProperty().addListener((obs, oldValue, newValue) -> {
            numberFieldX.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

            try {
                numberFieldX.getTextFormatter().getValueConverter().fromString(newValue);
                // no exception above means valid
                numberFieldX.setBorder(null);
                object.setPosition(new Vector3(Double.parseDouble(newValue), 0, 0));
            } catch (NumberFormatException e) {
                numberFieldX.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
            }
        });

        //Text field for changing y
        numberFieldY.setText(String.valueOf(object.position.getY()));

        numberFieldY.textProperty().addListener((obs, oldValue, newValue) -> {
            numberFieldY.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

            try {
                numberFieldY.getTextFormatter().getValueConverter().fromString(newValue);
                // no exception above means valid
                numberFieldY.setBorder(null);
                object.setPosition(new Vector3(0, Double.parseDouble(newValue), 0));
            } catch (NumberFormatException e) {
                numberFieldY.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
            }
        });

        //Text field for changing z
        numberFieldZ.setText(String.valueOf(object.position.getZ()));

        numberFieldZ.textProperty().addListener((obs, oldValue, newValue) -> {
            numberFieldZ.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

            try {
                numberFieldZ.getTextFormatter().getValueConverter().fromString(newValue);
                // no exception above means valid
                numberFieldZ.setBorder(null);
                object.setPosition(new Vector3(0, 0, Double.parseDouble(newValue)));
            } catch (NumberFormatException e) {
                numberFieldZ.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
            }
        });

        final double[] reflectivity = {0.00000};
        //Sets reflectivity of current object
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            reflectivity[0] = (double) newValue;
        });

        sliderScale.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(object.name.contains("Sphere")) {
                Sphere sphere = (Sphere) object;
                sphere.setScale((Double) newValue);
            }
            if(object.name.contains("Box")) {
                Box box = (Box) object;
                box.setScale((Double) newValue);
            }
        });

        //Sets color of current object
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            RayTracer18.Material.Material material = new RayTracer18.Material.Material(newValue);

            material.setReflection(reflectivity[0]);
            object.applyMaterial(material);
        });

    }
}
