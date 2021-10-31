public class Percolation {
    private WeightQuickUnionUF grid;
    private WeightQuickUnionUF full;
    private int n;
    private int top;
    private int bottom;
    private boolean[] openNodes;
    private int count = 0;


    public Percolation(int n) {
        if(n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }

        grid = new WeightQuickUnionUF(n*n + 2); // include 2 vitural nodes
        full = new WeightQuickUnionUF(n*n + 1); // 
        this.n = n;
        top = getIndexInArray(n, n) + 1;
        bottom = getIndexInArray(n, n) + 2;

        openNodes = new boolean[n*n]; // include vitural top and vitural bottom 
    }


    private int getIndexInArray(int row, int col) {
        doOutOfBoundsCheck(row, col);
        return n*(row-1) + (col-1);
    }

    private boolean isValid(int row, int col) {
        return row > 0
                && col > 0
                && row <= n
                && col <= n;
    }

    private void doOutOfBoundsCheck(int row, int col) {
        if (!isValid(row, col)) {
            throw new IndexOutOfBoundsException("Boo! Values are out of bounds");
        }
    }


    // Open a site (row, col) if it is not open already
    public void open(int row, int col) {
        doOutOfBoundsCheck(row, col);
        
        // no need to open if it is already open
        if(isOpen(row, col)) {
            return;
        }
        count++;
        int index = getIndexInArray(row, col);
        openNodes[index] = true;

        // if node is in the top row, union 'full' and 'grid' to vitural top node
        if(row == 1) {
            grid.union(top, index);
            full.union(top, index);
        }

        // if node in bottom row, only 'grid' union with vitural bottom node;
        if(row == n) {
            grid.union(bottom, index);
        }

        // union node with node above it
        if(isValid(row-1, col) && isOpen(row-1, col)) {
            grid.union(getIndexInArray(row-1, col), index);
            full.union(getIndexInArray(row-1, col), index);
        }

        // union node with node bottom it
        if(isValid(row+1, col) && isOpen(row+1, col)) {
            grid.union(getIndexInArray(row+1, col), index);
            full.union(getIndexInArray(row+1, col), index);
        }
        
        // union with node to the right of it
        if(isValid(row, col+1) && isOpen(row, col+1)) {
            grid.union(getIndexInArray(row, col+1), index);
            full.union(getIndexInArray(row, col+1), index);
        }

        // union with node to the left of it
        if(isValid(row, col-1) && isOpen(row, col-1)) {
            grid.union(getIndexInArray(row, col-1), index);
            full.union(getIndexInArray(row, col-1), index);
        }
    }

    // is the site (row, col) open ?
    public boolean isOpen(int row, int col) {
        doOutOfBoundsCheck(row, col);
        return openNodes[getIndexInArray(row, col)];
    }

    // is the site (row, col) full ? (or connected to top)
    public boolean isFull(int row, int col) {
        int index = getIndexInArray(row, col);
        return full.isConnectd(index, top);
    }

    // return the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate
    public boolean percolates() {
        return grid.isConnectd(top, bottom);
    }

    
// Clients test
    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(1,3);
        p.open(2,3);
        p.open(2,2);
        p.open(3,2);
        p.open(3,1);
        p.open(4,1);
        System.out.println(p.numberOfOpenSites());
        // System.out.println(p.isOpen(2,1));
        // System.out.println(p.isOpen(3,1));
        // System.out.println(p.isOpen(4,1));
        System.out.println(p.percolates());     
    }
}

// Weight-Quick-Union data structure (class Weight-Quick-Union)
final class WeightQuickUnionUF {
    private int[] id;
    private int[] size;
    //private int count;

    public WeightQuickUnionUF(int n) {
        id = new int[n]; 
        size = new int[n]; 
        
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
}
