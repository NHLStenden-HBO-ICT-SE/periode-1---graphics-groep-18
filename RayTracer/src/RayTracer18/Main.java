package RayTracer18;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    Button button;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Canvas canvas = new Canvas(300,300);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GOLD);
        primaryStage.setTitle("Ray tracer");
        button = new Button();
        button.setText("Zieke raytracer");
        button.setOnAction(e ->{
            System.out.println("huts");
        });


        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        Button b1 = new Button();
        b1.setOnAction(e->{

            //TEST FOR AN INTERSECTION
            Ray r1 = new Ray(new Vector3(0,5,0),new Vector3(0,-1,0));
            Triangle t = new Triangle(
                    new Vector3(-2,0,3),
                    new Vector3(-2,0,-3),
                    new Vector3(3,0,0)
            );
            System.out.println(t.intersect(r1).toString());

        });
        b1.setText("Render");
        Button b2 = new Button();
        b2.setText("hi2");
        gridPane.addRow(1,b1);
        gridPane.addRow(1, b2);

        GridPane mainc = new GridPane();
        mainc.getChildren().add(canvas);



        HBox statusbar = new HBox();
        Label l = new Label("Text");
        borderPane.setTop(gridPane);
        borderPane.setCenter(mainc);
        borderPane.setBottom(statusbar);
        borderPane.getChildren().add(button);

        primaryStage.setScene(new Scene(borderPane, 600, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}