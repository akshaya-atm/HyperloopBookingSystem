package com.akshaya.hyperloopbooking;
import java.util.*;

public class RoutesRepo {
    private String startLocation;
    private Set<String> routes;
    private Map<String, List<String>>neighbours;

    private static RoutesRepo routesRepo;
    private RoutesRepo(){
        this.neighbours = new HashMap<>();
        this.routes = new HashSet<>();
    }

    public static RoutesRepo getRoutesRepoInstance(){
        if(routesRepo==null){
            routesRepo = new RoutesRepo();
        }
        return routesRepo;
    }
    public void setStartLocation(String location){
        this.startLocation = location;
    }
    public String getStartLocation(){
        return this.startLocation;
    }
    public Set<String> getAllRoutes(){
        return routes;
    }
    public Map<String, List<String>>getAllNeighbours(){
        return neighbours;
    }
    public boolean isLocationValid(String location){
        return routes.contains(location);
    }
}
