
public class Shape implements ShapeInterface {
    int n=100000;
    int pointcount =0;
    int trianglecount=0;
    int edgecount=0;
    public Triangle[] triangles=new Triangle[n];
    public Point[] points =new Point[n];
    public Edge[] edges=new Edge[n];
    //public int[][] pointmatrix=new int[n][n];
    //public int[][] trianglematrix=new int[n][n];
    //public int[][] edgematrix=new int[n][n];

    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
    public boolean ADD_TRIANGLE(float [] triangle_coord){
        float x2=triangle_coord[3]-triangle_coord[0];
        float y2=triangle_coord[4]-triangle_coord[1];
        float z2=triangle_coord[5]-triangle_coord[2];
        float x1=triangle_coord[6]-triangle_coord[3];
        float y1=triangle_coord[7]-triangle_coord[4];
        float z1=triangle_coord[8]-triangle_coord[5];

        float s1=(x1*x2)+(y1*y2)+(z1*z2);
        double d1=Math.sqrt((x1*x1)+(y1*y1)+(z1*z1));
        double d2=Math.sqrt((x2*x2)+(y2*y2)+(z2*z2));
        double cos=s1/(d1*d2);
        double tan=Math.tan(Math.acos(cos));
        if (tan<0)
            tan=-tan;
        //System.out.println(tan);
        if(tan<0.001){
            //System.out.println('1');
            return false;
        }
        //System.out.println('2');

        //collinear case is to be handled
        Point p1=new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
        Point p2=new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
        Point p3=new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
        Triangle triangle = new Triangle(p1,p2,p3);
        boolean a=true,b=true,c=true;
        for (int i=0;i<pointcount;i++){
            if (p1.equals(points[i])){
                p1=points[i];
                //p1.index=i;
                a=false;
            }
            else if (p2.equals(points[i])){
                p2=points[i];
                //p2.index=i;
                b=false;
            }
            else if (p3.equals(points[i])){
                p3=points[i];
                //p3.index=i;
                c=false;
            }
        }
        //add point if it does not exist
        if (a){
            p1.index=pointcount;
            points[pointcount++]=p1;
        }
        if (b){
            p2.index=pointcount;
            points[pointcount++]=p2;
        }
        if (c){
            p3.index=pointcount;
            points[pointcount++]=p3;
        }
        //add edge if it does not exist

        Edge e1=new Edge(p1,p2);
        Edge e2=new Edge(p3,p2);
        Edge e3=new Edge(p1,p3);
        boolean a1=true,b1=true,c1=true;
        for (int i=0;i<edgecount;i++){
            if (e1.equals(edges[i])){
                e1=edges[i];
                a1=false;
            }
            else if (e2.equals(edges[i])){
                e2=edges[i];
                b1=false;
            }
            else if (e3.equals(edges[i])){
                e3=edges[i];
                c1=false;
            }
        }

        if (a1){
            e1.index=edgecount;
            edges[edgecount++]=e1;
            p1.adjpoints.add(p2);
            p2.adjpoints.add(p1);
            p1.adjedges.add(e1);
            p2.adjedges.add(e1);
        }else {
            e1.weight++;
        }
        if (b1){
            e2.index=edgecount;
            edges[edgecount++]=e2;
            p3.adjpoints.add(p2);
            p2.adjpoints.add(p3);
            p2.adjedges.add(e2);
            p3.adjedges.add(e2);
        }else {
            e2.weight++;
        }
        if (c1){
            e3.index=edgecount;
            edges[edgecount++]=e3;
            p1.adjpoints.add(p3);
            p3.adjpoints.add(p1);
            p1.adjedges.add(e3);
            p3.adjedges.add(e3);
        }else {
            e3.weight++;
        }
        p1.adjtriangles.add(triangle);
        p2.adjtriangles.add(triangle);
        p3.adjtriangles.add(triangle);
        for (int i=0;i<e1.adjtriangles.length;i++){
            triangle.adjtriangles.add(e1.adjtriangles.get(i));
            e1.adjtriangles.get(i).adjtriangles.add(triangle);
        }
        for (int i=0;i<e2.adjtriangles.length;i++){
            triangle.adjtriangles.add(e2.adjtriangles.get(i));
            e2.adjtriangles.get(i).adjtriangles.add(triangle);
        }
        for (int i=0;i<e3.adjtriangles.length;i++){
            triangle.adjtriangles.add(e3.adjtriangles.get(i));
            e3.adjtriangles.get(i).adjtriangles.add(triangle);
        }
        triangle.e1=e1;
        triangle.e2=e2;
        triangle.e3=e3;
        e1.adjtriangles.add(triangle);
        e2.adjtriangles.add(triangle);
        e3.adjtriangles.add(triangle);
        triangle.index=trianglecount;
        triangles[trianglecount++]=triangle;
        //representing edges i.e. adjacency of points
        //pointmatrix[p1.index][p2.index]++;
        //pointmatrix[p3.index][p2.index]++;
        //pointmatrix[p1.index][p3.index]++;

        return true;
    }

