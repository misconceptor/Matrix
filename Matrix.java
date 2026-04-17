import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.*;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
//exceptions
//transpose, + - *
//equals
//swap_rows
public class Matrix{
    private final int R, C;
    private int [][] data;
    public int getRows() { return R; }
    public int getColumns() { return C; }
    public int get(int _R, int _C) {
        if(_R >= 0 && _R < R && _C >= 0 && _C < C) {
            return data[_R][_C]; 
        }
        return 0;
    }
    public void set(int _R, int _C, int value) {
        if(_R >= 0 && _R < R && _C >= 0 && _C < C) {
            data[_R][_C] = value;
        }
    }
    public Matrix() {
        R = C = 0;
        data = null;
    }
    public Matrix(int N){
        R = C = N;
        data = new int[N][N];
    }
    public Matrix(int _R, int _C) {
        R = _R;
        C = _C;
        data = new int[R][C];
    }
    public Matrix(Matrix other) {
        R = other.getRows(); 
        C = other.getColumns();
        data = new int[R][C];
        for(int i = 0; i < R; ++i) {
            data[i] = Arrays.copyOf(other.data[i], C);
        }
    }
    public Matrix(int[][] arr) { // need exception when arr is not rectangular
        R = arr.length;
        C = arr[0].length;
        data = new int[R][C]; 
        for(int i = 0; i < R; ++i) {
            data[i] = Arrays.copyOf(arr[i], C);
        }
    }
    public Matrix(String filename) throws IOException {
        data = Files.lines(Paths.get(filename)) 
            .filter(line -> !line.trim().isEmpty())
            .map(line -> Arrays.stream(line.trim().split(" "))
            .mapToInt(Integer::parseInt)
            .toArray())
            .toArray(int[][]::new);
        R = data.length;
        C = data[0].length;
    }
    public static Matrix diag (int ... args) {
        int N = args.length;
        Matrix m = new Matrix(N);
        for(int i = 0; i < N; ++i) {
            m.set(i, i, args[i]);
        }
        return m;
    }
    public static Matrix identity (int N) {
        int[] a = new int[N];
        Arrays.fill(a, 1);
        Matrix i = Matrix.diag(a);
        return i;
    } 
    public void printMatrix() {
        Arrays.stream(data).forEach(
            row -> {
                Arrays.stream(row).forEach(
                    el -> System.out.print(el + " ")
                );
                System.out.println();
            }
        );
    }
    public Matrix add(Matrix other) { 
        if(R != other.getRows() || C != other.getColumns()) throw new IllegalArgumentException("non-compatible");
        int[][] _data = 
        IntStream

        
    }
}