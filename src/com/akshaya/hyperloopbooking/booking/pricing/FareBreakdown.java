package com.akshaya.hyperloopbooking.booking.pricing;

/**
 * Detailed breakdown of ticket pricing details.
 */
public class FareBreakdown {
    private final double baseFare;
    private final String discountName;
    private final double discountPercent;
    private final double discountAmount;
    private final double finalFare;

    public FareBreakdown(double baseFare, String discountName, double discountPercent, 
                         double discountAmount, double finalFare) {
        this.baseFare = baseFare;
        this.discountName = discountName;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.finalFare = finalFare;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public String getDiscountName() {
        return discountName;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getFinalFare() {
        return finalFare;
    }
}