    //
    public int TYPE_MESH(){
        boolean a=false,b=false;
        for (int i=0;i<edgecount;i++){
            if (edges[i].weight==1)
                a=true;
            else if (edges[i].weight>2)
                b=true;
        }
        //improper
        if (b)
            return 3;
        //semi proper
        if (a&&!b)
            return 2;
        return 1;
    }

    //
    public EdgeInterface [] BOUNDARY_EDGES(){
        ArrayList <Edge>list=new ArrayList<>();
        //System.out.println('1');
        //System.out.println(edgecount);


        for (int i=0;i<edgecount;i++){
            //System.out.println(edges[i].weight);

            if (edges[i].weight==1) {
                //System.out.println('2');
                list.add(edges[i]);
            }
        }

        if (list.length==0)
            return null;
        for (int i=0;i<list.length;i++) {
            for (int j=0;j<list.length-i-1;j++){
                if (list.get(j).compareTo(list.get(j+1))>0)
                    list.swap(j,j+1);
            }
        }
        Edge[] ar=new Edge[list.length];
        for (int i=0;i<list.length;i++) {
            ar[i]=list.get(i);
        }
        for (int i = 0; i < ar.length; i++) {
            for (int j = 0; j < ar.length-i-1; j++) {
                if (ar[j+1].compareTo(ar[j])<0){
                    Edge temp=ar[j];
                    ar[j]=ar[j+1];
                    ar[j+1]=temp;
                }

            }
        }
            return ar;
    }

