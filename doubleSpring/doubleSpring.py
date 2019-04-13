class Vector:
    def __init__(x,y):
        self.x=x;
        self.y=y;

    def dist(other):
        return sqrt(pow(self.x-other.x,2)+pow(self.y-other.y,2));


class Spring:
    def __init__(start,end,length,k):
        self.start=start;
        self.end=end;
        self.length=length;
        self.k=k;
    def length():
        return start.dist(end)

    def force():
        f=(self.naturalLength-self.length())*k;
        theta=atan2(-(start.y-end.y),-(start.x-end.x));
        return Vector(f*cos(theta),f*sin(theta));
    def E():
        return 0.5*self.k*pow(self.length()-self.naturalLength,2)
