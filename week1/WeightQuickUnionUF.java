

// Weight-Quick-Union data structure
public class WeightQuickUnionUF {
    private int[] id;
    private int[] size;
    //private int count;

    public WeightQuickUnionUF(int n) {
        id = new int[n];
        size = new int[n];
        //count = n;
        for (int i = 0; i < n; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }
    
    // Check p is in range [0,n-1]
    public void validate(int p) {
        if (p < 0 || p >= id.length) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + id.length);
        }
    }

    // find root of p
    public int root(int p) {
        validate(p);
        while(p != id[p]) {
            p = id[p];
        }
        return p;
    }

    // Check is connected
    public boolean isConnectd(int p, int q) {
        return root(p) == root(q);
    }

    // union two connect component
    public void union(int p, int q) {
        int rootp = root(p);
        int rootq = root(q);
        if (rootp == rootq) return;
        
        // smaller tree point to larger one
        if (size[p] < size[q]) {
            id[rootp] = rootq;
            size[rootq] = size[rootq] + size[rootp];
        }
        else {
            id[rootq] = rootp;
            size[rootp] += size[rootq];
        }
    }
    public static void main(String[] args){
        WeightQuickUnionUF array = new WeightQuickUnionUF(10);
        array.union(4, 3);
        array.union(3, 8);
        array.union(6, 5);
        array.union(9, 4);
        array.union(2, 1);
        array.union(5, 0);
        array.union(7, 2);
        array.union(6, 1);
        System.out.println(array.isConnectd(2, 5));
    }
}