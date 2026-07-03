package com.akshaya.hyperloopbooking.route;

import com.akshaya.hyperloopbooking.util.ConsoleScanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RouteView {

    private final Scanner scanner;

    public RouteView() {
        this.scanner = ConsoleScanner.getInstance().getScanner();
    }

    public String getInitInput(String[] commandParts) {
        if (commandParts.length < 3) {
            throw new IllegalArgumentException("Format must be: INIT [start_location] [pod_limit]");
        }
        String startLocation = commandParts[1].trim();
        if (startLocation.isEmpty()) {
            throw new IllegalArgumentException("Start location cannot be empty.");
        }
        try {
            int podLimit = Integer.parseInt(commandParts[2]);
            if (podLimit <= 0) {
                throw new IllegalArgumentException("Pod limit must be a positive integer.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Pod limit must be a valid integer.");
        }
        return startLocation;
    }

    public List<String> getRoutesInput(int count) {
        List<String> rawRoutes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            while (true) {
                System.out.print("Enter Route " + (i + 1) + " (e.g., A B 120): ");
                String input = scanner.nextLine().trim();
                try {
                    validateRouteInput(input);
                    rawRoutes.add(input);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
        return rawRoutes;
    }

    private void validateRouteInput(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Route cannot be empty.");
        }
        String[] tokens = input.split("\\s+");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Route must be in the format: FROM TO DISTANCE (e.g., 'A B 120').");
        }
        if (tokens[0].equals(tokens[1])) {
            throw new IllegalArgumentException("Route cannot connect a station to itself.");
        }
        try {
            double distance = Double.parseDouble(tokens[2]);
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be a positive number.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Distance must be a valid number (e.g., 120 or 85.5).");
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public int getRouteCount() {
        String input = scanner.nextLine().trim();
        try {
            int count = Integer.parseInt(input);
            if (count <= 0) {
                throw new IllegalArgumentException("Route count must be greater than zero.");
            }
            return count;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Route count must be a valid integer.");
        }
    }
}
