public class ArrayList<T> {
    int n=1000;
    public T[] list=(T[]) new Object[n];
    public int length=0;


    public T get(int i) {
        if (i<n)
        return list[i];
        else return null;
    }
    public void add(T obj) {
        if (length==n){
            n+=n;
            T[] listr=(T[]) new Object[n];
            for (int i=0;i<length;i++)
                listr[i]=list[i];
            list=listr;
        }
        list[length++]=obj;
    }
    public void swap(int a, int b) {
        T temp=list[a];
        list[a]=list[b];
        list[b]=temp;
    }
    public void addall(ArrayList<T> list1 ) {
        for (int i=0;i<list1.length;i++)
            add(list1.get(i));
    }
    public Object remove() {
        length--;
        Object temp=list[length];
        list[length]=null;
        return temp;
    }
}
