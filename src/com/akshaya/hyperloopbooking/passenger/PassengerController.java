package com.akshaya.hyperloopbooking.passenger;

import com.akshaya.hyperloopbooking.route.RouteController;
import java.util.List;

public class PassengerController {

    private final PassengerModel passengerModel;
    private final PassengerView passengerView;
    private final RouteController routeController;

    public PassengerController(PassengerModel passengerModel, PassengerView passengerView, 
                               RouteController routeController) {
        this.passengerModel = passengerModel;
        this.passengerView = passengerView;
        this.routeController = routeController;
    }

    public void addPassengers(int count) {
        if (count <= 0) {
            passengerView.showError("Passenger count must be greater than zero.");
            return;
        }
        if (!routeController.isInitialized()) {
            passengerView.showError("System is not initialized. Please set INIT and ADD_ROUTES first.");
            return;
        }

        for (int i = 0; i < count; i++) {
            while (true) {
                passengerView.showPassengerPrompt(i + 1);
                String input = passengerView.getPassengerInput();
                try {
                    parseAndAddPassenger(input);
                    break;
                } catch (IllegalArgumentException e) {
                    passengerView.showError(e.getMessage());
                }
            }
        }
        passengerView.showMessage(count + " passengers added to queue.");
    }

    private void parseAndAddPassenger(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger details cannot be empty.");
        }

        String[] tokens = input.trim().split("\\s+");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Expected format: NAME AGE DESTINATION (e.g., 'Alice 25 C').");
        }

        String name = tokens[0];
        int age;
        try {
            age = Integer.parseInt(tokens[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age must be a valid integer.");
        }

        if (age <= 0) {
            throw new IllegalArgumentException("Age must be a positive integer.");
        }

        String destination = tokens[2];
        if (destination.equals(routeController.getStartLocation())) {
            throw new IllegalArgumentException("Destination cannot be the same as the start location.");
        }
        if (!routeController.isValidStation(destination)) {
            throw new IllegalArgumentException(
                    "Destination '" + destination + "' is not a valid station in the routes.");
        }

        passengerModel.addPassenger(name, age, destination);
    }

    public void printQueue() {
        List<Passenger> passengers = passengerModel.getPassengerQueue();
        passengerView.printPassengerQueue(passengers);
    }

    public boolean isQueueEmpty() {
        return passengerModel.isQueueEmpty();
    }

    public Passenger peekNextPassenger() {
        return passengerModel.peekNextPassenger();
    }

    public List<Passenger> getPassengersForDestination(String destination, int limit) {
        return passengerModel.getPassengersForDestination(destination, limit);
    }
}
