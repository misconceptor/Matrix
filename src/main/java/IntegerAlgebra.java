public class IntegerAlgebra implements Algebra<Integer>{
    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }
    @Override
    public Integer subtract(Integer a, Integer b) {
        return a - b;
    }
    @Override
    public Integer multiply(Integer a, Integer b) {
        return a * b;
    }
    @Override
    public Integer divide (Integer a, Integer b) {
        return a / b;
    }
    @Override
    public Integer zero() {
        return 0;
    }
    @Override
    public Integer one() {
        return 1;
    }
}