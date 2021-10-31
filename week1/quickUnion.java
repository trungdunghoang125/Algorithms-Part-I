public class quickUnion {

    private int[] id;

    // Creat a array that store the index
    public quickUnion(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    // Get the root of a node
    private int root(int i) {
        while(i != id[i]) i = id[i];
        return i;
    }

    // Check if connect (or in a connect component)
    public boolean connected (int p, int q) {
        return root(p) == root(q);
    }

    // function to union
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }
    
    // main function to test
    public static void main(String [] args) {
        quickUnion array = new quickUnion(10);
        array.union(4, 3);
        array.union(3, 8);
        array.union(6, 5);
        array.union(9, 4);
        array.union(2, 1);
        array.union(5, 0);
        array.union(7, 2);
        array.union(6, 1);
        System.out.println(array.connected(5, 4));
    }
}
