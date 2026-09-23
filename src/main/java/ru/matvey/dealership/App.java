package ru.matvey.dealership;

import java.util.Arrays;
import java.util.stream.Stream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class App {
    private App() {}

    public static void main(String[] args) {
        if (Arrays.asList(args).contains("--migrate")) {
            // Use the same artifact and configuration, then close the one-off process.
            String[] migrationArgs = Stream.concat(
                    Arrays.stream(args).filter(arg -> !arg.equals("--migrate")),
                    Stream.of("--spring.main.web-application-type=none", "--spring.liquibase.enabled=true"))
                    .toArray(String[]::new);
            try (var context = SpringApplication.run(App.class, migrationArgs)) {
                // Liquibase completes during context initialization; failures exit non-zero.
            }
        } else {
            SpringApplication.run(App.class, args);
        }
    }
}
