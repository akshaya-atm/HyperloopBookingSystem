package com.akshaya.hyperloopbooking.route;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Contains all route business logic:
 * - Setting and validating the start location.
 * - Adding weighted edges to the repository.
 * - Finding the shortest path using Dijkstra's Algorithm.
 * - Validating station names.
 */
public class RouteModel {

    private final RoutesRepo routesRepo;

    public RouteModel(RoutesRepo routesRepo) {
        this.routesRepo = routesRepo;
    }

    /**
     * Sets the start location for the hyperloop network.
     * The start location does not need to be in any route yet at this point.
     *
     * @throws IllegalArgumentException if location is null or empty
     */
    public void setStartLocation(String startLocation) {
        if (startLocation == null || startLocation.trim().isEmpty()) {
            throw new IllegalArgumentException("Start location cannot be empty.");
        }
        routesRepo.setStartLocation(startLocation.trim());
        routesRepo.clear();
        routesRepo.setStartLocation(startLocation.trim());
    }

    /**
     * Parses and adds a list of raw route strings to the graph.
     * Expected format per entry: "FROM TO DISTANCE" (e.g., "A B 120")
     * Can be called multiple times to keep adding more routes.
     *
     * @throws IllegalArgumentException if no routes provided or start location not set
     */
    public void addRoutes(List<String> rawRoutes) {
        if (routesRepo.getStartLocation() == null) {
            throw new IllegalStateException("Start location must be set using INIT before adding routes.");
        }
        if (rawRoutes == null || rawRoutes.isEmpty()) {
            throw new IllegalArgumentException("At least one route must be provided.");
        }
        for (String raw : rawRoutes) {
            String[] tokens = raw.trim().split("\\s+");
            String from = tokens[0];
            String to = tokens[1];
            double distance = Double.parseDouble(tokens[2]);
            routesRepo.addEdge(from, to, distance);
        }
    }

    /**
     * Finds the shortest path (by total km) from the configured start location
     * to the given destination using Dijkstra's Algorithm.
     *
     * @param destination the target station
     * @return RouteResult containing ordered stops and total distance in km
     * @throws IllegalArgumentException if destination is invalid or same as start
     * @throws IllegalStateException    if no path exists to the destination
     */
    public RouteResult findShortestRoute(String destination) {
        String start = routesRepo.getStartLocation();

        if (destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination cannot be empty.");
        }
        if (destination.equals(start)) {
            throw new IllegalArgumentException("Destination cannot be the same as the start location.");
        }
        if (!routesRepo.isValidStation(destination)) {
            throw new IllegalArgumentException("Destination '" + destination + "' is not a known station.");
        }

        Map<String, List<RouteEdge>> adjacencyList = routesRepo.getAdjacencyList();
        PriorityQueue<RouteNode> minHeap = new PriorityQueue<>();
        Map<String, Double> settled = new HashMap<>();

        List<String> initialPath = new ArrayList<>();
        initialPath.add(start);
        minHeap.add(new RouteNode(start, 0.0, initialPath));

        while (!minHeap.isEmpty()) {
            RouteNode current = minHeap.poll();

            if (settled.containsKey(current.getStation())) continue;
            settled.put(current.getStation(), current.getDistance());

            if (current.getStation().equals(destination)) {
                return new RouteResult(current.getPath(), current.getDistance());
            }

            List<RouteEdge> edges = adjacencyList.getOrDefault(
                    current.getStation(), Collections.emptyList());

            for (RouteEdge edge : edges) {
                if (!settled.containsKey(edge.getDestination())) {
                    List<String> newPath = new ArrayList<>(current.getPath());
                    newPath.add(edge.getDestination());
                    minHeap.add(new RouteNode(
                            edge.getDestination(),
                            current.getDistance() + edge.getDistance(),
                            newPath));
                }
            }
        }

        throw new IllegalStateException(
                "No route found from '" + start + "' to '" + destination + "'.");
    }

    public boolean isValidStation(String station) {
        return routesRepo.isValidStation(station);
    }

    public String getStartLocation() {
        return routesRepo.getStartLocation();
    }

    public boolean isRoutesEmpty() {
        return routesRepo.getAdjacencyList().isEmpty();
    }

    public boolean isInitialized() {
        return routesRepo.getStartLocation() != null && !routesRepo.getAdjacencyList().isEmpty();
    }
}
