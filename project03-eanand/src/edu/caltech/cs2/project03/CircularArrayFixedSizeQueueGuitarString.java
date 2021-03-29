package edu.caltech.cs2.project03;

import edu.caltech.cs2.datastructures.CircularArrayFixedSizeQueue;
import java.util.Random;
import java.lang.Math;


public class CircularArrayFixedSizeQueueGuitarString {
    private edu.caltech.cs2.interfaces.IFixedSizeQueue data;
    private static final double energy_decay_factor = 0.996;
    private static final double sampling_rate = 44100;
    private static final Random rand = new Random();

    public CircularArrayFixedSizeQueueGuitarString(double frequency) {
        int samples = (int) Math.ceil(sampling_rate /frequency);
        this.data = new CircularArrayFixedSizeQueue(samples);
        for (int i = 0; i < sampling_rate; i++) {
            this.data.enqueue((double) 0);
        }
    }

    public int length() {
        return this.data.size();
    }

    public void pluck() {
        for (int i = 0; i < this.data.size(); i++) {
            this.data.dequeue();
        }
        for (int i = 0; i < this.data.size(); i++) {
            double noise = 2*((double) rand.nextFloat() - 0.5);
            this.data.enqueue(noise);
        }
    }

    public void tic() {
        double value1 = (double) this.data.dequeue();
        double value2 = (double) this.data.peek();
        double new_value = 0.5 * (value1 + value2) * energy_decay_factor;
        this.data.enqueue(new_value);
    }

    public double sample() {
        return (double) this.data.peek();
    }
}
