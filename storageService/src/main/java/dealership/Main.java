package dealership;

import dealership.runtimehints.MyRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
@ImportRuntimeHints(MyRuntimeHints.class)
public class Main {
    public Main() {}
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("=== Dealership Service started successfully! ===");
    }
}