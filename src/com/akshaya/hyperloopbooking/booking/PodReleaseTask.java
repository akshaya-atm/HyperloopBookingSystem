package com.akshaya.hyperloopbooking.booking;

public class PodReleaseTask implements Runnable {

    private final BookingModel bookingModel;
    private final String podId;

    public PodReleaseTask(BookingModel bookingModel, String podId) {
        this.bookingModel = bookingModel;
        this.podId = podId;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            bookingModel.releasePod(podId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
