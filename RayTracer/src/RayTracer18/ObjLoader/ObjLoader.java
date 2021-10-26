package RayTracer18.ObjLoader;


import RayTracer18.Primitives.Object3D;
import RayTracer18.Primitives.Triangle;
import RayTracer18.Vector2;
import RayTracer18.Vector3;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class ObjLoader extends Object3D {

    public File file;
    public Double scale;
    private ArrayList<Vector2> textureCords = new ArrayList<>();
    private ArrayList<Vector3> vertices = new ArrayList<>();
    private ArrayList<Triangle> faces = new ArrayList<>();
    private ArrayList<Vector3> normals = new ArrayList<>();

    public ObjLoader(Vector3 pos, File file, Double scale) {
        super(pos);
        this.file = file;
        this.scale = scale;
        this.name = "CustomObject";
    }

    public static Vector3 parseVertex(String[] data) {
        //returns the vertex of a given triangle by the data in the string
        return new Vector3(Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]));
    }

    public static Vector2 parseTextureCord(String[] data){
        return new Vector2(Double.parseDouble(data[1]), Double.parseDouble(data[2])) ;
    }

    public ArrayList<Triangle> parseFace(String[] data) {
        //returns the triangle out of the given vertices
        ArrayList<Triangle> triangles = new ArrayList<>();
        Triangle t = new Triangle(
                null,
                parseTriangleVertex(data[1].split("/")).add(new Vector3(0,-3,2)),
                parseTriangleVertex(data[2].split("/")).add(new Vector3(0,-3,2)),
                parseTriangleVertex(data[3].split("/")).add(new Vector3(0,-3,2))
        );
        if(t.p1.getNormal() != null && t.p2.getNormal() != null && t.p3.getNormal() != null){
            t.hasVertexNormals = true;
        }

        triangles.add(t);
        //checks if the data is a quad instead of a triangle, if so it will split the quad and add two triangles
        if (data.length == 5){
            System.out.println("Quad");
            Triangle t2 = new Triangle(
                    null,
                    parseTriangleVertex(data[1].split("/")).add(new Vector3(0,-3,2)),
                    parseTriangleVertex(data[3].split("/")).add(new Vector3(0,-3,2)),
                    parseTriangleVertex(data[4].split("/")).add(new Vector3(0,-3,2))

            );
            if(t2.p1.getNormal() != null && t2.p2.getNormal() != null && t2.p3.getNormal() != null){
                t2.hasVertexNormals = true;
            }
            triangles.add(t2);
        }


        return triangles;
    }
    public Vector3 parseTriangleVertex(String[] data) {
        //returns the one of the vertices of the triangle
        Vector3 vertex = null;
        if (!data[0].isEmpty()) {
            int vertexIndex = Integer.parseInt(data[0]) - 1;
            vertex = this.vertices.get(vertexIndex).clone();
        }
        if(!data[1].isEmpty()){
            //VT
            int index = Integer.parseInt(data[1]) -1;
            vertex.textureCords = textureCords.get(index).clone();
        }
        if(!data[2].isEmpty()){
            vertex.setNormal(normals.get(Integer.parseInt(data[2])-1));
        }

        return vertex;
    }

    public Triangle[] parseFile() throws Exception {

        //reads the provided obj file and parses the file into triangles

        BufferedReader input = new BufferedReader(new FileReader(file));






        String line;
        while ((line = input.readLine()) != null) {
            String[] data = line.split(" ");
            switch (data[0]) {

                case "v":
                    vertices.add(parseVertex(data));
                    break;

                case "f":
                    faces.addAll(parseFace(data));
                    break;
                case "vt":
                    textureCords.add(parseTextureCord(data));
                    break;
                case "vn":
                    normals.add(parseNormal(data));

            }
        }
        input.close();

        Triangle[] facesArray = new Triangle[faces.size()];
        facesArray = faces.toArray(facesArray);
        return facesArray;
    }
    public Vector3 parseNormal(String[] data){
        return new Vector3(Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]));
    }

    @Override
    public Color getColorAt(Vector3 cords) {
        return getMaterial().color;
    }
}