package RayTracer18;

import RayTracer18.Light.Light;
import RayTracer18.Light.PointLight;
import RayTracer18.Material.Material;
import RayTracer18.Primitives.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;


import java.util.ArrayList;

public class Main extends Application {

    BorderPane borderPane;
    GridPane gridPane;

    Scene3D scene = new Scene3D();
    Canvas canvas = new Canvas(700, 350);
    Object3D lastSelected = null;




    public void addMouseScrolling(Node node) {
        node.setOnScroll((ScrollEvent event) -> {

            double deltaY = event.getDeltaY();
            if(deltaY > 0){
                scene.camera.setFov(scene.camera.getFov() + 0.05);
            }
            else{
                scene.camera.setFov(scene.camera.getFov() - 0.05);

            }
            Renderer.renderScene(scene, canvas);
        });
    }

    TreeItem<String> rootHierarchy = new TreeItem<>("Entities");
    TreeItem<String> rootObjects = new TreeItem<>("Objects");
    TreeItem<String> rootLights = new TreeItem<>("Lights");

    public void createHierarchy(){
        ArrayList<Object3D> objectList =new ArrayList<>(scene.getObjects());
        ArrayList<Light> lightList = new ArrayList<>(scene.getLights());

        rootObjects.getChildren().clear();
        rootLights.getChildren().clear();
        for (Object3D ob : objectList) {
            String name = ob.getName() + " id:" + ob.id;
            TreeItem<String> item = new TreeItem<>(name);

            rootObjects.getChildren().add(item);
        }
        for (Light l : lightList) {
            String name = l.getName() + "id:" + l.id;
            TreeItem<String> item = new TreeItem<>(name);

            rootLights.getChildren().add(item);
        }

    }

    private void customizeLights() {
        var lights = scene.getLights();
        var label = new Label();
        label.setText("Lights");
        gridPane.add(label, 4, 0);

        for (int i = 0; i < lights.size(); i++) {
            int currentLight = i;

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
            gridPane.add(slider, 5, i + 1);

            //Colorpicker
            var colorPicker = new ColorPicker();
            colorPicker.setValue(lights.get(i).color);
            gridPane.add(colorPicker, 3, i + 1);

            //Text field for changing x
            TextField numberFieldX = new TextField();
            numberFieldX.setText(String.valueOf(lights.get(i).position.getX()));

            numberFieldX.textProperty().addListener((obs,oldValue,newValue) -> {
                numberFieldX.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

                try {
                    numberFieldX.getTextFormatter().getValueConverter().fromString(newValue);
                    // no exception above means valid
                    numberFieldX.setBorder(null);
                    lights.get(currentLight).setPosition(new Vector3(Double.parseDouble(newValue),0,0));
                } catch (NumberFormatException e) {
                    numberFieldX.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
                }
            });
            gridPane.add(numberFieldX, 6, i + 1);

            //Text field for changing y
            TextField numberFieldY = new TextField();
            numberFieldY.setText(String.valueOf(lights.get(i).position.getY()));

            numberFieldY.textProperty().addListener((obs,oldValue,newValue) -> {
                numberFieldY.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

                try {
                    numberFieldY.getTextFormatter().getValueConverter().fromString(newValue);
                    // no exception above means valid
                    numberFieldY.setBorder(null);
                    lights.get(currentLight).setPosition(new Vector3(0,Double.parseDouble(newValue),0));
                } catch (NumberFormatException e) {
                    numberFieldY.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
                }
            });
            gridPane.add(numberFieldY, 7, i + 1);

            //Text field for changing z
            TextField numberFieldZ = new TextField();
            numberFieldZ.setText(String.valueOf(lights.get(i).position.getZ()));

            numberFieldZ.textProperty().addListener((obs,oldValue,newValue) -> {
                numberFieldZ.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

                try {
                    numberFieldZ.getTextFormatter().getValueConverter().fromString(newValue);
                    // no exception above means valid
                    numberFieldZ.setBorder(null);
                    lights.get(currentLight).setPosition(new Vector3(0,0,Double.parseDouble(newValue)));
                } catch (NumberFormatException e) {
                    numberFieldZ.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
                }
            });
            gridPane.add(numberFieldZ, 8, i + 1);

            //Sets intensity of current light
            slider.valueProperty().addListener((observable, oldValue, newValue) -> lights.get(currentLight).setIntensity((Double) newValue));
            //Sets color of current light
            colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> lights.get(currentLight).setColor(newValue));

        }

