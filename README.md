# Hyperloop Booking System

A console-based Low-Level Design (LLD) project written in Java simulating a Hyperloop Pod Booking and Ticketing System. The project follows a strict, modular Model-View-Controller (MVC) architecture separated into distinct feature packages.

---

## Features

1. **Shortest-Path Routing (Dijkstra's Algorithm)**: Computes the shortest travel path based on track distance (km) over a multi-hop weighted network.
2. **Strict Age-Based Priority Queue**: Organizes passenger boarding priority using a max-heap Priority Queue, ensuring senior citizens and children board first.
3. **Smart Pod Grouping**: Automatically groups up to 4 passengers sharing the same destination as the highest-priority passenger.
4. **Dynamic Age-Based Pricing**: Calculates base fares ($0.50/km) and breaks down discounts (50% off for children <12, 30% off for seniors >=60). Generates detailed ASCII boarding passes.
5. **Limited Pod Inventory & Auto-Release**: Tracks a fixed number of physical pods. Spawns a separate background thread (`Thread` / `Runnable`) for each dispatched pod to simulate travel and automatically return it to the available pool after 3 seconds.
6. **Robust Input Validation**: Immediate validation preventing destination same-as-start location errors on input, age checks, and formatting errors.
7. **Strict Initialization Sequence**: Enforces setup commands before operational commands, displaying dynamic menu listings to guide the user.

---

## Project Structure

```text
src/
├── App.java                               # Bootstraps the application components
│
└── com/akshaya/hyperloopbooking/
    ├── HyperloopController.java           # CLI coordinator & main command loop
    │
    ├── route/                             # FEATURE: Pathfinding & Track Setup
    │   ├── RouteNode.java                 # Search node helper for Dijkstra
    │   ├── RouteEdge.java                 # Connection representation (destination, distance)
    │   ├── RouteResult.java               # Result container (stops, total distance)
    │   ├── RoutesRepo.java                # Adjacency list graph storage
    │   ├── RouteView.java                 # CLI views for track entry
    │   ├── RouteModel.java                # Graph logic & pathfinder
    │   └── RouteController.java           # Coordinator for routes
    │
    ├── passenger/                         # FEATURE: Queue Management
    │   ├── Passenger.java                 # Passenger data object
    │   ├── PassengerRepo.java             # Max-heap sorted storage
    │   ├── PassengerView.java             # View queue formatters
    │   ├── PassengerModel.java            # Parse, validate & extract logic
    │   └── PassengerController.java       # Coordinates queue entries
    │
    ├── booking/                           # FEATURE: Dispatch & Tickets
    │   ├── Pod.java                       # Pod data representation
    │   ├── PodStatus.java                 # Enum (AVAILABLE, DISPATCHED)
    │   ├── PodReleaseTask.java            # Background thread to auto-return pods
    │   ├── DispatchResult.java            # Boarding pass detail DTO
    │   ├── BookingView.java               # Generates ASCII boarding passes
    │   ├── BookingModel.java              # Grouping & dispatch logic
    │   ├── BookingController.java         # Orchestrates dispatches
    │   └── pricing/
    │       ├── PricingStrategy.java       # Pricing calculation interface
    │       └── AgeDiscountPricingStrategy.java # Child & SeniorCitizen discounts
    │
    └── util/                              # UTILITIES
        └── ConsoleScanner.java            # Singleton scanner for console input
```

---

## Commands

### 1. ADMIN Setup (Available before initialization)
* **`INIT [start_location] [pod_limit]`**  
  Sets the start station hub and limits the total physical pods available in inventory (e.g. `INIT A 2`).
  * *Note: First-time setup immediately prompts for initial routes.*

### 2. Operational CLI (Available after initialization)
* **`ADD_ROUTES [count]`**  
  Prompts for `count` bidirectional route details one-by-one with inline validation and retry (e.g. `A B 100`).
* **`ADD_PASSENGER [count]`**  
  Prompts for `count` passenger details one-by-one (Format: `NAME AGE DESTINATION`).
* **`START_POD [count]`**  
  Attempts to start `count` pods. Groups up to 4 passengers per pod going to the same destination, computes route, calculates fare, issues ASCII boarding passes, and auto-releases pods after 3 seconds.
* **`PRINT_Q`**  
  Prints all passengers currently waiting in the queue, sorted by age descending.
* **`EXIT`**  
  Terminates the application.

---

## Compiling & Running

Compile the project from the root folder:
```bash
javac -d bin -sourcepath src src/App.java
```

Run the application:
```bash
java -cp bin App
```
