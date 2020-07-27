



LinkedList:this class was created so as to take the advantage of linked list.
add,set,get,length has been implemented for furture usage.here changing 

this assignment made use of above created linked list and arrays.
triangle_array-consists of all the triangles that are inserted through ADDTRIANGLE();
edge_array-array of all edges created so far;
point_array-array of points created so far;

each point contains an array of connected points ,connected edged,connected triangles ;
each edge contains an array of connected triangles,that is having this edge as one of thier edges;
each triangle contains an array of connected triangles ,triangles which share comoon side with this triangle;

ADD_TRIANGLE()-each time this method is called 3 points 3 edges 1 triangle is created ;
               if they already exists in the above mentioned array we use those existing references else we use the new reference and add these new ones to the array of that sametype;
               for checking for 3 points it takes 3 times O(n),n is no of elements in point_array
               for checking for 3 points it takes 3 times O(n),n is no of elements in point_array
               for checking for 3 edges it takes 3 times O(n),n is no of elements in edge_array
               
               while adding each triangle we update all the above 3 arrays as required;
               If an edge to be added alreday exists in the edge array then besides 0using the edge's reference we also increase the weight of the edge by 1; 


NEIGHBOURS/EDGE_NEIGHBOURS/TRIANGLE_NEIGHBOURS_OF_POINT ()- all the functions of this sort can be solved by seaching linearly for the input point in the point_array and accessing the 3 arrays inside this element of point_array;
                        this functions assumes a time complexity of O(n);n is no of elements in the array;

IS_CONNECTED(t1,t2)-by linear search we reach t1 in the triangle array mentioned above;
                    we consider this triangle's connected triangles and check in them for t2;
                     we do this recursively for all the elements of the triangle's connected triangle's array till we find t2;
                    if we dont find t2 till al  the triangles in t1's components are finished then we return false else true;
                     the worst case could be t2 not present in t1's component after searching every triangle of t1's component;
                    hence the worst case is of O(n); where n is no of triangles in t1's component;
                   this is as good as running a bfs by choosing the elements fo the triangle array;

NO_OF_COMPONENTS()-we use the  same algorithm as specified above with a slight modification;
                   counter=0;
                   while(triangle_array[i] exists(not null))
                   {
                        by using above algo remove all triangle_array[i].connected triangles   from the main array(make them null)
                        counter++
                   }
                   this counter gives the no of components in our structure;

EDGE_NEIGHBOURS/POINT_ NEIGHBOURS/NEIGHBOURS_OF_TRIANGLE(t1)- get the original reference of t1 from the existing triangle_array and from that element's edge_array,point_array,
                                                              triangle_array return edges,triangles,points if needed as they exists there before hand;
                                                              this traversal linearly takes an ordder of O(n).where n is no of elements in the array;

TYPE MESH()-we run a loop over all the elements of edge array 
              if every edges weight is 2 the its a proper mesh;
              if atleast 1 edge have a weight of 1/3 then it is semi proper mesh and improper mesh respectively;
MAXIMUM_DIAMETER- we first arraylist of arraylist and add all the elements of a component into an arraylist and add this arraylist into this arraylist of arraylist
                  then we consider the arraylist of a component which has max no. of triangles and calculate the min path of each triangle from it to all other triangle in that list
                  then we take the max of that.
CENTROID- for each component we iterate each triangle in the component and add all the xyz coordinates of the points without repetition and then divide them by the no. of points iterated and a point is created by those cordinated and added into the arratlist to be returned.
CENTROID OF COMPONENT- the given point is searched and an adjacent triangle to it is taken.
                        the triangle is used to find the centroid of the component related to it as done in the above method
CLOSEST COMPONENTS- each point is checked with all other points whether they are connected or not, if yes then the distance is stored and at the end min dist is taken and the elements are also stored while storing the min dist so they are hence returned at the last

  