        //Creates button and applies light changes
        var button = new Button();
        button.setText("Apply");
        button.setOnAction(e -> {
            Renderer.renderScene(scene, canvas);
            createHierarchy();
        });
        gridPane.add(button, 4, 5);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
        borderPane = new BorderPane();
        gridPane = new GridPane();

        addMouseScrolling(canvas);

        primaryStage.setTitle("Ray tracer");


        Label label = new Label();
        label.setText(String.format("Field of View (FoV): %.1f ", Math.abs(.3 * 100 - 100)));

        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(1);
        slider.setValue(.3);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(.1);
        slider.setMinorTickCount(0);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            scene.camera.setFov(newValue.doubleValue());
            label.setText(String.format("Field of View (FoV): %.1f ", Math.abs((double) newValue * 100 - 100)));


            Renderer.renderScene(scene, canvas);

        });
        initRender(scene, canvas);
        createHierarchy();
        customizeLights();

        Button button = new Button();
        button.setText("Render");
        button.setOnAction(e -> {

            Vector3 d = new Vector3(1,-1,0);
            Vector3 n = new Vector3(0,1,0);
            Vector3 reflectionEquation = Vector3.multiply(d, n).multiply(2).multiply(n);
            Vector3 direction = Vector3.sub(d, reflectionEquation);
            System.out.println(direction);
            Renderer.renderScene(scene, canvas);


        });

        TreeView<String> tree = new TreeView<>(rootHierarchy);
        rootHierarchy.getChildren().add(rootObjects);
        rootHierarchy.getChildren().add(rootLights);
        tree.setShowRoot(false);
        tree.setMaxHeight(150);



        tree.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue,
                                Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String name = selectedItem.getValue();
                String id = name.substring(name.indexOf("id:")).substring(3).trim();
                Light selectedLight = scene.getLightById(id);
                Object3D selectedObject = scene.getObjectById(id);
                if(selectedLight != null){
                    //Do stuff with selected light
                    Label pos = new Label();
                    pos.setText(selectedLight.position.toString());
                    gridPane.add(pos, 3, 2);

                    Label namelabel = new Label();
                    namelabel.setText(selectedLight.name);

                }
                if(selectedObject != null){
                    //Do stuff with object
                    if(lastSelected != null){
                        lastSelected.restoreMaterial();
                    }
                    lastSelected = selectedObject;
                    Material pink = new Material(Color.PINK);
                    selectedObject.applyMaterial(pink);
                    Renderer.renderScene(scene, canvas);
                }


            }
        });

        gridPane.add(button, 1, 1);
        gridPane.add(label, 1, 0);
        gridPane.add(slider, 2, 0);
        gridPane.add(tree, 1, 2);

        GridPane mainc = new GridPane();
        mainc.getChildren().add(canvas);


        HBox statusbar = new HBox();
        borderPane.setTop(gridPane);
        borderPane.setCenter(mainc);
        borderPane.setBottom(statusbar);

        primaryStage.setScene(new Scene(borderPane, 600, 600));
        primaryStage.show();

    }


    public static void initRender(Scene3D scene, Canvas canvas) {
        Material blue = new Material(Color.BLUE);
        Material green = new Material(Color.GREEN);
        Material red = new Material(Color.RED);
        Material orange = new Material(Color.ORANGE);
        Material mirror = new Material(Color.GRAY);

        mirror.setReflection(0.4);

        Triangle t = new Triangle(
                new Vector3(1, 0, 5),
                new Vector3(-2, 0, 5),
                new Vector3(1, 6, 5),
                new Vector3(4, 0, 5));

        scene.add(t);
        t.applyMaterial(blue);


        //TODO: make this working correclty

        Plane p = new Plane(new Vector3(0, -0.5, 0), new Vector3(0, 1, 0));
        scene.add(p);
        p.applyMaterial(green);
        Plane p2 = new Plane(new Vector3(0, 10, 0), new Vector3(0, -1, 0));
        scene.add(p2);
        p2.applyMaterial(green);
        Plane p3 = new Plane(new Vector3(0, 0, 10), new Vector3(0, 0, -1));
        scene.add(p3);
        p3.applyMaterial(orange);

        Sphere s = new Sphere(new Vector3(2,1,2), 1);
        s.applyMaterial(mirror);

        Box b = new Box(new Vector3(-2,0,1.3), new Vector3(1,1,1));
        b.applyMaterial(red);

        scene.add(s);
        scene.add(b);

        PointLight l = new PointLight(new Vector3(2,0,0), 1f, Color.WHITE);
        scene.add(l);
        scene.camera.setProjectorSize(new Vector2(canvas.getWidth(), canvas.getHeight()));
    }


    public static void main(String[] args) {
        launch(args);
    }
}