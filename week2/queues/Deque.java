import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Deque<Item> implements Iterable<Item> {
    private Item[] deque;
    private int initSizeOfArray = 2;
    private int front;
    private int rear;
    private int N;     // number of element in deque

    // construct an empty deque
    public Deque() {
        this.N = 0;
        this.front = -1;
        this.rear = -1;
        deque = (Item[])new Object[initSizeOfArray];  // create a deque with init size
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (N == 0);
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Can't add null argument");
        // when queue is full
        if (front == 0 && rear == deque.length - 1 || front == rear + 1) {
            // double the array of deque when it's full
            resize(deque.length * 2);
        }
        // when queue is empty
        if (front == -1 && rear == -1) {
            front = 0;
            rear = 0;
            deque[front] = item;
        }
        // front = 0, so we add to last of the array (circular array)
        else if (front == 0) {
            front = deque.length - 1;
            deque[front] = item;
        }
        else {
            front--;
            deque[front] = item;
        }
        N++;
    }
    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Can't add null argument");
        // if the array is full, double it's size
        if (front == 0 && rear == deque.length - 1 || front == rear + 1) {
            resize(deque.length * 2);
        }
        // when the array of deque is empty
        if (front == -1 && rear == -1) {
            front = 0;
            rear = 0;
            deque[rear] = item;
        }
        // rear point to the last item of array, so we add to the first element of array (circular array)
        else if (rear == deque.length - 1) {
            rear = 0;
            deque[rear] = item;
        }
        else {
            rear++;
            deque[rear] = item;
        }
        N++;
    }
    // remove and return the item from the front
    public Item removeFirst() {
        Item item;
        if (N > 0 && N == deque.length / 4) {
            resize(deque.length / 2);
        }
        if (isEmpty()) {
            // throw Exception
            throw new NoSuchElementException("Deque underflow");
        }
        else if (front == rear) {
            item = deque[front];
            deque[front] = null;
            front = -1;
            rear = -1;
            N--;
        }
        else if (front == deque.length - 1) {
            item = deque[front];
            deque[front] = null; // avoid loitering
            front = 0; // wrap-around
            N--;
        }
        else {
            item = deque[front];
            deque[front] = null;
            front++;
            N--;
        }
        return item;
    }
    // remove and return the item from the back
    public Item removeLast() {
        Item item;
        // if number of element in deque less than deque/4 length, divide it
        if (N > 0 && N == deque.length / 4) {
            resize(deque.length / 2);
        }
        if (isEmpty()) {
            // throw exception
            throw new NoSuchElementException("Deque underflow");
        }
        else if (front == rear) {
            item = deque[front];
            deque[front] = null;
            front = -1;
            rear = -1;
            N--;
        }
        else if (rear == 0) {
            item = deque[rear];
            deque[rear] = null;
            rear = deque.length - 1;
            N--;
        }
        else {
            item = deque[rear];
            deque[rear] = null;
            rear--;
            N--;
        }
        return item;
    }

    public void display() {
        int i = front;
        while (i != rear) {
            System.out.println("deque[" + i + "] = " + deque[i]);
            i = (i+1) % deque.length;
        }
        System.out.println("deque[" + rear + "] = " + deque[rear]);
    }

    public void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        // loop to copy all element start with index = 0 in new array
        for (int i = 0; i < N; i++) {
            copy[i] = deque[(front+i) % deque.length];
        }
        deque = copy;
        front = 0;
        rear = N-1;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext()  { return i < N;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = deque[(i + front) % deque.length];
            i++;
            return item;
        }
    }
    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque1 = new Deque<String>();
    }
}
