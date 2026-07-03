package com.akshaya.hyperloopbooking.passenger;

/**
 * Data Transfer Object representing a passenger.
 */
public class Passenger {
    private final String name;
    private final int age;
    private final String destination;

    public Passenger(String name, int age, String destination) {
        this.name = name;
        this.age = age;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return name + " " + age;
    }
}
