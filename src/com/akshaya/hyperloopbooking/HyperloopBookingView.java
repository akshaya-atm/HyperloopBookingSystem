package com.akshaya.hyperloopbooking;

import com.akshaya.hyperloopbooking.dto.Passenger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HyperloopBookingView {
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcomeMessage() {
        System.out.println();
        System.out.println("=========================================================");
        System.out.println("         Welcome to the Hyperloop Booking System");
        System.out.println("=========================================================");
        System.out.println();
        System.out.println("--- Initialize Routes ---");
    }

    public RouteDetails getRouteDetails() {
        System.out.print("Enter initialization command (e.g., INIT 3 A): ");
        String[] initDetails = scanner.nextLine().trim().split("\\s+");

        if (initDetails.length < 3) {
            throw new IllegalArgumentException("Initialization command format must be: INIT [route_count] [start_location]");
        }
        if (!initDetails[0].equals("INIT")) {
            throw new IllegalArgumentException("The initialization command must start with 'INIT'.");
        }

        int routeCount;
        try {
            routeCount = Integer.parseInt(initDetails[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Route count must be a valid integer.");
        }
        if (routeCount <= 0) {
            throw new IllegalArgumentException("Route count must be greater than zero.");
        }

        String startLocation = initDetails[2];
        List<String> routes = new ArrayList<>();

        for (int i = 0; i < routeCount; i++) {
            while (true) {
                System.out.print("Enter Route " + (i + 1) + " (e.g., A B): ");
                String route = scanner.nextLine().trim();
                try {
                    validateRouteFormat(route);
                    routes.add(route);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

        return new RouteDetails(startLocation, routes);
    }

    private void validateRouteFormat(String route) {
        if (route.isEmpty()) {
            throw new IllegalArgumentException("Route cannot be empty.");
        }
        String[] rts = route.split("\\s+");
        if (rts.length != 2) {
            throw new IllegalArgumentException("Route must contain exactly two space-separated locations (e.g., 'A B').");
        }
        if (rts[0].equals(rts[1])) {
            throw new IllegalArgumentException("Route cannot connect a location to itself.");
        }
    }

    public static class RouteDetails {
        private String startLocation;
        private List<String> routes;

        RouteDetails(String startLocation, List<String> routes) {
            this.startLocation = startLocation;
            this.routes = routes;
        }

        public String getStartLocation() {
            return startLocation;
        }

        public List<String> getRoutes() {
            return routes;
        }
    }

    public String getCommand() {
        System.out.println("\n---------------------------------------------------------");
        System.out.println("Available Commands:");
        System.out.println("  - ADD_PASSENGER X  : Add X passengers to the queue");
        System.out.println("  - START_POD X      : Start X pods for the next passengers");
        System.out.println("  - PRINT_Q          : Print the current passenger queue");
        System.out.println("  - EXIT             : Exit the application");
        System.out.println("---------------------------------------------------------");
        System.out.print("Enter command: ");
        return scanner.nextLine().trim();
    }

    public void showMessage(String message){
        System.out.println(message);
        System.out.println();
    }

    public String getpassengerDetails() {
        System.out.print("Enter passenger details (NAME AGE DESTINATION): ");
        return scanner.nextLine().trim();
    }

    public void printPassengerList(List<Passenger> remainingPassengersList) {
        if (remainingPassengersList.isEmpty()) {
            System.out.println("No more passengers in the queue.");
        } else {
            System.out.println("Current Passenger Queue:");
            for (Passenger passenger : remainingPassengersList) {
                System.out.println("  - " + passenger);
            }
        }
    }

    public void printPodResult(String passengerName, List<String> path) {
        System.out.println(passengerName + " " + String.join(" ", path));
    }
}
