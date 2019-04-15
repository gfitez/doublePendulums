public class Vector {
    double x,y;
    public Vector(double x,double y){
        this.x=x;
        this.y=y;
    }
    public double dist(Vector other){
        return Math.sqrt(Math.pow((this.x-other.x),2)+Math.pow((this.y-other.y),2));
    }
    public double mag(){
        return Math.sqrt(Math.pow(this.x,2)+Math.pow(this.y,2));
    }
    public void add(Vector other){
        this.x+=other.x;
        this.y+=other.y;
    }
    public void div(double a){
        this.x/=a;
        this.y/=a;
    }

    public static Vector mult(Vector v1,double a){
        return new Vector(v1.x*a,v1.y*a);
    }
    public static Vector div(Vector v1,double a){
        return new Vector(v1.x/a,v1.y/a);
    }
    public static Vector add(Vector v1, Vector v2){
        return new Vector(v1.x+v2.x,v2.y+v2.y);
    }
}
