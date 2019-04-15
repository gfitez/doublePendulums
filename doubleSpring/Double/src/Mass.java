public class Mass {
    Vector pos, vel, acc;
    double mass;
    public Mass(Vector pos, double mass){
        this.pos=pos;
        this.acc=new Vector(0,0);
        this.vel=new Vector(0,0);

        this.mass=mass;
    }
    public void update(Vector force, double timeInterval){
        pos.add(Vector.mult(vel,timeInterval));
        pos.add(Vector.mult(Vector.mult(acc, Math.pow(timeInterval,2)),0.5));

        Vector oldAcc=acc;
        acc=Vector.div(force,mass);

        vel.add(Vector.mult(Vector.div(Vector.add(acc,oldAcc),2),timeInterval));
    }
    public double KE(){
        return Math.pow(vel.mag(),2)*mass*0.5;
    }
    public double PE(double g){
        return -g*pos.y*mass;
    }
    public double E(double g){
        return KE()+PE(g);
    }
}
