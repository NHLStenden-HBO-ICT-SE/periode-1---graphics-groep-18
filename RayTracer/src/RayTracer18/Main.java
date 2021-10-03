package RayTracer18;


import RayTracer18.Light.PointLight;
import RayTracer18.Primitives.*;
import com.sun.source.tree.Tree;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    Button button;
    Label label;
    Slider slider;

    Scene3D scene = new Scene3D();
    Canvas canvas = new Canvas(600, 300);

    public void addMouseScrolling(Node node) {
        node.setOnScroll((ScrollEvent event) -> {
            // Adjust the zoom factor as per your requirement
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            scene.camera.zoomCamera(canvas, scene, deltaY);
        });
    }

    TreeItem<String> rootHierarchy = new TreeItem<String>("Entities");
    TreeItem<String> rootObjects = new TreeItem<String>("Objects");
    TreeItem<String> rootLights = new TreeItem<String>("Lights");

    public void createHierarchy(){
        ArrayList<String> objectList = new ArrayList<>();
        ArrayList<String> lightList = new ArrayList<>();
        objectList.add(scene.getObjects().toString());
        lightList.add(scene.getLights().toString());
        rootObjects.setExpanded(true);
        rootLights.setExpanded(true);
        for (int i = 0; i < objectList.size(); i++){
            TreeItem<String> item = new TreeItem<String>(objectList.get(i));
            rootObjects.getChildren().add(item);
        }
        for (int i = 0; i < lightList.size(); i++) {
            TreeItem<String> item = new TreeItem<String>(lightList.get(i));
            rootLights.getChildren().add(item);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        addMouseScrolling(canvas);

        primaryStage.setTitle("Ray tracer");


        label = new Label();
        label.setText(String.format("Field of View (FoV): %.1f ", Math.abs(.3 * 100 - 100)));

        slider = new Slider();
        slider.setMin(0);
        slider.setMax(1);
        slider.setValue(.3);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(.1);
        slider.setMinorTickCount(0);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scene.camera.setFov(newValue.doubleValue());
                label.setText(String.format("Field of View (FoV): %.1f ", Math.abs((double) newValue * 100 - 100)));


                Renderer.renderScene(scene, canvas);

            }
        });

        button = new Button();
        button.setText("Trace da rays");
        button.setOnAction(e -> {
            initRender(scene, canvas);

            createHierarchy();
        });


        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();

        TreeView<String> tree = new TreeView<>(rootHierarchy);
        rootHierarchy.getChildren().add(rootObjects);
        rootHierarchy.getChildren().add(rootLights);
        tree.setShowRoot(false);
        tree.setMaxHeight(150);

        gridPane.addRow(1, button);
        gridPane.addRow(2, label);
        gridPane.addRow(3, slider);
        gridPane.addRow(4, tree);

        GridPane mainc = new GridPane();
        mainc.getChildren().add(canvas);


        HBox statusbar = new HBox();
        Label l = new Label("Text");
        borderPane.setTop(gridPane);
        borderPane.setCenter(mainc);
        borderPane.setBottom(statusbar);

        primaryStage.setScene(new Scene(borderPane, 600, 600));
        primaryStage.show();

    }


    public static void initRender(Scene3D scene, Canvas canvas){
        Material blue = new Material(Color.BLUE);
        Material green = new Material(Color.GREEN);
        Material floorm = new Material(Color.ORANGE);



        /*Triangle t = new Triangle(
                new Vector3(0,0,2),
                new Vector3(-3, 0, 2),
                new Vector3(0, 6, 2),
                new Vector3(3, 0, 2));
        scene.add(t);
        t.applyMaterial(blue);


        //TODO: make this working correclty
            /*Plane p = new Plane(.2);
            scene.add(p);
            p.applyMaterial(green);*/

        Sphere s = new Sphere(new Vector3(2, 0.5, 3), 1);
        Box b = new Box(new Vector3(-4, -0.5, 3), new Vector3(2,2,2));

        s.applyMaterial(green);
        b.applyMaterial(blue);


        scene.add(s);
        scene.add(b);


        PointLight l = new PointLight(new Vector3(0,0,0), 1f, Color.WHITE);
        scene.add(l);
        scene.camera.setProjectorSize(new Vector2(canvas.getWidth(), canvas.getHeight()));
        new Renderer().renderScene(scene, canvas);
    }


    public static void main(String[] args) {
        launch(args);
    }
}