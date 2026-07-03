package com.akshaya.hyperloopbooking.booking;

import java.util.List;

public class BookingController {

    private final BookingModel bookingModel;
    private final BookingView bookingView;

    public BookingController(BookingModel bookingModel, BookingView bookingView) {
        this.bookingModel = bookingModel;
        this.bookingView = bookingView;
    }

    public void initPods(int limit) {
        bookingModel.initPods(limit);
        bookingView.showMessage("Pod inventory initialized with " + limit + " pods.");
    }

    public void startPods(int count) {
        if (count <= 0) {
            bookingView.showError("Pod count must be greater than zero.");
            return;
        }

        if (!bookingModel.isRouteInitialized() || !bookingModel.isInventoryInitialized()) {
            bookingView.showError("System is not initialized. Please set INIT and ADD_ROUTES first.");
            return;
        }

        int podsStarted = 0;

        for (int i = 0; i < count; i++) {
            if (bookingModel.isQueueEmpty()) {
                if (podsStarted == 0) {
                    bookingView.showMessage("No passengers in the queue to dispatch.");
                } else {
                    bookingView.showMessage("Passenger queue is now empty. Dispatched " 
                            + podsStarted + " pods.");
                }
                return;
            }

            String destination = bookingModel.peekNextPassengerDestination();

            try {
                List<DispatchResult> results = bookingModel.dispatchNextPod(destination);
                if (results != null && !results.isEmpty()) {
                    Pod pod = results.get(0).getPod();
                    bookingView.showMessage("Starting Pod " + pod.getPodId() + " with " 
                            + results.size() + " passenger(s) to " + destination);

                    for (DispatchResult res : results) {
                        bookingView.printBoardingPass(
                                res.getPod(),
                                res.getPassenger(),
                                res.getStops(),
                                res.getTotalDistance(),
                                res.getFareBreakdown()
                        );
                    }
                    podsStarted++;

                    Thread releaseThread = new Thread(new PodReleaseTask(bookingModel, pod.getPodId()));
                    releaseThread.start();
                }
            } catch (IllegalStateException e) {
                if (e.getMessage().contains("No pods available")) {
                    bookingView.showError(e.getMessage());
                    return;
                }
                bookingModel.removeStuckPassengers(destination);
                bookingView.showError("Could not reach the destination for passenger group: " 
                        + e.getMessage());
            }
        }

        if (podsStarted > 0) {
            bookingView.showMessage("Successfully dispatched " + podsStarted + " pods.");
        }
    }
}
