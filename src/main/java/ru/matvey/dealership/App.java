package ru.matvey.dealership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public final class App {
    private App() {}
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.out.println("=== Dealership Service started successfully! ===");
    }
}
