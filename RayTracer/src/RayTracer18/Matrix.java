package RayTracer18;

import java.util.Arrays;

public class Matrix {
    public double [][] matrix;
    private int rows;
    private int columns;

    public Matrix(double[][] matrix){
        rows = matrix.length;
        columns = matrix[0].length;
        this.matrix = new double[][]{};
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                this.matrix[i][j] = matrix[i][j];
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "matrix=" + Arrays.toString(matrix) +
                ", rows=" + rows +
                ", columns=" + columns +
                '}';
    }
}
