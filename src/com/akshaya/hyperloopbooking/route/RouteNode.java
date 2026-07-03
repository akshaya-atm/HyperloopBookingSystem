package com.akshaya.hyperloopbooking.route;

import java.util.List;

/**
 * Represents a node in the shortest path search.
 * Tracks the current station, accumulated distance, and the path taken so far.
 */
public class RouteNode implements Comparable<RouteNode> {

    private final String station;
    private final double distance;
    private final List<String> path;

    public RouteNode(String station, double distance, List<String> path) {
        this.station = station;
        this.distance = distance;
        this.path = path;
    }

    public String getStation() {
        return station;
    }

    public double getDistance() {
        return distance;
    }

    public List<String> getPath() {
        return path;
    }

    @Override
    public int compareTo(RouteNode other) {
        return Double.compare(this.distance, other.distance);
    }
}
