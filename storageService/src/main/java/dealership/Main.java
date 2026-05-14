package dealership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class Main {
    public Main() {}
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("=== Dealership Service started successfully! ===");
    }
}