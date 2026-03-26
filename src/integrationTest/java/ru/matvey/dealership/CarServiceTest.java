package ru.matvey.dealership;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.matvey.dealership.abstraction.CarsRepository;
import ru.matvey.dealership.abstraction.DetailsRepository;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.services.CarService;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Body.BodyType;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.color.CarPaint;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
import ru.matvey.dealership.domain.details.fuel.Fuel;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CarServiceIT {

    @Autowired
    private CarService carService;

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private DetailsRepository detailsRepository;

    @Test
    void addCar_ShouldSaveCarToDatabase_WhenDetailsAreCompatible() {
        String targetModel = "M5";

        saveTestDetails(targetModel);

        CarComplictation complication = new CarComplictation(
                1500,
                "BMW",
                targetModel,
                "Sedan",      // bodyType
                "Petrol",     // fuelType
                "Automatic",  // transmissionType
                "AWD",        // drivelayoutType
                "Blue",       // color
                "M-Sport"    // wheel (если используется в логике)
        );

        UUID generatedId = carService.addCar(complication);

        assertThat(generatedId).isNotNull();

        Optional<Car> savedCar = carsRepository.findById(generatedId);

        assertThat(savedCar).isPresent();
        assertThat(savedCar.get().getModel()).isEqualTo(targetModel);
        assertThat(savedCar.get().getBody().getType()).isEqualTo("Sedan");
    }

    private void saveTestDetails(String model) {
        // Здесь мы сохраняем детали, передавая в них совместимую модель, 
        // чтобы конструктор Car не выбросил DomainValidationException
        detailsRepository.add(new Body(new BodyType("Modern sedan"), new Cost(5000), Set.of(model)));
//        detailsRepository.add(new Fuel("Petrol", "98 Octane", 0, Set.of(model)));
//        detailsRepository.add(new Transmission("Automatic", "ZF8", 3000, Set.of(model)));
//        detailsRepository.add(new DriveLayout("AWD", "XDrive", 4000, Set.of(model)));
//        detailsRepository.add(new CarPaint("Blue", "Marina Bay Blue", 2000, Set.of(model)));
    }
}