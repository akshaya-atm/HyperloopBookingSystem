package com.akshaya.hyperloopbooking;

import com.akshaya.hyperloopbooking.dto.Passenger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HyperloopBookingView {
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcomeMessage() {
        System.out.println();
        System.out.println();

        System.out.println("=========================================================");
        System.out.println("        Welcome to hyperloop Booking System");
        System.out.println("=========================================================");
        System.out.println();
        System.out.println();

        System.out.println("Initialize Routes");
    }

    public RouteDetails getRouteDetails() {
        System.out.print("Enter init command(INIT 7 A): ");
        String[] initDetails = scanner.nextLine().trim().split("\\s+");

        if (initDetails.length < 3) {
            throw new IllegalArgumentException("Init command should be INIT ROUTE_COUNT START_LOCATION");
        }
        if (!initDetails[0].equals("INIT")) {
            throw new IllegalArgumentException("First command should be INIT");
        }

        int routeCount;
        try {
            routeCount = Integer.parseInt(initDetails[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Route count should be a number");
        }
        if (routeCount <= 0) {
            throw new IllegalArgumentException("Route count should be greater than zero");
        }

        String startLocation = initDetails[2];
        List<String> routes = new ArrayList<>();

        for (int i = 0; i < routeCount; i++) {
            while (true) {
                System.out.println("Route " + (i + 1));
                String route = scanner.nextLine().trim();
                try {
                    validateRouteFormat(route);
                    routes.add(route);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return new RouteDetails(startLocation, routes);
    }

    private void validateRouteFormat(String route) {
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
        System.out.println("Enter the command");
        System.out.println("To Add passsenger: 'ADD_PASSENGER X' X is number of passengers ");
        System.out.println("To start a pod: 'START_POD X' X number of pods");
        System.out.println("To Know the queue List: 'PRINT_Q'");
        String command  =  scanner.nextLine().trim();
        return command;
    }
    public void showMessage(String message){
        System.out.println(message);
        System.out.println();
    }

    public String getpassengerDetails() {
        System.out.println( "Enter passenger Details  as NAME AGE Destination");
        String passengerDetails = scanner.nextLine().trim();
        return passengerDetails;
    }

    public void printPassengerList(List<Passenger> remainingPassengersList) {
        if(remainingPassengersList.isEmpty()){
            System.out.println("No more passengers");
        } else {
        for(Passenger passenger:remainingPassengersList){
            System.out.println(passenger);
        }
    }
    }

    public void printPodResult(String passengerName, List<String> path) {
        System.out.println(passengerName + " " + String.join(" ", path));
    }
}
