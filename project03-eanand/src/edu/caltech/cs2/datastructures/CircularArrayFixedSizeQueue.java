package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IFixedSizeQueue;
import java.util.Iterator;

public class CircularArrayFixedSizeQueue<E> implements IFixedSizeQueue<E> {
    private E[] data;
    private int front;
    private int size;

    public CircularArrayFixedSizeQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        this.data = (E[]) new Object[capacity];
        this.front = 0;
        this.size = 0;
    }

    public boolean isFull() {
        return this.size == this.data.length;
    }

    public int capacity() {
        return this.data.length;
    }

    public boolean enqueue(E e) {
        if (isFull()) {
            return false;
        }
        int back = (this.front + this.size);
        if (back >= this.data.length) {
            back = back % this.data.length;
        }
        this.data[back] = e;
        this.size++;
        return true;
    }

    public E dequeue() {
        if (size() == 0) {
            return null;
        }
        E value = this.data[this.front];
        if (this.front == this.data.length - 1) {
            this.front = 0;
        }
        else {
            this.front++;
        }
        this.size--;
        return value;
    }

    public E peek() {
        if (size() == 0) {
            return null;
        }
        return this.data[this.front];
    }

    public String toString() {
        if (size() == 0) {
            return "[]";
        }
        StringBuilder result = new StringBuilder("[");
        int curr = this.front;
        for (int i = 0; i < size(); i++) {
            result.append(this.data[curr]).append(", ");
            if (curr == this.data.length - 1) {
                curr = 0;
            }
            else {
                curr++;
            }
        }
        result = new StringBuilder(result.substring(0, result.length() - 2));
        return (result + "]");
    }

    public int size() {
        return this.size;
    }

    public Iterator<E> iterator() {
        return new CircularArrayFixedSizeQueueIterator();
    }

    private class CircularArrayFixedSizeQueueIterator implements Iterator<E> {
        private int first;
        private int curr;
        private final int size;

        public CircularArrayFixedSizeQueueIterator() {
            this.first = (CircularArrayFixedSizeQueue.this).front;
            this.size = (CircularArrayFixedSizeQueue.this).size();
            this.curr = 0;
        }

        public boolean hasNext() {
            return (this.curr < size);
        }

        public E next() {
            if (!hasNext()) {
                return null;
            }
            E value = (CircularArrayFixedSizeQueue.this).data[this.first];
            if (this.first == (CircularArrayFixedSizeQueue.this).data.length - 1) {
                this.first = 0;
            }
            else {
                this.first++;
            }
            this.curr++;
            return value;
        }
    }
}
