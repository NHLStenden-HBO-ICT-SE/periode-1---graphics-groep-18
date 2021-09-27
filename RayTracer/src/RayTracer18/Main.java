package RayTracer18;


import RayTracer18.Primitives.Triangle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    Button button;

    Scene3D scene = new Scene3D();


    @Override
    public void start(Stage primaryStage) throws Exception{
        Canvas canvas = new Canvas(600,200);

        primaryStage.setTitle("Ray tracer");
        button = new Button();
        button.setText("Trace da rays");
        button.setOnAction(e->{
            Triangle t = new Triangle(new Vector3(-3, 0, 4), new Vector3(0,6,4), new Vector3(3, 0,4));
            scene.add(t);
            scene.camera.setProjectorSize(new Vector2(canvas.getWidth(), canvas.getHeight()));
            new Renderer().renderScene(scene, canvas);
        });


        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        Button b1 = new Button();







        b1.setText("Render");
        b1.setOnAction(e->{
            Triangle t = new Triangle(new Vector3(-3, 0, 8), new Vector3(0, 6,8), new Vector3(3, 0,8));
            Vector3 worldPos = new Vector3(240/canvas.getWidth() * 2, 240/canvas.getHeight() * 2, 1);
            scene.camera.setProjectorSize(new Vector2(canvas.getWidth(), canvas.getHeight()));
            Ray r = new Ray(new Vector3(0,0,0), Vector3.sub(worldPos, new Vector3().clone()), this.scene);
            System.out.println(r.getDirection());

            System.out.println(r.shoot() == Color.GREEN);
            new Renderer().renderScene(scene, canvas);
        });
        Button b2 = new Button();
        b2.setText("hi2");
        gridPane.addRow(1,b1);
        gridPane.addRow(1, b2);
        gridPane.addRow(1, button);

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


    public static void main(String[] args) {
        launch(args);
    }
}