package com.akshaya.hyperloopbooking;

import com.akshaya.hyperloopbooking.dto.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class PassengerRepo {

    private PriorityQueue<Passenger> passengerQueue; 
   private static PassengerRepo passengerRepo;
    private PassengerRepo(){
        this.passengerQueue = new PriorityQueue<>((p1,p2)->p2.getAge()-p1.getAge());
    }

    public static  PassengerRepo getPassengerInstance(){
        if(passengerRepo==null){
            passengerRepo = new PassengerRepo();
        }
        return passengerRepo;
    }
    public boolean addPassengers(List<Passenger> passengers){
        for(Passenger passenger:passengers){
            passengerQueue.add(passenger);
        }
        return true;
    }
    public Passenger getPassenger(){
        return passengerQueue.poll();
    }
    public List<Passenger> getRemainiPassengers(){
        return new ArrayList<>(passengerQueue);
    }

}
