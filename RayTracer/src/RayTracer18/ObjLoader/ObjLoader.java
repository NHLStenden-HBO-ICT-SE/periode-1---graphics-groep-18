package RayTracer18.ObjLoader;


import RayTracer18.Primitives.Object3D;
import RayTracer18.Primitives.Triangle;
import RayTracer18.Vector3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ObjLoader extends Object3D {

    public File file;
    public Double scale;


    public ObjLoader(Vector3 pos, File file, Double scale) {
        super(pos);
        this.file = file;
        this.scale = scale;
        this.name = "CustomObject";
    }

    public static Vector3 parseVertex(String[] data) {
        return new Vector3(Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]));
    }

    public static Triangle parseFace(String[] data, ArrayList<Vector3> vertices) {
        return new Triangle(
                null,
                parseTriangleVertex(data[1].split("/"), vertices),
                parseTriangleVertex(data[2].split("/"), vertices),
                parseTriangleVertex(data[3].split("/"), vertices)


        );
    }
    public static Vector3 parseTriangleVertex(String[] data, ArrayList<Vector3> vertices) {
        Vector3 vertex = null;
        if (!data[0].isEmpty()) {
            int vertexIndex = Integer.parseInt(data[0]) - 1;
            vertex = vertices.get(vertexIndex);
        }
        return new Vector3(vertex);
    }

    public static Triangle[] parseFile(File file) throws Exception {
        BufferedReader input = new BufferedReader(new FileReader(file));

        ArrayList<Vector3> vertices = new ArrayList<>();

        ArrayList<Triangle> faces = new ArrayList<>();

        String line;
        while ((line = input.readLine()) != null) {
            String[] data = line.split(" ");
            switch (data[0]) {

                case "v":
                    vertices.add(parseVertex(data));
                    break;

                case "f":
                    faces.add(parseFace(data, vertices));
                    break;
            }
        }
        input.close();

        Triangle[] facesArray = new Triangle[faces.size()];
        facesArray = faces.toArray(facesArray);
        return facesArray;
    }
}