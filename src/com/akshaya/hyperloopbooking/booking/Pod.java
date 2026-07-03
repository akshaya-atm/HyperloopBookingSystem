package com.akshaya.hyperloopbooking.booking;

import com.akshaya.hyperloopbooking.passenger.Passenger;
import java.util.List;

/**
 * Represents a Hyperloop Pod carrying grouped passengers to a single destination.
 */
public class Pod {

    private final String podId;
    private final String destination;
    private final List<Passenger> passengers;

    public Pod(String podId, String destination, List<Passenger> passengers) {
        this.podId = podId;
        this.destination = destination;
        this.passengers = passengers;
    }

    public String getPodId() {
        return podId;
    }

    public String getDestination() {
        return destination;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    @Override
    public String toString() {
        return podId + " -> Destination: " + destination + " (Passengers: " + passengers.size() + ")";
    }
}
