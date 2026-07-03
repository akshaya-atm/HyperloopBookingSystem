package com.akshaya.hyperloopbooking.route;

/**
 * Represents a weighted directed connection between two stations.
 * Stores the destination station and the distance in kilometers.
 */
public class RouteEdge {
    private final String destination;
    private final double distance;

    public RouteEdge(String destination, double distance) {
        this.destination = destination;
        this.distance = distance;
    }

    public String getDestination() {
        return destination;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return destination + " (" + distance + " km)";
    }
}
