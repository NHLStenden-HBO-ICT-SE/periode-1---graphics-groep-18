package RayTracer18;

import RayTracer18.Lights.Light;
import RayTracer18.Lights.PointLight;
import RayTracer18.Material.Material;
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
    public static ProgressBar progressBar = new ProgressBar(0);
    public static ArrayList<ObjLoader> customObjects = new ArrayList<>();
    private static final String basePath = new File("").getAbsolutePath() + "/RayTracer";
    public Renderer renderer = new Renderer();
    BorderPane borderPane;
    GridPane gridPane;
    GridPane rightPane = new GridPane();
    Scene3D scene = new Scene3D();
    Canvas canvas = new Canvas(800, 400);
    Customizer customizer = new Customizer();
    Label idLabel = new Label();
    Label coordsLabel = new Label();
    Button applyButton = new Button();
    TreeItem<String> rootHierarchy = new TreeItem<>("Entities");
    TreeItem<String> rootObjects = new TreeItem<>("Objects");
    TreeItem<String> rootLights = new TreeItem<>("Lights");

    public static void initScene(Scene3D scene, Canvas canvas) {
        Material blue = new Material(Color.BLUE);
        Material green = new Material(Color.GREEN);
        Material red = new Material(Color.RED);
        Material orange = new Material(Color.ORANGE);
        Material mirror = new Material(Color.GRAY);
        mirror.setReflection(1);
        Material checker = new Material(Color.PURPLE);
        checker.isChecker = true;



        Plane background = new Plane(new Vector3(0, 0, 3), new Vector3(0, 0, -1));
        background.applyMaterial(orange);
        scene.add(background);


        //TODO: Try catch for if not found

//        //Rubic cube
//        {
//            Material objtex = new Material(Color.PINK);
//
//            ObjLoader objLoader = new ObjLoader(new Vector3(-2, 0, 4), new File(basePath + "/src/Models/FinalScene/rubic6.obj"), "Rubic Cube");
//            try {
//
//                objtex.setColorMap(ImageIO.read(new File(basePath + "/src/Models/Textures/r1.png")));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            objLoader.applyMaterial(objtex);
//            scene.add(objLoader);
//            objLoader.move(new Vector3(0, 0, 0.5));
//
//        }
//        //Rick Astley
//        {
//            Material objtex = new Material(Color.PINK);
//
//            ObjLoader objLoader = new ObjLoader(new Vector3(-2, 0, 4), new File(basePath + "/src/Models/FinalScene/rick.obj"), "Rick Astley");
//            try {
//
//                objtex.setColorMap(ImageIO.read(new File(basePath + "/src/Models/Textures/rickastley_D2.jpg")));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            objLoader.applyMaterial(objtex);
//            scene.add(objLoader);
//            objLoader.move(new Vector3(-0.3, 0, 0.5));
//        }
//        //Nhl Logo
//        {
//            Material objtex = new Material(Color.BLUE);
//
//            ObjLoader objLoader = new ObjLoader(new Vector3(0.5, 0.1, 0), new File(basePath + "/src/Models/FinalScene/nhl.obj"), "NHL Logo");
//            objLoader.applyMaterial(objtex);
//            scene.add(objLoader);
//            objLoader.move(new Vector3(-0.7, 0.5, 0.7));
//        }
//
//        //Banana
//        {
//            Material objtex = new Material(Color.PINK);
//
//            ObjLoader objLoader = new ObjLoader(new Vector3(-2, 0, 4), new File(basePath + "/src/Models/FinalScene/banana.obj"), "Bananas");
//            try {
//
//                objtex.setColorMap(ImageIO.read(new File(basePath + "/src/Models/Textures/banana.jpg")));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            objLoader.applyMaterial(objtex);
//            scene.add(objLoader);
//        }
//
////        //Dragon
//        {
//            Material objtex = new Material(Color.GREEN);
//
//            ObjLoader objLoader = new ObjLoader(new Vector3(-2, 2, 4), new File(basePath + "/src/Models/FinalScene/dragon.obj"), "Dragon");
//            objLoader.applyMaterial(objtex);
//            scene.add(objLoader);
//            objLoader.move(new Vector3(0, -0.06, 0.4));
//        }

        Sphere mirrorSphere = new Sphere(new Vector3(1, 0.6, 1.3), 0.5);
        mirrorSphere.applyMaterial(mirror);
        scene.add(mirrorSphere);


        Plane floor = new Plane(new Vector3(0, -0.5, 0), new Vector3(0, 1, 0));
        scene.add(floor);
        floor.applyMaterial(checker);
        Plane p2 = new Plane(new Vector3(0, 10, 0), new Vector3(0, -1, 0));
        scene.add(p2);
        p2.applyMaterial(green);
        Plane p3 = new Plane(new Vector3(0, 0, 10), new Vector3(0, 0, -1));
        scene.add(p3);
        p3.applyMaterial(orange);


        Box box = new Box(new Vector3(-2, 0, 1.3), new Vector3(1, 1, 1));
        box.applyMaterial(red);
        //scene.add(box);

        PointLight l = new PointLight(new Vector3(0, 0.2, 0.2), 1f, Color.ORANGE);
        scene.add(l);


        PointLight l2 = new PointLight(new Vector3(2, 0.2, 1.8), 1f, Color.BLUE);
        scene.add(l2);
        PointLight l3 = new PointLight(new Vector3(-2, 0.5, 0.7), 1f, Color.WHITE);
        scene.add(l3);
        scene.camera.setProjectorSize(new Vector2(canvas.getWidth(), canvas.getHeight()));
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void addMouseScrolling(Node node) {
        node.setOnScroll((ScrollEvent event) -> {

            double deltaY = event.getDeltaY();
            if (deltaY > 0) {
                scene.camera.setFov(scene.camera.getFov() + 0.05);
            } else {
                scene.camera.setFov(scene.camera.getFov() - 0.05);

            }
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



        });
        initScene(scene, canvas);
        createHierarchy();

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
              
                if (name.contains("id")) {
                    String id = name.substring(name.indexOf("id:")).substring(3).trim();
                    Light selectedLight = scene.getLightById(id);
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
                        if(selectedObject instanceof ObjLoader){
                            customizer.applyChangesCustomObject((ObjLoader) selectedObject);

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
}