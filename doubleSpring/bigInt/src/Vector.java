import java.math.BigDecimal;
import java.math.MathContext;

public class Vector {
    public BigDecimal x,y;
    private MathContext mc;
    public Vector(BigDecimal x, BigDecimal y, MathContext mc){
        this.x=x;
        this.y=y;
        this.mc=mc;
    }
    public BigDecimal dist(Vector other){
        return other.x.subtract(this.x,mc).pow(2,mc).add(other.y.subtract(this.y,mc).pow(2,mc),mc).sqrt(mc);
    }
    public void add(Vector other){
        this.x=this.x.add(other.x);
        this.y=this.y.add(other.y);
    }
    public void mult(BigDecimal a){
        this.x=this.x.multiply(a);
        this.y=this.y.multiply(a);
    }
    public static Vector add(Vector v1, Vector v2, MathContext mc){
        return new Vector(v1.x.add(v2.x,mc),v1.y.add(v2.y,mc),mc);
    }
    public static Vector mult(Vector v1, BigDecimal a, MathContext mc){
        return new Vector(v1.x.multiply(a,mc),v1.y.multiply(a,mc),mc);
    }
    public static Vector div(Vector v1, BigDecimal a, MathContext mc){
        return new Vector(v1.x.divide(a,mc),v1.y.divide(a,mc),mc);
    }
    public BigDecimal mag(){
        return this.x.pow(2,mc).add(this.y.pow(2,mc)).sqrt(mc);
    }

}
