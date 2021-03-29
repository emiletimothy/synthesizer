package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.Iterator;

public class LinkedDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {
    private int size;
    private Node<E> head_ptr;
    private Node<E> tail_ptr;

    public LinkedDeque() {
        this.head_ptr = null;
        this.tail_ptr = null;
        this.size = 0;
    }

    private static class Node<E> {
        public final E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(E data) {
            this(data, null, null);
        }
        public Node(E data, Node<E> prev_node, Node<E> next_node) {
            this.data = data;
            this.prev = prev_node;
            this.next = next_node;
        }
    }

    public String toString() {
        if (this.size == 0) {
            return "[]";
        }
        StringBuilder result = new StringBuilder("[");
        Node<E> curr = this.head_ptr;
        for (int i = 0; i < this.size; i++) {
            result.append(curr.data).append(", ");
            curr = curr.next;
        }
        result = new StringBuilder(result.substring(0, result.length() - 2));
        return (result + "]");
    }

    public void addFront(E e) {
        Node<E> newNode = new Node<>(e);
        if (this.size == 0) {
            this.head_ptr = newNode;
            this.tail_ptr = this.head_ptr;
        }
        else if (this.size == 1) {
            this.head_ptr = newNode;
            this.tail_ptr.prev = this.head_ptr;
            this.head_ptr.next = this.tail_ptr;
        }
        else {
            this.head_ptr.prev = newNode;
            newNode.next = this.head_ptr;
            this.head_ptr = newNode;
        }
        this.size++;
    }

    public void addBack(E e) {
        Node<E> newNode = new Node<>(e);
        if (this.size == 0) {
            this.head_ptr = newNode;
            this.tail_ptr = this.head_ptr;
        }
        else if (this.size == 1) {
            this.tail_ptr = newNode;
            this.tail_ptr.prev = this.head_ptr;
            this.head_ptr.next = this.tail_ptr;
        }
        else {
            newNode.prev = this.tail_ptr;
            this.tail_ptr.next = newNode;
            this.tail_ptr = newNode;
        }
        this.size++;
    }

    public E removeFront() {
        if (this.size == 0) {
            return null;
        }
        E value = this.head_ptr.data;
        if (this.size == 1) {
            this.head_ptr = null;
            this.tail_ptr = null;
        }
        else if (this.size == 2) {
            this.tail_ptr.prev = null;
            this.head_ptr = this.tail_ptr;
        }
        else {
            this.head_ptr = this.head_ptr.next;
            this.head_ptr.prev = null;
        }
        this.size--;
        return value;
    }

    public E removeBack() {
        if (this.size == 0) {
            return null;
        }
        else if (this.size == 1) {
            E value = this.tail_ptr.data;
            this.head_ptr = null;
            this.tail_ptr = null;
            this.size--;
            return value;
        }
        else if (this.size == 2) {
            E value = this.tail_ptr.data;
            this.head_ptr.next = null;
            this.tail_ptr = head_ptr;
            this.size--;
            return value;
        }
        else {
            E value = this.tail_ptr.data;
            this.tail_ptr = this.tail_ptr.prev;
            this.tail_ptr.next = null;
            this.size--;
            return value;
        }
    }

    public boolean enqueue(E e) {
        int old_size = this.size;
        addFront(e);
        return old_size < this.size;
    }

    public E dequeue() {
        return removeBack();
    }

    public boolean push(E e) {
        int old_size = this.size;
        addBack(e);
        return old_size < this.size;
    }

    public E pop() {
        if (this.size == 0) {
            return null;
        }
        return removeBack();
    }

    public E peek() {
        if (this.size == 0) {
            return null;
        }
        if (this.size == 1) {
            return this.head_ptr.data;
        }
        return this.tail_ptr.data;
    }

    public E peekFront() {
        if (this.size == 0) {
            return null;
        }
        return this.head_ptr.data;
    }

    public E peekBack() {
        return peek();
    }

    public Iterator<E> iterator() {
        return new LinkedDequeIterator();
    }

    private class LinkedDequeIterator implements Iterator<E> {
        private int currentIndex;
        private Node<E> curr;

        public LinkedDequeIterator() {
            this.currentIndex = 0;
            this.curr = (LinkedDeque.this).head_ptr;
        }

        public boolean hasNext() {
            return this.currentIndex < (LinkedDeque.this).size;
        }

        public E next() {
            E element = curr.data;
            this.curr = this.curr.next;
            this.currentIndex++;
            return element;
        }
    }

    public int size() {
        return this.size;
    }
}
