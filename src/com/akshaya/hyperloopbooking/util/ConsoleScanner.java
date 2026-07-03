package com.akshaya.hyperloopbooking.util;

import java.util.Scanner;

public class ConsoleScanner {

    private static ConsoleScanner instance;
    private final Scanner scanner;

    private ConsoleScanner() {
        this.scanner = new Scanner(System.in);
    }

    public static ConsoleScanner getInstance() {
        if (instance == null) {
            instance = new ConsoleScanner();
        }
        return instance;
    }

    public Scanner getScanner() {
        return scanner;
    }
}
