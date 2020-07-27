public class Edge implements EdgeInterface {
    Point a,b;
    int index;
    int weight=1;
    public ArrayList<Triangle> adjtriangles=new ArrayList();
    public Double length;


    public boolean equals(Edge edge) {
        if ((a.equals(edge.a)&&b.equals(edge.b))||(a.equals(edge.b)&&b.equals(edge.a)))
            return true;
        else return false;
    }

    public int compareTo(Edge e) {
        return length.compareTo(e.length);
    }
    public Edge(float[] coordinates) {
        a=new Point(coordinates[0],coordinates[1],coordinates[2]);
        b=new Point(coordinates[3],coordinates[4],coordinates[5]);
        ab=new Point[]{a,b};
        length= Math.sqrt(((a.x - b.x) * (a.x - b.x)) + ((a.y - b.y) * (a.y - b.y)) + ((a.z - b.z) * (a.z - b.z)));
    }

    Point[] ab;
    public Edge (Point p1,Point p2) {
        a=p1;b=p2;
        ab=new Point[]{a,b};
        length= Math.sqrt(((a.x - b.x) * (a.x - b.x)) + ((a.y - b.y) * (a.y - b.y)) + ((a.z - b.z) * (a.z - b.z)));
    }
    @Override
    public PointInterface[] edgeEndPoints() {
        return ab;
    }
}
