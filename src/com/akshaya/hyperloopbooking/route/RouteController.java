package com.akshaya.hyperloopbooking.route;

import java.util.List;

public class RouteController {

    private final RouteModel routeModel;
    private final RouteView routeView;

    public RouteController(RouteModel routeModel, RouteView routeView) {
        this.routeModel = routeModel;
        this.routeView = routeView;
    }

    public void init(String startLocation) {
        try {
            routeModel.setStartLocation(startLocation);
            routeView.showMessage("Start location set to: " + startLocation);

            if (routeModel.isRoutesEmpty()) {
                routeView.showMessage("First-time setup: Adding routes is mandatory.");
                int count = 0;
                while (true) {
                    try {
                        System.out.print("Enter number of routes to add: ");
                        count = routeView.getRouteCount();
                        break;
                    } catch (IllegalArgumentException e) {
                        routeView.showError(e.getMessage());
                    }
                }
                addRoutes(count);
            }
        } catch (IllegalArgumentException e) {
            routeView.showError(e.getMessage());
        }
    }

    public void addRoutes(int count) {
        try {
            List<String> rawRoutes = routeView.getRoutesInput(count);
            routeModel.addRoutes(rawRoutes);
            routeView.showMessage(count + " routes added successfully.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            routeView.showError(e.getMessage());
        }
    }

    public RouteResult findShortestRoute(String destination) {
        return routeModel.findShortestRoute(destination);
    }

    public boolean isValidStation(String station) {
        return routeModel.isValidStation(station);
    }

    public String getStartLocation() {
        return routeModel.getStartLocation();
    }

    public boolean isInitialized() {
        return routeModel.isInitialized();
    }
}
