package RayTracer18;

import RayTracer18.Lights.Light;
import RayTracer18.Lights.PointLight;
import RayTracer18.Material.Material;
import RayTracer18.Primitives.ObjLoader;
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

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    BorderPane borderPane;
    GridPane gridPane;
    GridPane rightPane = new GridPane();

    Scene3D scene = new Scene3D();
    Canvas canvas = new Canvas(900, 400);
    Customizer customizer = new Customizer();

    Label idLabel = new Label();
    Label coordsLabel = new Label();
    Button applyButton = new Button();
    public Renderer renderer = new Renderer();
    ArrayList<Object3D> selectedObjects = null;

    public static ProgressBar progressBar = new ProgressBar(0);
    private static String basePath = new File("").getAbsolutePath() + "/RayTracer";

    public void addMouseScrolling(Node node) {
        node.setOnScroll((ScrollEvent event) -> {

            double deltaY = event.getDeltaY();
            if (deltaY > 0) {
                scene.camera.setFov(scene.camera.getFov() + 0.05);
            } else {
                scene.camera.setFov(scene.camera.getFov() - 0.05);

            }
            renderer.reRender();
        });
    }

    public void applyChildren(Object3D object, TreeItem tree) {
        if (object instanceof ObjLoader) {
            try {
                Triangle[] triangles = ((ObjLoader) object).parseFile();
                for (Triangle triangle : triangles) {
                    String name = triangle.getName() + " id:" + triangle.id;
                    TreeItem<String> item = new TreeItem<>(name);
                    tree.getChildren().add(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    TreeItem<String> rootHierarchy = new TreeItem<>("Entities");
    TreeItem<String> rootObjects = new TreeItem<>("Objects");
    TreeItem<String> rootLights = new TreeItem<>("Lights");

    public void createHierarchy() {
        ArrayList<Object3D> objectList = new ArrayList<>(scene.getHiarcyObjects());
        ArrayList<Light> lightList = new ArrayList<>(scene.getLights());

        rootObjects.getChildren().clear();
        rootLights.getChildren().clear();
        for (Object3D ob : objectList) {
            String name = ob.getName() + " id:" + ob.id;
            TreeItem<String> item = new TreeItem<>(name);
            applyChildren(ob, item);
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


            renderer.reRender();

        });
        initScene(scene, canvas);
        createHierarchy();
//        customizeLights();

        Button renderButton = new Button();
        renderButton.setText("Render");
        renderButton.setOnAction(e -> {


            if (renderer.running) {
                renderer.reRender();
            } else {
                renderer.initRenderer(scene, canvas);
                renderer.start();
            }


        });


        TreeView<String> tree = new TreeView<>(rootHierarchy);
        rootHierarchy.getChildren().add(rootObjects);
        rootHierarchy.getChildren().add(rootLights);
        tree.setShowRoot(false);
        tree.setMaxHeight(150);


        rightPane.add(idLabel, 0, 1);
        rightPane.add(coordsLabel, 0, 2);
        applyButton.setText("Apply");
        applyButton.setVisible(false);
        rightPane.add(applyButton, 0, 22);


        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue,
                                Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String name = selectedItem.getValue();
                String id = name.substring(name.indexOf("id:")).substring(3).trim();
                Light selectedLight = scene.getLightById(id);
                if (name.contains("CUSTOM"))
                    selectedObjects = scene.getObjectListById(id);

                Object3D selectedObject = scene.getObjectById(id);

                idLabel.setText("Customizing: " + id);


                if (selectedLight != null) {
                    applyButton.setVisible(true);
                    customizer.sliderScale.setVisible(false);
                    customizer.labelScale.setVisible(false);
                    coordsLabel.setText("Coordinates : " + selectedLight.position.toString());
                    customizer.lightCustomizer(selectedLight);

                }
                if (selectedObject != null) {
                    applyButton.setVisible(true);
                    customizer.sliderScale.setVisible(true);
                    customizer.labelScale.setVisible(true);
                    coordsLabel.setText("Coordinates : " + selectedObject.position.toString());
                    customizer.objectCustomizer(selectedObject);
                }

                //Creates button and applies light changes
                applyButton.setOnAction(e -> {
                    if (name.contains("CUSTOM"))
                        for (Object3D selectedObjectFromObject : selectedObjects) {
                            if (selectedObjectFromObject != null) {
                                customizer.applyChangesObject(selectedObjectFromObject);
                            }
                        }
                    if (selectedObject != null) {
                        customizer.applyChangesObject(selectedObject);
                        coordsLabel.setText("Coordinates : " + selectedObject.position.toString());
                    } else if (selectedLight != null) {
                        customizer.applyChangesLight(selectedLight);
                        coordsLabel.setText("Coordinates : " + selectedLight.position.toString());
                    }

                    customizer.sliderScale.setValue(1f);
                    if (renderer.running) {
                        renderer.reRender();
                    } else {
                        renderer.initRenderer(scene, canvas);
                        renderer.start();
                    }
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

        primaryStage.setScene(new Scene(borderPane, 1500, 800));
        primaryStage.show();

    }


    public static void initScene(Scene3D scene, Canvas canvas) {
        Material blue = new Material(Color.BLUE);
        Material green = new Material(Color.GREEN);
        Material red = new Material(Color.RED);
        Material orange = new Material(Color.ORANGE);
        Material mirror = new Material(Color.GRAY);
        Material brick = new Material(Color.BLACK);
        Material checker = new Material(Color.PURPLE);
        checker.isChecker = true;


        try {
            brick.setColorMap(ImageIO.read(new File(basePath + "/src/Models/Textures/bricks.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Material objtex = new Material(Color.PINK);

        mirror.setReflection(1);

        Vector3 tp1 = new Vector3(-2, -0.5, 3);
        Vector3 tp2 = new Vector3(0, 2, 3);
        Vector3 tp3 = new Vector3(2, -0.5, 3);
        //Triangle uv positions
//        tp1.textureCords = new Vector2(0,1);
//        tp2.textureCords = new Vector2(0.5,0);
//        tp3.textureCords = new Vector2(1,1);
        Triangle t = new Triangle(
                Triangle.calculateCenter(tp1, tp2, tp3),
                tp1, tp2, tp3
        );
        scene.add(t);

        t.rotateY(125);
        t.rotateX(0);
        t.applyMaterial(orange);


        //TODO: Try catch for if not found
        ObjLoader objLoader = new ObjLoader(new Vector3(-2, 0, 4), new File(basePath + "/src/Models/rikuv.obj"), "Dominace asserting Rick Astley CUSTOM");
        try {

            objtex.setColorMap(ImageIO.read(new File(basePath + "/src/Models/Textures/rickastley_D2.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        objLoader.applyMaterial(objtex);
        scene.add(objLoader);

        Plane floor = new Plane(new Vector3(0, -0.5, 0), new Vector3(0, 1, 0));
        scene.add(floor);
        floor.applyMaterial(checker);
        Plane p2 = new Plane(new Vector3(0, 10, 0), new Vector3(0, -1, 0));
        scene.add(p2);
        p2.applyMaterial(green);
        Plane p3 = new Plane(new Vector3(0, 0, 10), new Vector3(0, 0, -1));
        scene.add(p3);
        p3.applyMaterial(orange);

        Sphere mirrorSphere = new Sphere(new Vector3(-2, 0.5, 2), 1);
        mirrorSphere.applyMaterial(mirror);


        Box box = new Box(new Vector3(-2, 0, 1.3), new Vector3(1, 1, 1));
        box.applyMaterial(red);
        //scene.add(box);
        scene.add(mirrorSphere);

        PointLight l = new PointLight(new Vector3(0, 2, 0.2), 8f, Color.WHITE);
        scene.add(l);
        PointLight l2 = new PointLight(new Vector3(0, 1, 1), 3f, Color.WHITE);
        scene.add(l2);
        scene.camera.setProjectorSize(new Vector2(canvas.getWidth(), canvas.getHeight()));
    }


    public static void main(String[] args) {
        launch(args);
    }
}