    public void countrecurs(int v, Triangle[] visited) {
        // Mark the current node as visited and print it
        Triangle temp=visited[v];
        visited[v] = null;
        //System.out.println(temp);
        // Recur for all the vertices
        // adjacent to this vertex
        for ( int i=0;i<temp.adjtriangles.length; i++) {
            Triangle x=temp.adjtriangles.get(i);
            if(visited[x.index]!=null) countrecurs(x.index,visited);
        }

    }
    //
    public int COUNT_CONNECTED_COMPONENTS(){
        Triangle[] triangles1=new Triangle[trianglecount];
        //System.out.println('1');
        for(int i=0;i<trianglecount; i++) {
            //System.out.println('2');

            triangles1[i]=triangles[i];
        }
        int x=0;
        //if
        for(int v = 0; v < trianglecount; ++v) {
            //System.out.println('3');

            if(triangles1[v]!=null) {
                //System.out.println('4');

                countrecurs(v,triangles1);
                x++;
            }
        }
        return x;
    }


    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
    public TriangleInterface [] NEIGHBORS_OF_TRIANGLE(float [] triangle_coord){
        Triangle triangle=new Triangle(triangle_coord);
        for (int i=0;i<trianglecount;i++) {
            if (triangles[i].equals(triangle)) {
                triangle=triangles[i];
                break;
            }
        }
        ArrayList<Triangle> list=triangle.adjtriangles;
        if (list==null)
            return null;
        Triangle[] ar=new Triangle[list.length];
        for (int i=0;i<list.length;i++) {
            ar[i]=list.get(i);
        }
        return ar;
    }


    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
    public EdgeInterface [] EDGE_NEIGHBOR_TRIANGLE(float [] triangle_coord){
        Triangle triangle=new Triangle(triangle_coord);
        for (int i=0;i<trianglecount;i++) {
            if (triangles[i].equals(triangle)) {
                triangle=triangles[i];
                break;
            }
        }
        /*ArrayList<Triangle> list=triangle.adjtriangles;
        if (list==null)
            return null;
        Edge[] ar=new Edge[list.length];
        for (int i=0;i<list.length;i++) {
            ar[i]=list.get(i);
        }*/
        Edge[] ar= new Edge[3];
        ar[0]=triangle.e1;
        ar[1]=triangle.e2;
        ar[2]=triangle.e3;
        return ar;
    }

    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
    public PointInterface [] VERTEX_NEIGHBOR_TRIANGLE(float [] triangle_coord){
        Triangle triangle=new Triangle(triangle_coord);
        for (int i=0;i<trianglecount;i++) {
            if (triangles[i].equals(triangle)) {
                triangle=triangles[i];
                break;
            }
        }
        Point[] ar= new Point[3];
        ar[0]=triangle.p1;
        ar[1]=triangle.p2;
        ar[2]=triangle.p3;
        return ar;
    }


    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
    public TriangleInterface [] EXTENDED_NEIGHBOR_TRIANGLE(float [] triangle_coord){
        Triangle triangle=new Triangle(triangle_coord);
        for (int i=0;i<trianglecount;i++) {
            if (triangles[i].equals(triangle)) {
                triangle=triangles[i];
                break;
            }
        }
        ArrayList<Triangle> list=triangle.adjtriangles;
        if (list==null)
            return null;
        ArrayList<Triangle> list1=new ArrayList<>();
        list1.addall(list);
        for (int i=0;i<triangle.p1.adjtriangles.length;i++) {
            if (!triangle.p1.adjtriangles.get(i).e1.equals(triangle.e1)&&
                    !triangle.p1.adjtriangles.get(i).e2.equals(triangle.e1)&&
                    !triangle.p1.adjtriangles.get(i).e3.equals(triangle.e1)&&
                    !triangle.p1.adjtriangles.get(i).e1.equals(triangle.e3)&&
                    !triangle.p1.adjtriangles.get(i).e2.equals(triangle.e3)&&
                    !triangle.p1.adjtriangles.get(i).e3.equals(triangle.e3)) {
                list1.add(triangle.p1.adjtriangles.get(i));
            }
        }
        for (int i=0;i<triangle.p2.adjtriangles.length;i++) {
            if (!triangle.p2.adjtriangles.get(i).e1.equals(triangle.e1)&&
                    !triangle.p2.adjtriangles.get(i).e2.equals(triangle.e1)&&
                    !triangle.p2.adjtriangles.get(i).e3.equals(triangle.e1)&&
                    !triangle.p2.adjtriangles.get(i).e1.equals(triangle.e2)&&
                    !triangle.p2.adjtriangles.get(i).e2.equals(triangle.e2)&&
                    !triangle.p2.adjtriangles.get(i).e3.equals(triangle.e2)) {
                list1.add(triangle.p2.adjtriangles.get(i));
            }
        }
        for (int i=0;i<triangle.p3.adjtriangles.length;i++) {
            if (!triangle.p3.adjtriangles.get(i).e1.equals(triangle.e2)&&
                    !triangle.p3.adjtriangles.get(i).e2.equals(triangle.e2)&&
                    !triangle.p3.adjtriangles.get(i).e3.equals(triangle.e2)&&
                    !triangle.p3.adjtriangles.get(i).e1.equals(triangle.e3)&&
                    !triangle.p3.adjtriangles.get(i).e2.equals(triangle.e3)&&
                    !triangle.p3.adjtriangles.get(i).e3.equals(triangle.e3)) {
                list1.add(triangle.p3.adjtriangles.get(i));
            }
        }
        Triangle[] ar=new Triangle[list1.length];
        for (int i=0;i<list1.length;i++)
            ar[i]=list1.get(i);
        for (int i = 0; i < ar.length; i++) {
            for (int j = 0; j < ar.length-i-1; j++) {
                if (ar[j+1].compareTo(ar[j])<0){
                    Triangle temp=ar[j];
                    ar[j]=ar[j+1];
                    ar[j+1]=temp;
                }

            }
        }
        return ar;
    }


    //INPUT [x,y,z]
    public TriangleInterface [] INCIDENT_TRIANGLES(float [] point_coordinates){
        Point point=new Point(point_coordinates);
        for (int i=0;i<pointcount;i++) {
            if (points[i].equals(point)) {
                point=points[i];
                break;
            }
        }
        ArrayList<Triangle> list=point.adjtriangles;
        if (list==null)
            return null;
        Triangle[] ar=new Triangle[list.length];
        for (int i=0;i<list.length;i++) {
            ar[i]=list.get(i);
        }
        return ar;
    }


