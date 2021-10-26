package RayTracer18;

import RayTracer18.Light.Light;
import RayTracer18.Primitives.Box;
import RayTracer18.Primitives.Object3D;
import RayTracer18.Primitives.Sphere;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.converter.DoubleStringConverter;

public class Customizer {
    Label labelScale = new Label();

    Slider slider = new Slider();
    Slider sliderScale = new Slider();
    ColorPicker colorPicker = new ColorPicker();
    TextField numberFieldX = new TextField();
    TextField numberFieldY = new TextField();
    TextField numberFieldZ = new TextField();
    TextField axisField = numberFieldX;

    public void generateCustomizer(GridPane gridPane){
        gridPane.add(slider, 0, 4);
        Label labelColorPicker = new Label();
        labelColorPicker.setText("Color: ");
        gridPane.add(labelColorPicker, 0, 5);
        gridPane.add(colorPicker, 0, 6);

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

        //Sets the numberFields for XYZ
        setNumberFieldLight('X', light);
        setNumberFieldLight('Y', light);
        setNumberFieldLight('Z', light);

        //Sets intensity of current light
        slider.valueProperty().addListener((observable, oldValue, newValue) -> light.setIntensity((Double) newValue));
        //Sets color of current light
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> light.setColor(newValue));
    }

    public void objectCustomizer(Object3D object) {
        //TODO: fix the recalling of applyMaterial of selecting another object by changing the value of the fields
        //Colorpicker
        colorPicker.setValue(object.getMaterial().getColorAt());

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

        //Sets the numberFields for XYZ
        setNumberFieldObject('X', object);
        setNumberFieldObject('Y', object);
        setNumberFieldObject('Z', object);

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

    public void setNumberFieldObject(char axis, Object3D object){
        if(axis == 'X')
            axisField = numberFieldX;
        if(axis == 'Y')
            axisField = numberFieldY;
        if(axis == 'Z')
            axisField = numberFieldZ;

        //Text field for changing an axis
        axisField.setText(String.valueOf(object.position.getX()));

        axisField.textProperty().addListener((obs, oldValue, newValue) -> {
            axisField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

            try {
                axisField.getTextFormatter().getValueConverter().fromString(newValue);
                // no exception above means valid
                axisField.setBorder(null);
                object.setPosition(new Vector3(Double.parseDouble(newValue), 0, 0));
            } catch (NumberFormatException e) {
                axisField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
            }
        });
    }

    public void setNumberFieldLight(char axis, Light light){
        if(axis == 'X')
            axisField = numberFieldX;
        if(axis == 'Y')
            axisField = numberFieldY;
        if(axis == 'Z')
            axisField = numberFieldZ;

        //Text field for changing an axis
        axisField.setText(String.valueOf(light.position.getX()));

        axisField.textProperty().addListener((obs, oldValue, newValue) -> {
            axisField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

            try {
                axisField.getTextFormatter().getValueConverter().fromString(newValue);
                // no exception above means valid
                axisField.setBorder(null);
                light.setPosition(new Vector3(Double.parseDouble(newValue), 0, 0));
            } catch (NumberFormatException e) {
                axisField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
            }
        });
    }
}


