package RayTracer18;

import RayTracer18.Light.Light;
import RayTracer18.Light.PointLight;
import RayTracer18.Material.Material;
import RayTracer18.ObjLoader.ObjLoader;
import RayTracer18.Primitives.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {

    BorderPane borderPane;
    GridPane gridPane;
    GridPane rightPane = new GridPane();

    Scene3D scene = new Scene3D();
    Canvas canvas = new Canvas(1200, 600);
    Object3D lastSelected = null;
    Customizer customizer = new Customizer();

    Label idLabel = new Label();
    Label coordsLabel = new Label();
    Button applyButton = new Button();
    public static ProgressBar progressBar = new ProgressBar(0);


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
//        customizeLights();

        Button renderButton = new Button();
        renderButton.setText("Render");
        renderButton.setOnAction(e -> {



            Renderer.renderScene(scene, canvas);
            System.out.println(Renderer.storage.get("950_220"));


        });


        TreeView<String> tree = new TreeView<>(rootHierarchy);
        rootHierarchy.getChildren().add(rootObjects);
        rootHierarchy.getChildren().add(rootLights);
        tree.setShowRoot(false);
        tree.setMaxHeight(150);

        Label sliderLabel = new Label();

        rightPane.add(idLabel, 0, 1);
        rightPane.add(coordsLabel, 0, 2);
        rightPane.add(sliderLabel, 0, 3);
        applyButton.setText("Apply");
        rightPane.add(applyButton, 0, 15);


        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue,
                                Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String name = selectedItem.getValue();
                String id = name.substring(name.indexOf("id:")).substring(3).trim();
                Light selectedLight = scene.getLightById(id);
                Object3D selectedObject = scene.getObjectById(id);

                idLabel.setText("Customizing: " + id);


                if (selectedLight != null) {
                    customizer.sliderScale.setVisible(false);
                    customizer.labelScale.setVisible(false);
                    //Do stuff with selected light
                    customizer.lightCustomizer(selectedLight);
                    coordsLabel.setText("Coordinates : " + selectedLight.position.toString());
                    sliderLabel.setText("Light intensity");
                }
                if (selectedObject != null) {
                    customizer.sliderScale.setVisible(true);
                    customizer.labelScale.setVisible(true);
                    //Do stuff with object
                    customizer.objectCustomizer(selectedObject);
                    if (lastSelected != null) {
//                        lastSelected.restoreMaterial();
                    }
                    //TODO: fix the pink selection feature, so the colorpicker also works
                    lastSelected = selectedObject;
//                    Material pink = new Material(Color.PINK);
//                    selectedObject.applyMaterial(pink);
                    coordsLabel.setText("Coordinates : " + selectedObject.position.toString());
                    sliderLabel.setText("Object reflectivity");
                    Renderer.renderScene(scene, canvas);
                }

                //Creates button and applies light changes
                applyButton.setOnAction(e -> {
                    if (selectedObject != null)
                        coordsLabel.setText("Coordinates : " + selectedObject.position.toString());

                    else if (selectedLight != null)
                        coordsLabel.setText("Coordinates : " + selectedLight.position.toString());

                    customizer.sliderScale.setValue(1f);
                    Renderer.renderScene(scene, canvas);
                    createHierarchy();
                });


            }
        });

        gridPane.add(progressBar, 0, 0);
        gridPane.add(renderButton, 0, 1);
        gridPane.add(label, 1, 1);
        gridPane.add(slider, 2, 1);
        tree.setMinWidth(600);
        rightPane.add(tree, 0, 0);

        customizer.generateCustomizer(rightPane);

        GridPane mainc = new GridPane();
        mainc.getChildren().add(canvas);


        HBox statusbar = new HBox();
        borderPane.setTop(gridPane);
        borderPane.setCenter(mainc);
        borderPane.setRight(rightPane);
        borderPane.setBottom(statusbar);

        primaryStage.setScene(new Scene(borderPane, 1200, 800));
        primaryStage.show();

    }







    public static void initRender(Scene3D scene, Canvas canvas) {
        Material blue = new Material(Color.BLUE);
        Material green = new Material(Color.GREEN);
        Material red = new Material(Color.RED);
        Material orange = new Material(Color.ORANGE);
        Material mirror = new Material(Color.GRAY);

        mirror.setReflection(1);

        Triangle t = new Triangle(
                new Vector3(1, 0, 5),
                new Vector3(-2, -0.5, 3),
                new Vector3(1, 6, 5),
                new Vector3(4, -0.5, 5));

        //scene.add(t);
        t.applyMaterial(blue);


        //TODO: make this working correclty

        ObjLoader objLoader = new ObjLoader(new Vector3(-2,0,4), new File(System.getProperty("user.dir") + "/RayTracer/src/Models/draak.obj"), 1.0);
        objLoader.applyMaterial(green);
        scene.add(objLoader);

        Plane p = new Plane(new Vector3(0, -0.5, 0), new Vector3(0, 1, 0));
        scene.add(p);
        p.applyMaterial(green);
        Plane p2 = new Plane(new Vector3(0, 10, 0), new Vector3(0, -1, 0));
        scene.add(p2);
        p2.applyMaterial(green);
        Plane p3 = new Plane(new Vector3(0, 0, 10), new Vector3(0, 0, -1));
        scene.add(p3);
        p3.applyMaterial(orange);

        Sphere mirrorSphere = new Sphere(new Vector3(-2,0.5,2), 1);
        mirrorSphere.applyMaterial(mirror);








        Box box = new Box(new Vector3(-2,0,1.3), new Vector3(1,1,1));
        box.applyMaterial(red);
        scene.add(box);
        scene.add(mirrorSphere);

        PointLight l = new PointLight(new Vector3(0,2,4), 8f, Color.ORANGE);
        scene.add(l);
        PointLight l2 = new PointLight(new Vector3(0,1,1), 3f, Color.BLUE);
        scene.add(l2);
        scene.camera.setProjectorSize(new Vector2(canvas.getWidth(), canvas.getHeight()));
    }


    public static void main(String[] args) {
        launch(args);
    }
}