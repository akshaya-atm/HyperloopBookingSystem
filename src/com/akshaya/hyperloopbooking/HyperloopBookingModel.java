package com.akshaya.hyperloopbooking;

import com.akshaya.hyperloopbooking.dto.Passenger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HyperloopBookingModel {

    private PassengerRepo passengerRepo;
    private RoutesRepo routesRepo;

    HyperloopBookingModel(PassengerRepo passengerRepo, RoutesRepo routesRepo) {
        this.passengerRepo = passengerRepo;
        this.routesRepo = routesRepo;
    }

    public boolean hasRoutes() {
        return !routesRepo.getAllRoutes().isEmpty();
    }

    public void addStartLocation(String location) {
        routesRepo.setStartLocation(location);
    }

    public boolean addRoutes(List<String> routes) {
        Set<String> routesList = routesRepo.getAllRoutes();
        Map<String, List<String>> neighBours = routesRepo.getAllNeighbours();
        for (String route : routes) {
            if (route.isEmpty()) {
                throw new IllegalArgumentException("Route should not be empty");
            }
            String[] rts = route.split("\\s+");
            if (rts.length != 2) {
                throw new IllegalArgumentException("Route should be FROM TO");
            }
            if (rts[0].equals(rts[1])) {
                throw new IllegalArgumentException("Route cannot connect same location");
            }
            routesList.add(rts[0]);
            routesList.add(rts[1]);
            if (neighBours.containsKey(rts[0])) {
                neighBours.get(rts[0]).add(rts[1]);
            } else {
                List<String> tempList = new ArrayList<>();
                tempList.add(rts[1]);
                neighBours.put(rts[0], tempList);
            }
            if (neighBours.containsKey(rts[1])) {
                neighBours.get(rts[1]).add(rts[0]);
            } else {
                List<String> tempList = new ArrayList<>();
                tempList.add(rts[0]);
                neighBours.put(rts[1], tempList);
            }
        }
        return true;
    }

    public void addPassengers(List<Passenger> passenger) {
        passengerRepo.addPassengers(passenger);

    }

    public List<Passenger> getRemainingPassengersList() {
        return passengerRepo.getRemainiPassengers();
    }

    public Passenger startNextPod() {
        if (getRemainingPassengersList().isEmpty()) {
            throw new IllegalStateException("Passenger queue is empty");
        }
        return passengerRepo.getPassenger();

    }

    public List<String> findShortestRoute(String destination) {
        return getShortestRoute(routesRepo.getStartLocation(), destination);
    }

    private List<String> getShortestRoute(String startLocation, String destination) {
        List<String> path = new ArrayList<>();
        ArrayDeque<String> queue = new ArrayDeque<>();
        Map<String, List<String>> neighBours = routesRepo.getAllNeighbours();
        Map<String, Boolean> visited = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        for (String route : routesRepo.getAllRoutes()) {
            visited.put(route, false);
        }
        queue.add(startLocation);
        parent.put(startLocation, null);
        visited.put(startLocation, true);
        while (!queue.isEmpty()) {
            String curLocation = queue.poll();
            if (curLocation.equals(destination)) {

                return buildPath(parent, destination);
            }
            List<String> curNeighBours = neighBours.get(curLocation);
            if (curNeighBours != null) {
                for (String curNeighBour : curNeighBours) {
                    if (!visited.getOrDefault(curNeighBour, false)) {
                        queue.add(curNeighBour);
                        parent.put(curNeighBour, curLocation);
                        visited.put(curNeighBour, true);
                    }
                }
            }
        }
        throw new IllegalStateException("Could not reach the destination");
    }

    private List<String> buildPath(Map<String, String> parent, String destination) {
        List<String> path = new ArrayList<>();
        String current = destination;
        while (current != null) {
            path.add(current);
            current = parent.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    public List<Passenger> getPassengerInQueue() {
        return null;
    }

    public boolean isValidRoute(String location) {
        return routesRepo.getAllRoutes().contains(location);
    }

}
