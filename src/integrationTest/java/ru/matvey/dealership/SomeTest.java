package ru.matvey.dealership;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Body.BodyInfo;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.Transmission.TransmissionInfo;
import ru.matvey.dealership.domain.details.Wheel.Wheel;
import ru.matvey.dealership.domain.details.Wheel.WheelInfo;
import ru.matvey.dealership.domain.details.color.CarPaint;
import ru.matvey.dealership.domain.details.color.PaintInfo;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutInfo;
import ru.matvey.dealership.domain.details.fuel.Fuel;
import ru.matvey.dealership.domain.details.fuel.FuelInfo;
import ru.matvey.dealership.infrastracture.jparepositories.JpaDetailRepository;
import ru.matvey.dealership.presentation.models.CarConfigurationRequest;

import java.util.List;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SomeTest extends BaseIntegrationTest {

    @Autowired
    private JpaDetailRepository detailRepository;


    @Test
    void adminShouldSuccessfullyAddAndSearchCars() throws Exception {
        prepareDetails();

        CarConfigurationRequest carRequest = new CarConfigurationRequest(
            "Tesla",
            "Model S",
            "Sedan",
            "Red",
            "Electric",
            "Auto",
            "AWD",
            "Sport",
            null
        );

        mockMvc.perform(post("/api/cars")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/cars/search")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].brand").value("Tesla"));
    }

    @Test
    void userShouldNotBeAbleToAddCar() throws Exception {
        CarConfigurationRequest carRequest = new CarConfigurationRequest(
                "Tesla", "Model X", "Sedan", "Black", "Electric", "Auto", "AWD", "Sport", null
        );

        mockMvc.perform(post("/api/cars")
                        .with(jwt().authorities(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(status().isForbidden()); // Ожидаем 403 Forbidden
    }

    @Test
    void shouldReturnAllDetailsFromDatabase() throws Exception {
        prepareDetails();

        mockMvc.perform(get("/api/details")
                        .with(jwt().authorities(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_ADMIN"))))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$").isArray())

                .andExpect(jsonPath("$.length()").value(6))

                .andExpect(jsonPath("$[?(@.detailInfo == 'Sedan')]").exists())
                .andExpect(jsonPath("$[?(@.detailInfo == 'Red')]").exists())
                .andExpect(jsonPath("$[?(@.detailInfo == 'Electric')]").exists());
    }

    @Test
    void shouldFindDetailsByType() {
        Set<String> models = Set.of("Model S");
        Body body = new Body(new BodyInfo("Sedan"), new Cost(1000), models);
        CarPaint paint = new CarPaint(new PaintInfo("Red"), new Cost(500), models);
        prepareDetails();
        List<Detail> foundBodies = detailRepository.findByType("BODY");
        System.out.println(foundBodies.get(0).Type());
    }

    private void prepareDetails() {
        Set<String> models = Set.of("Model S", "Model X");
        detailRepository.save(new Body(new BodyInfo("Sedan"), new Cost(1000), models));
        detailRepository.save(new CarPaint(new PaintInfo("Red"), new Cost(500), models));
        detailRepository.save(new Fuel(new FuelInfo("Electric"), new Cost(2000), models));
        detailRepository.save(new Transmission(new TransmissionInfo("Auto"), new Cost(1500), models));
        detailRepository.save(new DriveLayout(new DriveLayoutInfo("AWD"), new Cost(1200), models));
        detailRepository.save(new Wheel(new WheelInfo("Sport"), new Cost(1200), models));
    }
}