package com.akshaya.hyperloopbooking;

import com.akshaya.hyperloopbooking.booking.BookingController;
import com.akshaya.hyperloopbooking.passenger.PassengerController;
import com.akshaya.hyperloopbooking.route.RouteController;
import com.akshaya.hyperloopbooking.util.ConsoleScanner;
import java.util.Scanner;

public class HyperloopController {

    private final RouteController routeController;
    private final PassengerController passengerController;
    private final BookingController bookingController;
    private final Scanner scanner;

    public HyperloopController(RouteController routeController, PassengerController passengerController,
                               BookingController bookingController) {
        this.routeController = routeController;
        this.passengerController = passengerController;
        this.bookingController = bookingController;
        this.scanner = ConsoleScanner.getInstance().getScanner();
    }

    public void start() {
        showWelcomeMessage();

        while (true) {
            showCommandMenu();
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split("\\s+");
            String command = parts[0].toUpperCase();

            try {
                if (!command.equals("INIT") && !command.equals("EXIT")) {
                    if (!routeController.isInitialized()) {
                        System.out.println("Error: System is not initialized. Please run 'INIT [start_location] [pod_limit]' first.");
                        continue;
                    }
                }

                if (command.equals("INIT") && routeController.isInitialized()) {
                    System.out.println("Error: System is already initialized.");
                    continue;
                }

                switch (command) {
                    case "INIT":
                        if (parts.length < 3) {
                            System.out.println("Error: Format must be: INIT [start_location] [pod_limit]");
                        } else {
                            routeController.init(parts[1]);
                            int limit = Integer.parseInt(parts[2]);
                            bookingController.initPods(limit);
                        }
                        break;

                    case "ADD_ROUTES":
                        if (parts.length < 2) {
                            System.out.println("Error: Route count is missing. Format: ADD_ROUTES [count]");
                        } else {
                            int count = Integer.parseInt(parts[1]);
                            routeController.addRoutes(count);
                        }
                        break;

                    case "ADD_PASSENGER":
                        if (parts.length < 2) {
                            System.out.println("Error: Passenger count is missing. Format: ADD_PASSENGER [count]");
                        } else {
                            int count = Integer.parseInt(parts[1]);
                            passengerController.addPassengers(count);
                        }
                        break;

                    case "START_POD":
                        if (parts.length < 2) {
                            System.out.println("Error: Pod count is missing. Format: START_POD [count]");
                        } else {
                            int count = Integer.parseInt(parts[1]);
                            bookingController.startPods(count);
                        }
                        break;

                    case "PRINT_Q":
                        passengerController.printQueue();
                        break;

                    case "EXIT":
                        System.out.println("Thank you for using the Hyperloop Booking System. Goodbye!");
                        return;

                    default:
                        System.out.println("Error: Unknown command. Please try again.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Numeric argument must be a valid integer.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void showWelcomeMessage() {
        System.out.println();
        System.out.println("=========================================================");
        System.out.println("         Welcome to the Hyperloop Booking System");
        System.out.println("=========================================================");
    }

    private void showCommandMenu() {
        System.out.println("\n---------------------------------------------------------");
        System.out.println("Available Commands:");
        if (!routeController.isInitialized()) {
            System.out.println("  - INIT [start_location] [pod_limit] : Initialize hub & pods");
        } else {
            System.out.println("  - ADD_ROUTES [count]                : Add route connections");
            System.out.println("  - ADD_PASSENGER [count]             : Add passengers to queue");
            System.out.println("  - START_POD [count]                 : Start pods & dispatch");
            System.out.println("  - PRINT_Q                           : Print current queue");
        }
        System.out.println("  - EXIT                              : Exit application");
        System.out.println("---------------------------------------------------------");
        System.out.print("Enter command: ");
    }
}
