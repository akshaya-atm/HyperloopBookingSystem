package com.akshaya.hyperloopbooking.passenger;

import com.akshaya.hyperloopbooking.util.ConsoleScanner;
import java.util.List;
import java.util.Scanner;

public class PassengerView {

    private final Scanner scanner;

    public PassengerView() {
        this.scanner = ConsoleScanner.getInstance().getScanner();
    }

    public void showPassengerPrompt(int index) {
        System.out.print("Enter passenger " + index + " details (NAME AGE DESTINATION): ");
    }

    public String getPassengerInput() {
        return scanner.nextLine().trim();
    }

    public void printPassengerQueue(List<Passenger> passengers) {
        if (passengers.isEmpty()) {
            System.out.println("No passengers in the queue.");
            return;
        }
        System.out.println("Current Passenger Queue (ordered by priority/age):");
        for (Passenger passenger : passengers) {
            System.out.println("  - " + passenger.getName() + " (Age: " + passenger.getAge() 
                    + ") -> Destination: " + passenger.getDestination());
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }
}
