public class Triangle implements TriangleInterface {

    Point p1,p2,p3;
    Edge e1,e2,e3;
    int index;
    int index2;
    public boolean equals(Triangle t) {
        if ((this.p1.equals(t.p1) || this.p1.equals(t.p2) || this.p1.equals(t.p3)) &&
                (this.p2.equals(t.p1) || this.p2.equals(t.p2) || this.p2.equals(t.p3)) &&
                (this.p3.equals(t.p1) || this.p3.equals(t.p2) || this.p3.equals(t.p3)))
            return true;
        else return false;
    }
    public Triangle(Point a,Point b,Point c) {
        p1=a;
        p2=b;
        p3=c;
        adjtriangles = new ArrayList();
        p=new Point[]{p1,p2,p3};
    }

//    @Override
//    public String toString() {
//        return p1.toString()+p2.toString()+p3.toString();
//    }
    public int compareTo(Triangle t) {
        Integer i1=index;
        Integer i2=t.index;
        return i2.compareTo(i1);
    }

    public Triangle(float[] coordinates) {
        p1=new Point(coordinates[0],coordinates[1],coordinates[2]);
        p2=new Point(coordinates[3],coordinates[4],coordinates[5]);
        p3=new Point(coordinates[6],coordinates[7],coordinates[8]);
        adjtriangles = null;
        p=new Point[]{p1,p2,p3};
    }
    public ArrayList<Triangle> adjtriangles;

    Point[] p;
    @Override
    public PointInterface[] triangle_coord() {
        return p;
    }
}