    // INPUT [x,y,z]
    public PointInterface [] NEIGHBORS_OF_POINT(float [] point_coordinates){
        Point point=new Point(point_coordinates);
        for (int i=0;i<pointcount;i++) {
            if (points[i].equals(point)) {
                point=points[i];
                break;
            }
        }
        ArrayList<Point> list=point.adjpoints;
        if (list==null)
            return null;
        Point[] ar=new Point[list.length];
        for (int i=0;i<list.length;i++) {
            ar[i]=list.get(i);
        }
        return ar;
    }


    // INPUT[x,y,z]
    public EdgeInterface [] EDGE_NEIGHBORS_OF_POINT(float [] point_coordinates){
        Point point=new Point(point_coordinates);
        for (int i=0;i<pointcount;i++) {
            if (points[i].equals(point)) {
                point=points[i];
                break;
            }
        }
        ArrayList<Edge> list=point.adjedges;
        if (list==null)
            return null;
        Edge[] ar=new Edge[list.length];
        for (int i=0;i<list.length;i++) {
            ar[i]=list.get(i);
        }
        return ar;
    }


    // INPUT[x,y,z]
    public TriangleInterface [] FACE_NEIGHBORS_OF_POINT(float [] point_coordinates){ return INCIDENT_TRIANGLES(point_coordinates);}


    public boolean isconnectedrecurs(Triangle triangle,int v, boolean[] visited) {
        // Mark the current node as visited and print it
        Triangle temp=triangles[v];
        visited[v] = true;
        if (triangle.equals(temp))
            return true;
        boolean b=false;
        //System.out.println(temp);
        // Recur for all the vertices
        // adjacent to this vertex
        for ( int i=0;i<temp.adjtriangles.length; i++) {
            Triangle x=temp.adjtriangles.get(i);
            if(!visited[x.index])
               b= isconnectedrecurs(triangle,x.index,visited);
            if (b)
                break;
        }
        return b;
    }

// INPUT // [xa1,ya1,za1,xa2,ya2,za2,xa3,ya3,za3 , xb1,yb1,zb1,xb2,yb2,zb2,xb3,yb3,zb3]   where xa1,ya1,za1,xa2,ya2,za2,xa3,ya3,za3 are 3 coordinates of first triangle and xb1,yb1,zb1,xb2,yb2,zb2,xb3,yb3,zb3 are coordinates of second triangle as given in specificaition.

    public boolean IS_CONNECTED(float [] triangle_coord_1, float [] triangle_coord_2){
        Triangle triangle = new Triangle(triangle_coord_1);
        for ( int i=0;i<trianglecount; i++) {
            if(triangle.equals(triangles[i])){
                triangle=triangles[i];
                break;
            }
        }
        if (triangle.adjtriangles==null)
            return false;
        Triangle triangle1=new Triangle(triangle_coord_2);
        boolean[] visited = new boolean[trianglecount];
        return isconnectedrecurs(triangle1,triangle.index,visited);
    }


    // INPUT [x1,y1,z1,x2,y2,z2] // where (x1,y1,z1) refers to first end point of edge and  (x2,y2,z2) refers to the second.
    public TriangleInterface [] TRIANGLE_NEIGHBOR_OF_EDGE(float [] edge_coordinates){
        Edge edge=new Edge(edge_coordinates);
        for (int i=0;i<edgecount;i++) {
            if (edges[i].equals(edge)) {
                edge=edges[i];
                break;
            }
        }
        ArrayList<Triangle> list=edge.adjtriangles;
        if (list==null)
            return null;
        Triangle[] ar=new Triangle[list.length];
        for (int i=0;i<list.length;i++) {
            ar[i]=list.get(i);
        }
        return ar;
    }

    public void diarecurs(int v, boolean[] visited,ArrayList<Triangle> list) {

        //System.out.println("added");
        Triangle temp=triangles[v];
        list.add(temp);
        visited[v] = true;
        for ( int i=0;i<temp.adjtriangles.length; i++) {
            Triangle x=temp.adjtriangles.get(i);
            if(!visited[x.index]){
                //System.out.println("added");
                diarecurs(x.index,visited,list);
            }
        }
    }

