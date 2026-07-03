package com.akshaya.hyperloopbooking.booking.pricing;

/**
 * Strategy interface for computing passenger ticket fares and details.
 */
public interface PricingStrategy {

    /**
     * Calculates the ticket fare details based on travel distance and passenger age.
     *
     * @param distanceKm the total route distance in kilometers
     * @param age        the passenger's age
     * @return FareBreakdown detailing base fare, discounts, and final fare
     */
    FareBreakdown calculateFare(double distanceKm, int age);
}
