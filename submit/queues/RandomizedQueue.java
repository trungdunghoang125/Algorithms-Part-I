import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // number of element in RandomizedQueue
    private int N;
    // array to store item of queue
    private Item[] randomizedQueue;
    // construct an empty randomized queue
    public RandomizedQueue() {
        this.N = 0;
        randomizedQueue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (N == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Can't enqueue null item");
        if (N == randomizedQueue.length) {
            resize(randomizedQueue.length * 2);
        }
        randomizedQueue[N] = item;
        N++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue empty!");
        }
        int index = StdRandom.uniform(N-1);
        Item output = randomizedQueue[index];
        randomizedQueue[index] = randomizedQueue[N-1];
        randomizedQueue[N-1] = null;
        N--;
        return output;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = randomizedQueue[i];
        }
        randomizedQueue = copy;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue empty!");
        }
        int index = StdRandom.uniform(N);
        return randomizedQueue[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current = 0;
        private int[] iterorder;

        public RandomizedQueueIterator() {
            if (N > 0) {
                iterorder = new int[N];
                for (int i = 0; i < N; i++)
                    iterorder[i] = i;
                StdRandom.shuffle(iterorder, 0, N - 1);
            }
        }

        public boolean hasNext() {
            return current < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current >= N)
                throw new java.util.NoSuchElementException();
            Item item = (Item) randomizedQueue[iterorder[current]];
            current = current + 1;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue1 = new RandomizedQueue<Integer>();
        randomizedQueue1.enqueue(1);
        randomizedQueue1.enqueue(2);
        randomizedQueue1.enqueue(3);
        randomizedQueue1.enqueue(4);
        System.out.println(randomizedQueue1.sample());
    }

}