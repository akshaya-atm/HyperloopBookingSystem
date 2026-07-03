package com.akshaya.hyperloopbooking.passenger;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles all passenger business logic:
 * - Queueing passengers.
 * - Extracting matching passengers for pod boarding.
 */
public class PassengerModel {

    private final PassengerRepo passengerRepo;

    public PassengerModel(PassengerRepo passengerRepo) {
        this.passengerRepo = passengerRepo;
    }

    /**
     * Adds a pre-validated passenger to the queue.
     */
    public void addPassenger(String name, int age, String destination) {
        Passenger passenger = new Passenger(name, age, destination);
        passengerRepo.addPassenger(passenger);
    }

    /**
     * Returns a sorted copy of the passenger queue.
     */
    public List<Passenger> getPassengerQueue() {
        return passengerRepo.getAllPassengersSorted();
    }

    public boolean isQueueEmpty() {
        return passengerRepo.isEmpty();
    }

    public Passenger peekNextPassenger() {
        return passengerRepo.peek();
    }

    /**
     * Retrieves up to 'limit' passengers going to the specified destination.
     * The passengers are pulled from the queue (removed) and returned in priority order.
     * All non-matching polled passengers are restored back to the queue.
     */
    public List<Passenger> getPassengersForDestination(String destination, int limit) {
        List<Passenger> matched = new ArrayList<>();
        List<Passenger> unmatched = new ArrayList<>();

        while (!passengerRepo.isEmpty() && matched.size() < limit) {
            Passenger p = passengerRepo.poll();
            if (p.getDestination().equals(destination)) {
                matched.add(p);
            } else {
                unmatched.add(p);
            }
        }

        // Put back unmatched passengers to retain original priority
        for (Passenger p : unmatched) {
            passengerRepo.addPassenger(p);
        }

        return matched;
    }
}
