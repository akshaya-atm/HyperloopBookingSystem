package com.akshaya.hyperloopbooking.booking;

import com.akshaya.hyperloopbooking.booking.pricing.FareBreakdown;
import com.akshaya.hyperloopbooking.booking.pricing.PricingStrategy;
import com.akshaya.hyperloopbooking.passenger.Passenger;
import com.akshaya.hyperloopbooking.passenger.PassengerController;
import com.akshaya.hyperloopbooking.route.RouteController;
import com.akshaya.hyperloopbooking.route.RouteResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookingModel {

    private final PassengerController passengerController;
    private final RouteController routeController;
    private final PricingStrategy pricingStrategy;
    private final Map<String, PodStatus> podInventory = Collections.synchronizedMap(new LinkedHashMap<>());

    public BookingModel(PassengerController passengerController, RouteController routeController,
                        PricingStrategy pricingStrategy) {
        this.passengerController = passengerController;
        this.routeController = routeController;
        this.pricingStrategy = pricingStrategy;
    }

    public void initPods(int limit) {
        podInventory.clear();
        for (int i = 1; i <= limit; i++) {
            String podId = String.format("POD-%03d", i);
            podInventory.put(podId, PodStatus.AVAILABLE);
        }
    }

    public boolean isQueueEmpty() {
        return passengerController.isQueueEmpty();
    }

    public boolean isRouteInitialized() {
        return routeController.isInitialized();
    }

    public boolean isInventoryInitialized() {
        return !podInventory.isEmpty();
    }

    public String peekNextPassengerDestination() {
        Passenger passenger = passengerController.peekNextPassenger();
        return passenger != null ? passenger.getDestination() : null;
    }

    private String getAvailablePodId() {
        synchronized (podInventory) {
            for (Map.Entry<String, PodStatus> entry : podInventory.entrySet()) {
                if (entry.getValue() == PodStatus.AVAILABLE) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    public List<DispatchResult> dispatchNextPod(String destination) {
        String podId = getAvailablePodId();
        if (podId == null) {
            throw new IllegalStateException("No pods available in inventory.");
        }

        RouteResult routeResult = routeController.findShortestRoute(destination);

        List<Passenger> group = passengerController.getPassengersForDestination(destination, 4);

        podInventory.put(podId, PodStatus.DISPATCHED);
        Pod pod = new Pod(podId, destination, group);

        List<DispatchResult> results = new ArrayList<>();
        for (Passenger passenger : group) {
            FareBreakdown fare = pricingStrategy.calculateFare(
                    routeResult.getTotalDistance(),
                    passenger.getAge()
            );
            results.add(new DispatchResult(pod, passenger, routeResult.getStops(), 
                    routeResult.getTotalDistance(), fare));
        }

        return results;
    }

    public void releasePod(String podId) {
        if (!podInventory.containsKey(podId)) {
            throw new IllegalArgumentException("Pod ID '" + podId + "' does not exist in inventory.");
        }
        if (podInventory.get(podId) != PodStatus.DISPATCHED) {
            throw new IllegalArgumentException("Pod ID '" + podId + "' is not currently dispatched.");
        }
        podInventory.put(podId, PodStatus.AVAILABLE);
    }

    public void removeStuckPassengers(String destination) {
        passengerController.getPassengersForDestination(destination, 4);
    }
}
