package agh.ics.oop;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        try {
            Application.launch(SimulationConfiguration.class, args);
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
