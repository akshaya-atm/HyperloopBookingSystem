package com.akshaya.hyperloopbooking.booking;

import com.akshaya.hyperloopbooking.booking.pricing.FareBreakdown;
import com.akshaya.hyperloopbooking.passenger.Passenger;
import java.util.List;

public class DispatchResult {
    private final Pod pod;
    private final Passenger passenger;
    private final List<String> stops;
    private final double totalDistance;
    private final FareBreakdown fareBreakdown;

    public DispatchResult(Pod pod, Passenger passenger, List<String> stops, 
                          double totalDistance, FareBreakdown fareBreakdown) {
        this.pod = pod;
        this.passenger = passenger;
        this.stops = stops;
        this.totalDistance = totalDistance;
        this.fareBreakdown = fareBreakdown;
    }

    public Pod getPod() {
        return pod;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public List<String> getStops() {
        return stops;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public FareBreakdown getFareBreakdown() {
        return fareBreakdown;
    }
}