    public int MAXIMUM_DIAMETER(){

        ArrayList<ArrayList<Triangle>> t=new ArrayList<>();
        boolean[] visited1=new boolean[trianglecount];
        //System.out.println("tria="+trianglecount);
        for(int i=0;i<trianglecount;i++) {
            if(!visited1[triangles[i].index]) {
                //System.out.println("if inside for");
                ArrayList<Triangle> tr= new ArrayList<>();
                diarecurs(i,visited1,tr);
                t.add(tr);
            }
        }
        int max=0;
        int maxlength=0;
        //System.out.println("t.len="+t.get(0).length);
        for(int i=0;i<t.length;i++) {
            if(t.get(max).length<t.get(i).length) {
                //System.out.println("yes");
                maxlength=t.get(i).length;
               max=i;
            }
        }
        if (maxlength==1)
            return 0;
        //System.out.println("max="+max);
        ArrayList<Triangle> list=t.get(max);
        for(int i=0;i<list.length;i++) {
            list.get(i).index2=i;
        }
        int x=list.length;
        int[][] minpaths=new int[x][x];
        for (int i=0;i<x;i++){
            boolean[] visited=new boolean[x];
            int[] distance=new int[x];

            for (int j=0;j<x;j++){
                distance[j]=Integer.MAX_VALUE;
            }
            distance[i]=0;
            for (int j=0;j<x-1;j++){
                int minVertex=findMinVertex(distance,visited);
                //System.out.println("minvertex="+ minVertex);
                for (int k=0;k<list.get(minVertex).adjtriangles.length;k++){
                    if (!visited[list.get(minVertex).adjtriangles.get(k).index2]&&distance[minVertex]!=Integer.MAX_VALUE){
                        visited[minVertex]=true;
                        int ndist=distance[minVertex]+1;
                        if (ndist<distance[list.get(minVertex).adjtriangles.get(k).index2])
                            distance[list.get(minVertex).adjtriangles.get(k).index2]=ndist;
                    }
                }
            }
//            for (int i1 = 0; i1 < distance.length; i1++) {
//                System.out.print(distance[i1]+" ");
//            }
//            System.out.println();

            minpaths[i]=distance;
        }
        //System.out.println(minpaths);
        int maxi=0;
        int maxj=0;
        for(int i=0;i<list.length;i++) {
            for(int j=0;j<list.length;j++) {
                if(minpaths[maxi][maxj]<minpaths[i][j]) {
                    maxi=i;
                    maxj=j;
                }
            }
        }
        return minpaths[maxi][maxj];
    }
    public int findMinVertex(int[] distance, boolean[] visited){
        int minVertex=-1;
        for (int i=0;i<distance.length;i++){
            if (!visited[i]&&(minVertex==-1 ||distance[i]<distance[minVertex]))
                minVertex=i;
        }
        return minVertex;
    }


    public void centroidrecurs(float[] xyz,Int count,int v, boolean[] visited,boolean[] visited1) {

        Triangle temp=triangles[v];
        if (!visited1[temp.p1.index]){
            visited1[temp.p1.index]=true;
            xyz[0]+=temp.p1.x;
            xyz[1]+=temp.p1.y;
            xyz[2]+=temp.p1.z;
            count.v++;
        }
        if (!visited1[temp.p2.index]){
            visited1[temp.p2.index]=true;
            xyz[0]+=temp.p2.x;
            xyz[1]+=temp.p2.y;
            xyz[2]+=temp.p2.z;
            count.v++;
        }
        if (!visited1[temp.p3.index]){
            visited1[temp.p3.index]=true;
            xyz[0]+=temp.p3.x;
            xyz[1]+=temp.p3.y;
            xyz[2]+=temp.p3.z;
            count.v++;
        }
        visited[v] = true;
        //System.out.println(temp);

        for ( int i=0;i<temp.adjtriangles.length; i++) {
            Triangle x=temp.adjtriangles.get(i);
            if(!visited[x.index])
                centroidrecurs(xyz,count,x.index,visited,visited1);
        }

    }

    public PointInterface [] CENTROID (){
        boolean[] visited1=new boolean[pointcount];
        boolean[] visited=new boolean[trianglecount];

        ArrayList<Point> list =new ArrayList<>();

        for(int v = 0; v < trianglecount; ++v) {
            float[] xyz=new float[]{0,0,0};
            Int count=new Int(0);
            if(!visited[v]) {
                //System.out.println("="+v);

                centroidrecurs(xyz,count,v,visited,visited1);
                xyz[0]/=count.v;
                xyz[1]/=count.v;
                xyz[2]/=count.v;

                //System.out.println(" v="+count.v+" ");

                //System.out.println();
                //System.out.println("xyz="+xyz[1]);
                Point point=new Point(xyz);
                list.add(point);
            }

        }

        //System.out.println("length ="+list.length);

        Point[]  list1=new Point[list.length];
        for (int v= 0; v < list.length; ++v)
            list1[v]=list.get(v);
        for (int i = 0; i < list1.length; i++) {
            for (int j = 0; j < list1.length-i-1; j++) {
                if (list1[j+1].compareTo(list1[j])<0){
                    Point temp=list1[j];
                    list1[j]=list1[j+1];
                    list1[j+1]=temp;
                }

            }
        }

        return list1;
    }

