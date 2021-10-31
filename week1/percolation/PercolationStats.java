import java.lang.Math;
public class PercolationStats {
    private int n; // kich co cua khoi
    private int trials; // so thu nghiem thuc hien
    private double[] threshold;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if(n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n && trials should greater than 0");
        }
        this.n = n;
        this.trials = trials;
        threshold = new double[trials];

        for(int i = 0; i < trials; ++i) {
            Percolation p = new Percolation(n);
            int openCount = 0;
            while(!p.percolates()) {
                randomOpen(p);
                openCount++;
            }
            // System.out.println(openCount);
            threshold[i] = (double)(openCount)/(n*n);

        } 
    }

    private void randomOpen(Percolation p) {
        boolean openNode = true;
        int row = 0;
        int col = 0;

        // random row and col in range [1,n];
        while(openNode) {
            double ranRow = Math.random();
            ranRow = ranRow*n + 1;
            row = (int)ranRow;

            double ranCol = Math.random();
            ranCol = ranCol*n + 1;
            col = (int)ranCol;
            openNode = p.isOpen(row, col);
        }
        p.open(row, col);
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for(int i = 0; i < threshold.length; ++i) {
            sum += threshold[i];
        }
        return sum/trials;
        
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double x_trungbinh = mean();
        double a = 0;
        for (int i = 0; i < threshold.length; ++i) {
            a += (threshold[i] - x_trungbinh) * (threshold[i] - x_trungbinh);
        }
        return Math.sqrt(a/(trials-1));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96*stddev()/Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);
        // double a = ps.mean();
        System.out.println("mean:\t\t\t\t = " + ps.mean());
        System.out.println("stddev:\t\t\t\t = " + ps.stddev());
        System.out.println("95% confidence interval:\t = " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }

  
}
