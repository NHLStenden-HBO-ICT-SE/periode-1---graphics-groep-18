package RayTracer18;

import RayTracer18.Light.Light;
import RayTracer18.Primitives.Box;
import RayTracer18.Primitives.Object3D;
import RayTracer18.Primitives.Sphere;
import RayTracer18.Primitives.Triangle;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Customizer {
    Label labelScale = new Label();
    Label labelSlider = new Label();

    Label rotationLabel = new Label();

    Label labelRotateX = new Label();
    Label labelRotateY = new Label();
    Label labelRotateZ = new Label();
    Label labelColorPicker = new Label();
    Label labelX = new Label();
    Label labelY = new Label();
    Label labelZ = new Label();

    Slider slider = new Slider();
    Slider sliderScale = new Slider();
    Slider sliderRotateX = new Slider();
    Slider sliderRotateY = new Slider();
    Slider sliderRotateZ = new Slider();

    ColorPicker colorPicker = new ColorPicker();

    TextField numberFieldX = new TextField();
    TextField numberFieldY = new TextField();
    TextField numberFieldZ = new TextField();

    public void generateCustomizer(GridPane gridPane) {
        gridPane.add(rotationLabel, 0, 3);
        gridPane.add(labelSlider, 0, 4);
        gridPane.add(slider, 0, 5);
        labelColorPicker.setText("Color: ");
        gridPane.add(labelColorPicker, 0, 6);
        gridPane.add(colorPicker, 0, 7);

        labelScale.setText("Scale: ");
        gridPane.add(labelScale, 0, 8);
        gridPane.add(sliderScale, 0, 9);

        labelX.setText("X: ");
        gridPane.add(labelX, 0, 10);
        gridPane.add(numberFieldX, 0, 11);
        labelY.setText("Y: ");
        gridPane.add(labelY, 0, 12);
        gridPane.add(numberFieldY, 0, 13);
        labelZ.setText("Z: ");
        gridPane.add(labelZ, 0, 14);
        gridPane.add(numberFieldZ, 0, 15);

        //Creates all rotation labels and sliders
        labelRotateX.setText("Rotate X: 0.00");
        gridPane.add(labelRotateX, 0, 16);
        gridPane.add(sliderRotateX, 0, 17);
        labelRotateY.setText("Rotate Y: 0.00");
        gridPane.add(labelRotateY, 0, 18);
        gridPane.add(sliderRotateY, 0, 19);
        labelRotateZ.setText("Rotate Z: 0.00");
        gridPane.add(labelRotateZ, 0, 20);
        gridPane.add(sliderRotateZ, 0, 21);


        //Sets all slider properties
        sliderRotateX.setMin(-360f);
        sliderRotateX.setMax(360f);
        sliderRotateX.setBlockIncrement(1);
        sliderRotateX.setMajorTickUnit(20);
        sliderRotateX.setMinorTickCount(10);
        sliderRotateX.setShowTickLabels(true);

        sliderRotateY.setMin(-360f);
        sliderRotateY.setMax(360f);
        sliderRotateY.setBlockIncrement(1);
        sliderRotateY.setMajorTickUnit(20);
        sliderRotateY.setMinorTickCount(10);
        sliderRotateY.setShowTickLabels(true);

        sliderRotateZ.setMin(-360f);
        sliderRotateZ.setMax(360f);
        sliderRotateZ.setBlockIncrement(1);
        sliderRotateZ.setMajorTickUnit(20);
        sliderRotateZ.setMinorTickCount(10);
        sliderRotateZ.setShowTickLabels(true);


        //Disables all fields and labels on startup
        labelRotateX.setVisible(false);
        labelRotateY.setVisible(false);
        labelRotateZ.setVisible(false);
        labelColorPicker.setVisible(false);
        labelX.setVisible(false);
        labelY.setVisible(false);
        labelZ.setVisible(false);
        labelScale.setVisible(false);

        sliderRotateX.setVisible(false);
        sliderRotateY.setVisible(false);
        sliderRotateZ.setVisible(false);
        slider.setVisible(false);
        sliderScale.setVisible(false);
        colorPicker.setVisible(false);
        numberFieldX.setVisible(false);
        numberFieldY.setVisible(false);
        numberFieldZ.setVisible(false);
    }

    public void lightCustomizer(Light light) {
        labelSlider.setText("Light intensity: " + light.intensity);

        //Disables the unused fields
        labelRotateX.setVisible(false);
        labelRotateY.setVisible(false);
        labelRotateZ.setVisible(false);
        labelScale.setVisible(false);

        sliderRotateX.setVisible(false);
        sliderRotateY.setVisible(false);
        sliderRotateZ.setVisible(false);


        //Enables the used fields
        labelScale.setVisible(true);
        labelSlider.setVisible(true);
        labelColorPicker.setVisible(true);

        slider.setVisible(true);
        colorPicker.setVisible(true);

        labelX.setVisible(true);
        labelY.setVisible(true);
        labelZ.setVisible(true);
        numberFieldX.setVisible(true);
        numberFieldY.setVisible(true);
        numberFieldZ.setVisible(true);


        //Intensity slider
        slider.setValue(light.intensity);
        slider.setMin(0f);
        slider.setMax(10f);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(2);
        slider.setMinorTickCount(1);
        slider.setShowTickLabels(true);

        //Colorpicker
        colorPicker.setValue(light.color);

        //Sets the numberFields for XYZ
        numberFieldX.setText(String.valueOf(light.position.getX()));
        numberFieldY.setText(String.valueOf(light.position.getY()));
        numberFieldZ.setText(String.valueOf(light.position.getZ()));

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelSlider.setText("Light intensity: " + String.format("%.2f", newValue));
        });
    }

    public void objectCustomizer(Object3D object) {
        labelSlider.setText("Object reflectivity: " + object.getMaterial().getReflection());

        //Disables the unused fields
        labelRotateX.setVisible(false);
        labelRotateY.setVisible(false);
        labelRotateZ.setVisible(false);
        sliderRotateX.setVisible(false);
        sliderRotateY.setVisible(false);
        sliderRotateZ.setVisible(false);

        //Enables the used fields
        labelColorPicker.setVisible(true);

        colorPicker.setVisible(true);
        slider.setVisible(true);
        labelX.setVisible(true);
        labelY.setVisible(true);
        labelZ.setVisible(true);
        numberFieldX.setVisible(true);
        numberFieldY.setVisible(true);
        numberFieldZ.setVisible(true);

        if (object.name.contains("Triangle")) {
            //Disables/Enables the unused fields
            labelRotateX.setVisible(true);
            labelRotateY.setVisible(true);
            labelRotateZ.setVisible(true);
            sliderRotateX.setVisible(true);
            sliderRotateY.setVisible(true);
            sliderRotateZ.setVisible(true);

            //Updates rotation labels in real time
            sliderRotateX.valueProperty().addListener((observable, oldValue, newValue) -> {
                labelRotateX.setText("Rotate X: " + String.format("%.2f", newValue));
            });
            sliderRotateY.valueProperty().addListener((observable, oldValue, newValue) -> {
                labelRotateY.setText("Rotate Y: " + String.format("%.2f", newValue));
            });
            sliderRotateZ.valueProperty().addListener((observable, oldValue, newValue) -> {
                labelRotateZ.setText("Rotate Z: " + String.format("%.2f", newValue));
            });
        }

        //Colorpicker
        colorPicker.setValue(object.getMaterial().getColorAt());

        //Reflectivity slider
        slider.setValue(object.getMaterial().getReflection());
        slider.setMin(0f);
        slider.setMax(1f);
        slider.setBlockIncrement(.1);
        slider.setMajorTickUnit(.2);
        slider.setShowTickLabels(true);

        //Scale
        sliderScale.setValue(1f);
        sliderScale.setMin(0f);
        sliderScale.setMax(10f);
        sliderScale.setBlockIncrement(.5);
        sliderScale.setMajorTickUnit(1);
        sliderScale.setShowTickLabels(true);

        //Sets the numberFields for XYZ
        numberFieldX.setText(String.valueOf(object.position.getX()));
        numberFieldY.setText(String.valueOf(object.position.getY()));
        numberFieldZ.setText(String.valueOf(object.position.getZ()));

        //Updates reflectivity label in real time
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelSlider.setText("Object reflectivity: " + String.format("%.2f", newValue));
        });

        //Updates scale label in real time
        sliderScale.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelScale.setText("Scale: " + String.format("%.2f", newValue));
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

    public void applyChangesObject(Object3D object) {
        //Object set position
        object.setPosition(new Vector3(Double.parseDouble(numberFieldX.getText()), Double.parseDouble(numberFieldY.getText()), Double.parseDouble(numberFieldZ.getText())));
        //Sets material from colorpicker
        RayTracer18.Material.Material material = new RayTracer18.Material.Material(colorPicker.getValue());
        material.setReflection(slider.getValue());
        object.applyMaterial(material);

        //Sets the rotation
        if (object.name.contains("Triangle")) {
            Triangle triangle = (Triangle) object;
            triangle.rotateX(sliderRotateX.getValue());
            triangle.rotateY(sliderRotateY.getValue());
            triangle.rotateZ(sliderRotateZ.getValue());

            rotationLabel.setText("Rotation : x=" + String.format("%.2f", sliderRotateX.getValue()) + ", y=" + String.format("%.2f", sliderRotateY.getValue()) + ", z=" + String.format("%.2f", sliderRotateZ.getValue()));
            sliderRotateX.setValue(0.00);
            sliderRotateY.setValue(0.00);
            sliderRotateZ.setValue(0.00);
        }
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


