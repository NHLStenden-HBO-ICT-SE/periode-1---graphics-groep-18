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
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.util.ArrayList;

public class Main extends Application {

    BorderPane borderPane;
    GridPane gridPane;

    Scene3D scene = new Scene3D();
    Canvas canvas = new Canvas(700, 350);

    public void addMouseScrolling(Node node) {
        node.setOnScroll((ScrollEvent event) -> {

            double deltaY = event.getDeltaY();
            if (deltaY > 0) {
                scene.camera.setFov(scene.camera.getFov() + 0.05);
            } else {
                scene.camera.setFov(scene.camera.getFov() - 0.05);

            }
            Renderer.renderScene(scene, canvas);
        });
    }

    TreeItem<String> rootHierarchy = new TreeItem<String>("Entities");
    TreeItem<String> rootObjects = new TreeItem<String>("Objects");
    TreeItem<String> rootLights = new TreeItem<String>("Lights");

    public void createHierarchy() {
        ArrayList<Object3D> objectList = new ArrayList<>(scene.getObjects());
        ArrayList<Light> lightList = new ArrayList<>(scene.getLights());

        rootObjects.getChildren().clear();
        rootLights.getChildren().clear();
        for (int i = 0; i < objectList.size(); i++) {
            TreeItem<String> item = new TreeItem<>(objectList.get(i).getName());
            rootObjects.getChildren().add(item);
        }
        for (int i = 0; i < lightList.size(); i++) {
            TreeItem<String> item = new TreeItem<>(lightList.get(i).getName());
            rootLights.getChildren().add(item);
        }

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

        //region Customizer

        //Calls customize lights
        var lightLabel = new Label();
        lightLabel.setText("Lights");
        gridPane.add(lightLabel, 4, 0);
        Customizer customizer = new Customizer();
        for (int i = 0; i < scene.getLights().size(); i++) {
            customizer.changeIntensity(scene.getLights(), gridPane, i);
            customizer.changeColor(scene.getLights(), gridPane, i);
            customizer.changePosition(scene.getLights(), gridPane, i);
        }

        //Creates button and applies light changes
        var applyButton = new Button();
        applyButton.setText("Apply");
        applyButton.setOnAction(e -> {
            Renderer.renderScene(scene, canvas);
            createHierarchy();
        });
        gridPane.add(applyButton, 4, 5);

        //endregion

        //Render button
        Button renderButton = new Button();
        renderButton.setText("Render");
        renderButton.setOnAction(e -> {

            Vector3 d = new Vector3(1, -1, 0);
            Vector3 n = new Vector3(0, 1, 0);
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

        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue,
                                Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                System.out.println("Selected Text : " + selectedItem.getValue());
                System.out.println(selectedItem.valueProperty());
                // do what ever you want
            }
        });

        gridPane.add(renderButton, 1, 1);
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
        Material floorm = new Material(Color.ORANGE);
        Material red = new Material(Color.RED);
        Material mirror = new Material(Color.WHITE);
        Material orange = new Material(Color.ORANGE);
        mirror.setReflection(1);

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
        p.applyMaterial(mirror);
        Plane p2 = new Plane(new Vector3(0, 10, 0), new Vector3(0, -1, 0));
        scene.add(p2);
        p2.applyMaterial(green);
        Plane p3 = new Plane(new Vector3(0, 0, 10), new Vector3(0, 0, -1));
        scene.add(p3);
        p3.applyMaterial(green);

        Sphere s = new Sphere(new Vector3(2, 0.5, 2), 1);
        s.applyMaterial(blue);

        Box b = new Box(new Vector3(-2, 0, 1.3), new Vector3(1, 1, 1));
        b.applyMaterial(red);

        scene.add(s);
        scene.add(b);

        PointLight l = new PointLight(new Vector3(2, 0, 0), 1f, Color.WHITE);
        scene.add(l);
        scene.camera.setProjectorSize(new Vector2(canvas.getWidth(), canvas.getHeight()));
    }


    public static void main(String[] args) {
        launch(args);
    }
}