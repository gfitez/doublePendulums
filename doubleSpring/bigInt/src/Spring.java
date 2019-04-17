import java.math.BigDecimal;
import java.math.MathContext;

public class Spring {
    Vector start,end;
    BigDecimal naturalLength;
    private BigDecimal k;
    private MathContext mc;
    public Spring(Vector start, Vector end, BigDecimal naturalLength, BigDecimal k, MathContext mc){
        this.start=start;
        this.end=end;
        this.naturalLength=naturalLength;
        this.k=k;
        this.mc=mc;
    }
    public BigDecimal length(){
        return start.dist(end);
    }
    public Vector force(){
        BigDecimal f = (naturalLength.subtract(length(),mc).multiply(k));

        double y=end.y.doubleValue()-start.y.doubleValue();
        double x=end.x.doubleValue()-start.x.doubleValue();
        double theta=Math.atan2(y,x);

        BigDecimal xF= new BigDecimal(Math.cos(theta),mc).multiply(f);
        BigDecimal yF= new BigDecimal(Math.sin(theta),mc).multiply(f);

        //TODO FIX!!
        xF=new BigDecimal(0,mc);
        yF=f.multiply(new BigDecimal((y>0?1:-1),mc));

        return new Vector(xF,yF,mc);
    }
    public BigDecimal E(){
        //return new BigDecimal(Math.pow(length().doubleValue()-naturalLength.doubleValue(),2)*0.5*k.doubleValue());
        return (length().subtract(naturalLength,mc)).pow(2,mc).multiply(k,mc).multiply(new BigDecimal("0.5",mc),mc);
    }
}
