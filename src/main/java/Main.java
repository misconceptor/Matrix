import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) {
        InputStream is = Main.class.getClassLoader()
        .getResourceAsStream("in1.txt");
        InputStream is2 = Main.class.getClassLoader()
        .getResourceAsStream("in2.txt");
        try{
            if(is == null) {
                System.err.println("file not found in folder"); 
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            Matrix <Integer> m = new Matrix<>(Integer.class, br, Integer::valueOf);
            br = new BufferedReader(new InputStreamReader(is2));

            Matrix <Integer> n = new Matrix<>(Integer.class, br, Integer::valueOf);

            IntegerAlgebra ia = new IntegerAlgebra();
            m.multiply(n, ia).printMatrix();

        } catch (IOException e) {
            System.err.println("ERROR READING FILE " + e.getMessage());
        }

        // String in2 = "in2.txt";
    }
}