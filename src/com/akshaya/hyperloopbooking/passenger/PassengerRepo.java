package com.akshaya.hyperloopbooking.passenger;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Singleton repository storing the passenger priority queue.
 * Sorted by age descending (oldest passengers first).
 */
public class PassengerRepo {

    private final PriorityQueue<Passenger> passengerQueue;
    private static PassengerRepo instance;

    private PassengerRepo() {
        // Sort by age descending (oldest first). If ages are equal, preserve insertion order/any order.
        this.passengerQueue = new PriorityQueue<>((p1, p2) -> Integer.compare(p2.getAge(), p1.getAge()));
    }

    public static PassengerRepo getInstance() {
        if (instance == null) {
            instance = new PassengerRepo();
        }
        return instance;
    }

    public void addPassenger(Passenger passenger) {
        passengerQueue.add(passenger);
    }

    public Passenger peek() {
        return passengerQueue.peek();
    }

    public Passenger poll() {
        return passengerQueue.poll();
    }

    public boolean isEmpty() {
        return passengerQueue.isEmpty();
    }

    public int size() {
        return passengerQueue.size();
    }

    public void clear() {
        passengerQueue.clear();
    }

    /**
     * Returns a new list containing all passengers sorted by priority (age descending).
     */
    public List<Passenger> getAllPassengersSorted() {
        PriorityQueue<Passenger> copy = new PriorityQueue<>(passengerQueue);
        List<Passenger> sorted = new ArrayList<>();
        while (!copy.isEmpty()) {
            sorted.add(copy.poll());
        }
        return sorted;
    }
}
