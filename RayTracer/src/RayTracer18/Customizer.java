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
    double reflectivity = 0.00000;

    public void generateCustomizer(GridPane gridPane) {
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

        sliderScale.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (object.name.contains("Sphere")) {
                Sphere sphere = (Sphere) object;
                sphere.setScale((Double) newValue);
            }
            if (object.name.contains("Box")) {
                Box box = (Box) object;
                box.setScale((Double) newValue);
            }
        });
    }

    public void setNumberFieldObject(char axis, Object3D object) {
        if (axis == 'X')
            numberFieldX.setText(String.valueOf(object.position.getX()));
        if (axis == 'Y')
            numberFieldY.setText(String.valueOf(object.position.getY()));
        if (axis == 'Z')
            numberFieldZ.setText(String.valueOf(object.position.getZ()));
    }

    public void setNumberFieldLight(char axis, Light light) {
        if (axis == 'X')
            numberFieldX.setText(String.valueOf(light.position.getX()));
        if (axis == 'Y')
            numberFieldY.setText(String.valueOf(light.position.getY()));
        if (axis == 'Z')
            numberFieldZ.setText(String.valueOf(light.position.getZ()));
    }

    public void applyChangesObject(Object3D object) {
        //Object set position
        object.setPosition(new Vector3(Double.parseDouble(numberFieldX.getText()), Double.parseDouble(numberFieldY.getText()), Double.parseDouble(numberFieldZ.getText())));
        //Sets material from colorpicker
        RayTracer18.Material.Material material = new RayTracer18.Material.Material(colorPicker.getValue());
        material.setReflection(slider.getValue());
        object.applyMaterial(material);

    }

    public void applyChangesLight(Light light) {
        //Light set position
        light.setPosition(new Vector3(Double.parseDouble(numberFieldX.getText()), Double.parseDouble(numberFieldY.getText()), Double.parseDouble(numberFieldZ.getText())));
        //Sets intensity of current light
        light.setIntensity(slider.getValue());
        //Sets color of current light
        light.setColor(colorPicker.getValue());
    }
}


