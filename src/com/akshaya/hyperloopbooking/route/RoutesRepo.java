package com.akshaya.hyperloopbooking.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Singleton repository storing the weighted route graph.
 * Maintains a set of all known stations and an adjacency list of RouteEdges.
 */
public class RoutesRepo {

    private String startLocation;
    private Set<String> stations;
    private Map<String, List<RouteEdge>> adjacencyList;

    private static RoutesRepo instance;

    private RoutesRepo() {
        this.stations = new HashSet<>();
        this.adjacencyList = new HashMap<>();
    }

    public static RoutesRepo getInstance() {
        if (instance == null) {
            instance = new RoutesRepo();
        }
        return instance;
    }

    public void clear() {
        this.stations.clear();
        this.adjacencyList.clear();
        this.startLocation = null;
    }

    public void setStartLocation(String location) {
        this.startLocation = location;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public Set<String> getStations() {
        return stations;
    }

    public Map<String, List<RouteEdge>> getAdjacencyList() {
        return adjacencyList;
    }


    public void addEdge(String from, String to, double distance) {
        stations.add(from);
        stations.add(to);

        adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(new RouteEdge(to, distance));
        adjacencyList.computeIfAbsent(to, k -> new ArrayList<>()).add(new RouteEdge(from, distance));
    }

    public boolean isValidStation(String station) {
        return stations.contains(station);
    }
}
