// package com.example.myapp.testfx;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
//equals
public class Matrix <T extends Number> {
    private final int R, C;
    private final T [][] data;
    private final Class <T> type;

    public int getRows() { return R; }
    public int getColumns() { return C; }

    public T get(int _R, int _C) {
        if(_R >= 0 && _R < R && _C >= 0 && _C < C) {
            return data[_R][_C];
        }
        throw new IndexOutOfBoundsException();
    }
    public void set(int _R, int _C, T value) {
        if(_R >= 0 && _R < R && _C >= 0 && _C < C) {
            data[_R][_C] = value;
        }
    }
    public Matrix(Class<T> type, int N){
        this(type, N, N);
    }

    @SuppressWarnings("unchecked")
    public Matrix(Class<T> _type, int _R, int _C) {
        type = _type;
        R = _R;
        C = _C;
        data = (T[][]) Array.newInstance(type, R, C);
    }

    @SuppressWarnings("unchecked")
    public Matrix(Matrix<T> other) {
        R = other.R;
        C = other.C;
        this.type = other.type;
        data = (T[][]) Array.newInstance(type, R, C);
        for(int i = 0; i < R; ++i) {
            data[i] = Arrays.copyOf(other.data[i], C);
        }
    }
    @SuppressWarnings("unchecked")
    public Matrix(Class<T> type, BufferedReader reader, Function<String, T> parser) throws IOException {
        this.type = type;
        this.data = reader.lines()
                .filter(line -> !line.trim().isEmpty())
                .map(line -> Arrays.stream(line.trim().split("\\s+"))
                        .map(parser)
                        .toArray(size -> (T[]) Array.newInstance(type, size)))
                .toArray(size -> (T[][]) Array.newInstance(type, size, 0));
        this.R = data.length;
        this.C = (R > 0) ? data[0].length : 0;
        reader.close();
    }

    public static <T extends Number> Matrix <T>  diag (Class<T> type, Algebra<T> alg, T... args) {
        int N = args.length;
        Matrix<T> m = new Matrix<>(type, N);
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                m.set(i, j, alg.zero());
            }
            m.set(i, i, args[i]);
        }
        return m;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Number> Matrix <T> identity (Class<T> type, Algebra<T> alg, int N) {
        T[] ones = (T[]) java.lang.reflect.Array.newInstance(type, N);
        for (int i = 0; i < N; ++i) {
            ones[i] = alg.one();
        } 
        return diag(type, alg, ones);
    }

    public Matrix <T> add (Matrix <T> other, Algebra<T> alg) {
        return operate(other, alg::add);
    }

    public Matrix <T> subtract (Matrix <T> other, Algebra<T> alg) {
        return operate(other, alg::subtract);
    }

    public Matrix <T> multiply (Matrix <T> other, Algebra<T> alg) {
        if(C != other.R) throw new IllegalArgumentException("incompatible dimensions");
        Matrix <T> res = new Matrix<>(type, R, other.C);
        for (int i = 0; i < R; ++i) {
            for (int j = 0; j < other.C; ++j) {
                T sum = alg.zero();
                for (int k = 0; k < this.C; ++k) {
                    T product = alg.multiply(this.get(i, k), other.get(k, j));
                    sum = alg.add(sum, product);
                }
                res.set(i, j, sum);
            }
        }
        return res;
    }

    public Matrix<T> transpose() {
        Matrix<T> res = new Matrix<>(type, C, R);
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) res.set(j, i, get(i, j));
        }
        return res;
    }

    public void swapRows(int i, int j) {
        T[] tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    public Matrix <T> operate (Matrix <T> other, BiFunction<T, T, T> operation ) {
        if (R != other.getRows() || C != other.getColumns()) throw new IllegalArgumentException("incompatible");
        Matrix <T> res = new Matrix<>(type, R, C);
        for (int i = 0; i < R; ++i) {
            for (int j = 0; j < C; ++j) {
                res.set(i, j, operation.apply(this.data[i][j], other.data[i][j]));
            }
        }
        return res;
    }

    public Matrix<T> rref(Algebra<T> alg) {
        Matrix<T> ans = new Matrix<>(this);
        int pivot = 0;
        for (int r = 0; r < ans.R; ++r) {
            if (pivot >= ans.C) break;
            int i = r;
            while (ans.get(i, pivot).equals(alg.zero())) {
                i++;
                if (i == ans.R) {
                    i = r;
                    pivot++;
                    if (pivot == ans.C) return ans;
                }
            }
            ans.swapRows(i, r);
            T lv = ans.get(r, pivot);
            for (int j = 0; j < ans.C; ++j) {
                ans.set(r, j, alg.divide(ans.get(r, j), lv));
            }
            for (int k = 0; k < ans.R; ++k) {
                if (k != r) {
                    T factor = ans.get(k, pivot);
                    for (int j = 0; j < ans.C; ++j) {
                        T sub = alg.multiply(factor, ans.get(r, j));
                        ans.set(k, j, alg.subtract(ans.get(k, j), sub));
                    }
                }
            }
            ++pivot;
        }
        return ans;
    }
    public Matrix<T> augment(Matrix<T> other) {
        if (this.R != other.R) {
            throw new IllegalArgumentException("Error: different number of rows");
        }
        Matrix<T> ans = new Matrix<>(this.type, this.R, this.C + other.C);
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < this.C; j++) {
                ans.set(i, j, this.get(i, j));
            }
            for (int j = 0; j < other.C; j++) {
                ans.set(i, j + this.C, other.get(i, j));
            }
        }
        return ans;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                sb.append(data[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
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
}