public class Spring {
    Vector start,end;
    double naturalLength, k;
    public Spring(Vector start, Vector end, double naturalLength, double k){
        this.start=start;
        this.end=end;
        this.naturalLength=naturalLength;
        this.k=k;
    }
    public double length(){
        return start.dist(end);
    }
    public Vector force(){
        double f=(naturalLength-length())*k;
        double theta=Math.atan2(end.y-start.y,end.x-start.x);

        return new Vector(f*Math.cos(theta),f*Math.sin(theta));
    }
    public double E(){
        return 0.5*k*Math.pow(naturalLength-length(),2);
    }
}
