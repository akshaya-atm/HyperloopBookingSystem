package com.akshaya.hyperloopbooking;

import com.akshaya.hyperloopbooking.dto.Passenger;
import java.util.ArrayList;
import java.util.List;

public class HyperloopBookingController {
    private HyperloopBookingView hyperloopView;
    private HyperloopBookingModel hyperloopModel;

    public void init() {
        hyperloopView = new HyperloopBookingView();
        PassengerRepo passengerRepo = PassengerRepo.getPassengerInstance();
        RoutesRepo routesRepo = RoutesRepo.getRoutesRepoInstance();
        hyperloopModel = new HyperloopBookingModel(passengerRepo, routesRepo);
        start();
    }

    private void start() {
        hyperloopView.showWelcomeMessage();
        while(true){
        try {
            HyperloopBookingView.RouteDetails routeDetails = hyperloopView.getRouteDetails();
            hyperloopModel.addStartLocation(routeDetails.getStartLocation());
            hyperloopModel.addRoutes(routeDetails.getRoutes());
            break;
        } catch (IllegalArgumentException e) {
            hyperloopView.showMessage(e.getMessage());
        }
    }
        while (true) {
            try {
                String command = hyperloopView.getCommand();
                String commandParts[] = command.split("\\s+");
                switch (commandParts[0]) {
                    case "ADD_PASSENGER":
                        if (commandParts.length < 2)
                            throw new IllegalArgumentException("Number of Pods to start is missing");
                        List<Passenger> passengers = new ArrayList<>();
                        int count;
                        try {
                            count = Integer.parseInt(commandParts[1]);

                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Passenger Count should be a number");
                        }
                        if (count <= 0) {
                            throw new IllegalArgumentException("Count should be greater than zero");
                        }
                        for (int i = 0; i < count; i++) {
                            String passenger = hyperloopView.getpassengerDetails();
                            passengers.add(parsePassenger(passenger));
                        }
                        hyperloopModel.addPassengers(passengers);

                        break;
                    case "START_POD":
                        if (commandParts.length < 2)
                            throw new IllegalArgumentException("Number of Pods to start is missing");
                        int numberOfPods;

                        try {
                            numberOfPods = Integer.parseInt(commandParts[1]);

                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Number of Pods to start should be a number");
                        }
                        if (numberOfPods <= 0) {
                            throw new IllegalArgumentException("Number of Pods should be greater than zero");
                        }
                        for (int i = 0; i < numberOfPods; i++) {
                            Passenger passenger = hyperloopModel.startNextPod();
                            try {
                                List<String> route = hyperloopModel.findShortestRoute(passenger.getDestination());
                                hyperloopView.printPodResult(passenger.getName(), route);
                            } catch (IllegalStateException e) {
                                System.out.println(passenger.getName() + " " + e.getMessage());
                            }
                        }
                        break;
                    case "PRINT_Q":
                        hyperloopView.printPassengerList(hyperloopModel.getRemainingPassengersList());
                        break;
                    case "EXIT":
                        hyperloopView.showMessage("Thank you");
                        return;
                        
                    default:
                        throw new IllegalArgumentException("INVALID COMMAND");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                hyperloopView.showMessage(e.getMessage());
            }
        }

    }

    private Passenger parsePassenger(String passenger) {

        String[] passengerDetails = passenger.split("\\s+");
        if (passengerDetails.length < 3) {
            throw new IllegalArgumentException("Passenger details should be NAME AGE DESTINATION");
        }
        int age;
        try {
            age = Integer.parseInt(passengerDetails[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Passenger age should be a number");
        }
        if(!hyperloopModel.isValidRoute(passengerDetails[2]))
            throw new IllegalArgumentException("Destination is not available in routes");
        return new Passenger(passengerDetails[0], age, passengerDetails[2]);

    }
}
