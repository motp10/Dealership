package ru.matvey.dealership;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.matvey.dealership.abstraction.CarsRepository;
import ru.matvey.dealership.abstraction.DetailsRepository;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Body.BodyType;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.color.CarPaint;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
import ru.matvey.dealership.domain.details.fuel.Fuel;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class CarCreationDatabaseAuditTest {
    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void database(DynamicPropertyRegistry registry) {
        registry.add("spring.liquibase.enabled", () -> "true");
        registry.add("spring.liquibase.contexts", () -> "demo");
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @LocalServerPort int port;
    @Autowired JdbcTemplate jdbc;
    @Autowired DetailsRepository details;
    @Autowired CarsRepository cars;
    @Autowired PlatformTransactionManager transactions;

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"brand\":\"BMW\",\"model\":\"M5\",\"bodyType\":\"Sedan\",\"paint\":\"Black\",\"fuel\":\"Petrol\",\"transmission\":\"Automatic\",\"driveLayout\":\"AWD\"}",
            "{\"brand\":\"BMW\",\"model\":\"M5\",\"bodyType\":\"BODY\",\"paint\":\"Black\",\"fuel\":\"Petrol\",\"transmission\":\"Automatic\",\"driveLayout\":\"AWD\"}",
            "{}",
            "{\"brand\":\"\",\"model\":\"\"}"
    })
    void invalidRequestReturns400AndDoesNotInsert(String json) throws Exception {
        var before = jdbc.queryForObject("select count(*) from cars", Long.class);
        try (var client = HttpClient.newHttpClient()) {
            var response = client.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/cars"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json)).build(), HttpResponse.BodyHandlers.ofString());
            assertThat(response.statusCode()).isEqualTo(400);
        }
        assertThat(jdbc.queryForObject("select count(*) from cars", Long.class)).isEqualTo(before);
    }

    @Test
    void lookupUsesDiscriminatorAndValue() {
        assertThat(jdbc.queryForObject("select count(*) from details where type = 'BODY' and value = 'Sedan'", Integer.class)).isEqualTo(1);
        assertThat(details.getDetails(new BodyType("Sedan"))).hasSize(1);
        assertThat(details.getDetails(new BodyType("BODY"))).isEmpty();
    }

    @Test
    void repositoryCanCommitCarWhenCorrectDetailsAreProvided() {
        var id = new TransactionTemplate(transactions).execute(status -> {
            var car = new Car("BMW", "M5",
                    (Body) details.findById(detailId(1)).orElseThrow(),
                    (CarPaint) details.findById(detailId(4)).orElseThrow(),
                    (Fuel) details.findById(detailId(2)).orElseThrow(),
                    (Transmission) details.findById(detailId(3)).orElseThrow(),
                    (DriveLayout) details.findById(detailId(5)).orElseThrow(), new Cost(123456));
            cars.add(car);
            return car.getId();
        });
        try {
            var row = jdbc.queryForMap("select * from cars where id = ?", id);
            assertThat(row.get("brand")).isEqualTo("BMW");
            assertThat(row.get("model")).isEqualTo("M5");
            assertThat(row.get("base_cost")).isEqualTo(123456.0);
            assertThat(row.get("body_id")).isEqualTo(detailId(1));
            assertThat(row.get("color_id")).isEqualTo(detailId(4));
            assertThat(row.get("fuel_id")).isEqualTo(detailId(2));
            assertThat(row.get("transmission_id")).isEqualTo(detailId(3));
            assertThat(row.get("drive_layout_id")).isEqualTo(detailId(5));
            assertThat(row.get("created_at")).isNotNull();
            assertThat(row.get("updated_at")).isNotNull();
            assertThat(row.get("removed")).isEqualTo(false);
        } finally {
            cars.delete(id);
        }
    }

    @Test
    void httpCreatesCarWithWheelPriceAndAllForeignKeys() throws Exception {
        var wheelId = UUID.randomUUID();
        jdbc.update("insert into details(id, type, value, cost, removed) values (?, 'WHEEL', 'Alloy', 5000, false)", wheelId);
        UUID carId = null;
        try (var client = HttpClient.newHttpClient()) {
            var response = client.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/cars"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(validJson("M5", "Alloy", "123456")))
                    .build(), HttpResponse.BodyHandlers.ofString());
            assertThat(response.statusCode()).as(response.body()).isEqualTo(200);
            carId = UUID.fromString(response.body().replace("\"", ""));
            var row = jdbc.queryForMap("select * from cars where id = ?", carId);
            assertThat(row.get("base_cost")).isEqualTo(123456.0);
            assertThat(row.get("wheel_id")).isEqualTo(wheelId);
            assertThat(row.get("body_id")).isEqualTo(detailId(1));
            assertThat(row.get("fuel_id")).isEqualTo(detailId(2));
            assertThat(row.get("transmission_id")).isEqualTo(detailId(3));
            assertThat(row.get("color_id")).isEqualTo(detailId(4));
            assertThat(row.get("drive_layout_id")).isEqualTo(detailId(5));
            assertThat(row.get("created_at")).isNotNull();
            var get = client.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/cars/" + carId)).GET().build(), HttpResponse.BodyHandlers.ofString());
            assertThat(get.statusCode()).isEqualTo(200);
            assertThat(get.body()).contains("Alloy", "323456");
        } finally {
            if (carId != null) cars.delete(carId);
            jdbc.update("delete from details where id = ?", wheelId);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"missingWheel", "negativePrice", "blankBrand", "missingBody"})
    void invalidConfigurationsDoNotWriteCars(String scenario) throws Exception {
        String json = validJson("M5", null, "123456");
        json = switch (scenario) {
            case "missingWheel" -> validJson("M5", "Unknown", "123456");
            case "negativePrice" -> validJson("M5", null, "-1");
            case "blankBrand" -> json.replace("BMW", "   ");
            case "missingBody" -> json.replace("Sedan", "Unknown");
            default -> throw new AssertionError(scenario);
        };
        invalidRequestReturns400AndDoesNotInsert(json);
    }

    private String validJson(String model, String wheel, String price) {
        return """
                {"brand":"BMW","model":"%s","bodyType":"Sedan","paint":"Black",
                 "fuel":"Petrol","transmission":"Automatic","driveLayout":"AWD",
                 "wheel":%s,"baseCost":%s}
                """.formatted(model, wheel == null ? "null" : "\"" + wheel + "\"", price);
    }

    private UUID detailId(int suffix) {
        return UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa" + suffix);
    }
}