    // INPUT [x,y,z]
    public PointInterface CENTROID_OF_COMPONENT (float [] point_coordinates){
        //System.out.println("xyz=");
        Integer v=null;
        Point temp=new Point(point_coordinates);
        //System.out.println("pointcount="+pointcount);
        for ( int i=0;i<pointcount; i++) {
            if(points[i].equals(temp)){
                //System.out.println("oh yeah");
                temp=points[i];
                v=i;
            }
        }

        if (v==null)
            return null;
        Int count=new Int( 0);
        int x =temp.adjtriangles.get(0).index;
        //System.out.println("="+x);

        boolean[] visited1=new boolean[pointcount];
        boolean[] visited=new boolean[trianglecount];
        float[] xyz=new float[]{0,0,0};
        //System.out.println("1");

        centroidrecurs(xyz,count,x,visited,visited1);

        //System.out.println(" v="+count.v+" ");
        xyz[0]/=count.v;
        xyz[1]/=count.v;
        xyz[2]/=count.v;
        //System.out.println("=");
        //System.out.println("xyz="+xyz[1]);
        Point point=new Point(xyz);

        return point;
    }


    public PointInterface [] CLOSEST_COMPONENTS(){

            float ar1[]=new float[9];
            float ar2[]=new float[9];

            Point[] answer =new Point[2];
            double min_dist=Double.MAX_VALUE;
            double cald=0;
            for(int i=0;i<pointcount;i++)
                for(int j=0;j<pointcount;j++)
                    for(int k=0;k<points[i].adjtriangles.length;k++)
                        for(int l=0;l<points[j].adjtriangles.length;l++)
                        {
                            ar1[0]=points[i].adjtriangles.get(k).p1.getX();
                            ar1[1]=points[i].adjtriangles.get(k).p1.getY();
                            ar1[2]=points[i].adjtriangles.get(k).p1.getZ();
                            ar1[3]=points[i].adjtriangles.get(k).p2.getX();
                            ar1[4]=points[i].adjtriangles.get(k).p2.getY();
                            ar1[5]=points[i].adjtriangles.get(k).p2.getZ();
                            ar1[6]=points[i].adjtriangles.get(k).p3.getX();
                            ar1[7]=points[i].adjtriangles.get(k).p3.getY();
                            ar1[8]=points[i].adjtriangles.get(k).p3.getZ();
                            ar2[0]=points[j].adjtriangles.get(l).p1.getX();
                            ar2[1]=points[j].adjtriangles.get(l).p1.getY();
                            ar2[2]=points[j].adjtriangles.get(l).p1.getZ();
                            ar2[3]=points[j].adjtriangles.get(l).p2.getX();
                            ar2[4]=points[j].adjtriangles.get(l).p2.getY();
                            ar2[5]=points[j].adjtriangles.get(l).p2.getZ();
                            ar2[6]=points[j].adjtriangles.get(l).p3.getX();
                            ar2[7]=points[j].adjtriangles.get(l).p3.getY();
                            ar2[8]=points[j].adjtriangles.get(l).p3.getZ();

                            if(!IS_CONNECTED(ar1,ar2))
                            {
                                //System.out.println('1');

                                cald=Math.sqrt(((points[i].x-points[j].x)*(points[i].x-points[j].x))+
                                        ((points[i].y-points[j].y)*(points[i].y-points[j].y))+
                                        ((points[i].z-points[j].z)*(points[i].z-points[j].z)));
                                //System.out.println(min_dist+"=mindist "+cald+"=cald");
                                if(min_dist>=cald)
                                {
                                    //System.out.println('1');
                                    min_dist=cald;
                                    answer[0]=points[i];
                                    answer[1]=points[j];
                                }
                            }

                        }
            return answer;
    }
    class Int{
        public Int(float v){
            this.v=v;
        }
        float v;
    }





}

