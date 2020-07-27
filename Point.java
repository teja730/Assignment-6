public class Point implements PointInterface {
    public float x,y,z;
    int index;
    public ArrayList<Point> adjpoints;
    public ArrayList<Edge> adjedges;
    public ArrayList<Triangle> adjtriangles;
    public Point(float a,float b,float c) {
        x=a;y=b;z=c;
        adjpoints = new ArrayList();
        adjedges = new ArrayList();
        adjtriangles = new ArrayList();
    }
    public boolean equals(Point point) {
        //System.out.println(x+" "+point.x+" "+y+" "+point.y+" "+z+" "+point.z);
        if (x==point.x&&y==point.y&&z==point.z)
            return true;
        else return false;

    }
//    @Override
//    public String toString() {
//        return getXYZcoordinate().toString();
//    }
    public int compareTo(Point point) {
        if (x<point.x)
            return -1;
        if (x>point.x)
            return 1;
        else if (x==point.x) {
            if (y<point.y)
                return -1;
            if (y>point.y)
                return 1;
            else if (y==point.y) {
                if (z<point.z)
                    return -1;
                if (z>point.z)
                    return 1;
                else if (z==point.z) {
                    return 0;
                }
            }
        }
        return 0;
    }
    public Point(float [] ar) {
        x=ar[0];y=ar[1];z=ar[2];
        adjtriangles=null;
        adjedges=null;
        adjpoints=null;
    }
    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }

    @Override
    public float[] getXYZcoordinate() {
        return new float[]{x, y, z};
    }
}
