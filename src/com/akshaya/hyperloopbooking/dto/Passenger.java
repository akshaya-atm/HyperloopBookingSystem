package com.akshaya.hyperloopbooking.dto;

public class Passenger {
    private String name;
    private int age;
    private String destination;

    @Override
    public String toString() {
        return name + " " + age;
    }

    public Passenger(String name, int age, String destination) {
        this.name = name;
        this.age = age;
        this.destination = destination;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getDestination() {
        return this.destination;
    }
    

}
