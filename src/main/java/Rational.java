import java.math.BigInteger;

public class Rational extends Number {

    public class RationalAlgebra implements Algebra <Rational> {
        @Override public Rational add (Rational a, Rational b) {
            return a.add(b); 
        }
        @Override public Rational subtract (Rational a, Rational b) {
            return a.subtract(b);
        }
        @Override public Rational multiply (Rational a, Rational b) {
            return a.multiply(b);
        }
        @Override public Rational divide (Rational a, Rational b) {
            return a.divide(b);
        }
        @Override public Rational zero() {
            return new Rational(0);
        }
        @Override public Rational one() {
            return new Rational(1);
        }
    }

    private BigInteger num, denom;

    private void normalize () {
        BigInteger _gcd = num.gcd(denom);
        num = num.divide(_gcd);
        denom = denom.divide(_gcd);
        if (denom.compareTo(BigInteger.ZERO) < 0) {
            num = num.negate();
            denom = denom.negate();
        }
    }

    public Rational () {
        num = BigInteger.ZERO;
        denom = BigInteger.ONE;
    }
    public Rational (int _num, int _denom) {
        num = BigInteger.valueOf(_num);
        denom = BigInteger.valueOf(_denom);
        normalize();
    }
    public Rational (BigInteger _num, BigInteger _denom) {
        if(_denom.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("zero denominator");
        }
        num = _num;
        denom = _denom;
        normalize();
    }
    public Rational (BigInteger _num) {
        this(_num, BigInteger.ONE);
    }
    public Rational (int _num) {
        this(_num, 1);
    }
    public Rational (Rational other) {
        this(other.num, other.denom);
    }
    public Rational negate() {
        return new Rational(num.negate(), denom);
    }
    public Rational add (Rational other) {
        BigInteger _num = num.multiply(other.denom).add(other.num.multiply(denom));
        BigInteger _denom = denom.multiply(other.denom);
        return new Rational(_num, _denom);
    }
    public Rational subtract (Rational other) {
        return this.add(other.negate());
    }
    public Rational multiply (Rational other) {
        BigInteger _num = num.multiply(other.num);
        BigInteger _denom = denom.multiply(other.denom);
        return new Rational(_num, _denom);
    }

    public Rational divide (Rational other) {
        BigInteger _num = num.multiply(other.denom);
        BigInteger _denom = denom.multiply(other.num);
        return new Rational(_num, _denom);
    }

    @Override
    public String toString() {
        if (denom.equals(BigInteger.ONE)) return num.toString();
        return num.toString() + " / " + denom.toString();
    }
    @Override
    public boolean equals (Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Rational other = (Rational)obj;
        return num.equals(other.num) && denom.equals(other.denom);
    }
    @Override
    public int hashCode() {
        return 31 * num.hashCode() + denom.hashCode();
    }

    @Override
    public int intValue() {
        return num.divide(denom).intValue();
    }
    
    @Override
    public long longValue() {
        return num.divide(denom).longValue();
    }
    
    @Override
    public float floatValue() {
        return num.floatValue() / denom.floatValue();
    }
    
    @Override
    public double doubleValue() {
        return num.doubleValue() / denom.doubleValue();
    }
}