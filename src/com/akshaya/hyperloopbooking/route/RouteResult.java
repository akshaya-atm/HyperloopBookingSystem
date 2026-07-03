package com.akshaya.hyperloopbooking.route;

import java.util.List;

/**
 * Represents the result of a shortest-path (Dijkstra) computation.
 * Contains the ordered list of station stops and the total distance in km.
 */
public class RouteResult {

    private final List<String> stops;
    private final double totalDistance;

    public RouteResult(List<String> stops, double totalDistance) {
        this.stops = stops;
        this.totalDistance = totalDistance;
    }

    public List<String> getStops() {
        return stops;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    @Override
    public String toString() {
        return String.join(" -> ", stops) + " (" + String.format("%.1f", totalDistance) + " km)";
    }
}
