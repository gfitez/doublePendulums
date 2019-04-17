import java.math.BigDecimal;
import java.math.MathContext;

public class Mass {
    Vector pos, vel, acc;
    BigDecimal mass;
    MathContext mc;

    public Mass(Vector pos, BigDecimal mass, MathContext mc) {
        this.pos = pos;
        this.vel = new Vector(new BigDecimal(0, mc), new BigDecimal(0, mc), mc);
        this.acc = new Vector(new BigDecimal(0, mc), new BigDecimal(0, mc), mc);

        this.mass = mass;
        this.mc = mc;
    }

    public void update(Vector force, BigDecimal timeInterval) {



        Vector vTerm = (Vector.mult(vel, timeInterval, mc));
        Vector aTerm = Vector.mult(Vector.mult(acc, new BigDecimal("0.5", mc), mc), timeInterval.pow(2, mc), mc);
        pos.add(Vector.add(vTerm, aTerm, mc));


        Vector oldAcc = acc;
        acc = Vector.div(force, mass, mc);

        vel.add(Vector.mult(Vector.mult(Vector.add(acc, oldAcc, mc), timeInterval, mc), new BigDecimal("0.5", mc), mc));
        //vel.add(Vector.mult(acc, timeInterval, mc));
        //System.out.println(vel.y.toPlainString());
        //System.out.println(Vector.add(acc, oldAcc, mc).y.toPlainString());
        //System.out.println(timeInterval);
        //System.out.println(Vector.mult(Vector.add(acc, oldAcc, mc), timeInterval, mc).y.toPlainString());
        //System.out.println(vel.y.toPlainString());

        //vel.add(Vector.mult(Vector.mult(acc,timeInterval,mc),(new BigDecimal(0.5,mc)),mc));
        //

    }
    public BigDecimal KE(){
        //return new BigDecimal(Math.pow(vel.mag().doubleValue(),2)*mass.doubleValue()*0.5,mc);
        //System.out.println(vel.y.toPlainString());
        //System.out.println(vel.y.toPlainString());
        System.out.println(vel.mag());
        return vel.mag().pow(2,mc).multiply(mass,mc).multiply(new BigDecimal("0.5",mc),mc);
    }
    public BigDecimal PE(BigDecimal g){
        return new BigDecimal(0,mc);
        //return new BigDecimal(pos.y.doubleValue()*-1*g.doubleValue()* mass.doubleValue());
        //return pos.y.multiply(new BigDecimal(-1,mc),mc).multiply(g,mc).multiply(mass,mc);
    }
    public BigDecimal E(BigDecimal g){
        return KE().add(PE(g));
    }
}
