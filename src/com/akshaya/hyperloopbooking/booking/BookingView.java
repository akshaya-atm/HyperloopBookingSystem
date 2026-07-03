package com.akshaya.hyperloopbooking.booking;

import com.akshaya.hyperloopbooking.booking.pricing.FareBreakdown;
import com.akshaya.hyperloopbooking.passenger.Passenger;
import java.util.List;

/**
 * Handles all console output related to pod booking, boarding passes, and dispatch results.
 */
public class BookingView {

    /**
     * Prints a beautiful ASCII Boarding Pass for a passenger boarding a pod.
     */
    public void printBoardingPass(Pod pod, Passenger passenger, List<String> route, 
                                  double distance, FareBreakdown fareBreakdown) {
        String routeStr = String.join(" -> ", route);
        System.out.println("=============================================================");
        System.out.println("                HYPERLOOP BOARDING PASS                      ");
        System.out.println("=============================================================");
        System.out.printf("  %-15s : %s\n", "POD ID", pod.getPodId());
        System.out.printf("  %-15s : %s\n", "DESTINATION", pod.getDestination());
        System.out.printf("  %-15s : %s (Age: %d)\n", "PASSENGER", passenger.getName(), passenger.getAge());
        System.out.printf("  %-15s : %s\n", "ROUTE PATH", routeStr);
        System.out.printf("  %-15s : %.1f km\n", "TOTAL DISTANCE", distance);
        System.out.println("-------------------------------------------------------------");
        System.out.println("  FARE DETAIL:");
        System.out.printf("    %-15s : $%-8.2f\n", "Base Fare", fareBreakdown.getBaseFare());
        System.out.printf("    %-15s : %s (%.0f%%)\n", "Discount applied", 
                fareBreakdown.getDiscountName(), fareBreakdown.getDiscountPercent());
        System.out.printf("    %-15s : -$%-8.2f\n", "Savings", fareBreakdown.getDiscountAmount());
        System.out.printf("    %-15s : $%-8.2f\n", "Total Paid", fareBreakdown.getFinalFare());
        System.out.println("=============================================================");
        System.out.println();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }
}
