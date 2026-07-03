package com.akshaya.hyperloopbooking.booking.pricing;

/**
 * Implementation of PricingStrategy applying age-based discounts:
 * - Base rate: $0.50 per kilometer of travel distance
 * - Age < 12: 50% discount
 * - Age >= 60: 30% discount
 */
public class AgeDiscountPricingStrategy implements PricingStrategy {

    private static final double BASE_RATE_PER_KM = 0.50;

    @Override
    public FareBreakdown calculateFare(double distanceKm, int age) {
        double baseFare = distanceKm * BASE_RATE_PER_KM;
        String discountName = "Standard Fare";
        double discountPercent = 0.0;

        if (age < 12) {
            discountName = "Child Discount";
            discountPercent = 0.50;
        } else if (age >= 60) {
            discountName = "Senior Citizen Discount";
            discountPercent = 0.30;
        }

        double discountAmount = baseFare * discountPercent;
        double finalFare = baseFare - discountAmount;

        return new FareBreakdown(
                baseFare,
                discountName,
                discountPercent * 100.0,
                discountAmount,
                finalFare
        );
    }
}
