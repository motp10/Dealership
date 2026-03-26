package ru.matvey.dealership;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.operations.DetailOperation;
import ru.matvey.dealership.contracts.services.DetailService;
import ru.matvey.dealership.contracts.services.UserService;
import ru.matvey.dealership.infrastracture.jparepositories.JpaUserRepository;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SomeTest extends BaseIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DetailService detailService;

    @Test
    @Transactional
    void addCar_ShouldPersistToDatabase() {
        UUID id = userService.addUser("mam", "123", "big mom", "CLIENT");
        userService.deleteUser(id);
        var users = userRepository.findAll();
        assertThat(users.size() == 0);
    }

    @Autowired
    private JpaUserRepository userRepository;

    @Test
    @Transactional
    void shouldLoadSeedUsers() {
        var users = userRepository.findAll();
        userService.addUser("mam", "123", "big mom", "CLIENT");
        assertThat(users.size() == 1);
    }
    @Test
    void addCar_ShouldSaveCarToDatabase_WhenDetailsAreCompatible() {
        String targetModel = "model";

        UUID bodyId = detailService.addDetail(new DetailOperation.Request("body", "body", 10, Set.of(targetModel)));
        detailService.addDetail(new DetailOperation.Request("fuel", "fuel", 10, Set.of(targetModel)));
        detailService.addDetail(new DetailOperation.Request("transmission", "transmission", 10, Set.of(targetModel)));
        detailService.addDetail(new DetailOperation.Request("drivelayout", "drivelayout", 10, Set.of(targetModel)));
        detailService.addDetail(new DetailOperation.Request("fuel", "fuel", 10, Set.of(targetModel)));
        detailService.addDetail(new DetailOperation.Request("wheel", "wheel", 10, Set.of(targetModel)));
        detailService.addDetail(new DetailOperation.Request("interior", "interior", 10, Set.of(targetModel)));


        CarComplictation complication = new CarComplictation(
                1500,
                "brand",
                targetModel,
                "body",
                "fuel",
                "transmission",
                "drive",
                "color",
                "wheel"
        );

        Assertions.assertThat(detailService.getAllDetails().size() == 7);
        var x = detailService.getDetail(bodyId);
        assertThat(x.cost() == 10);
    }
}


//
//    @Test
//    void updateCar_ShouldModifyDatabase() {
//        // Arrange
//        var car = saveNewCar();
//        var carModel = new CarModelDto(
//                "Updated Brand",
//                car.getModel(),
//                car.getBasePrice(),
//                car.getColor(),
//                vehicleEnumMapper.toDto(car.getBodyType()),
//                vehicleEnumMapper.toDto(car.getFuelType()),
//                car.getEnginePower(),
//                car.getEngineVolume(),
//                vehicleEnumMapper.toDto(car.getTransmissionType()),
//                vehicleEnumMapper.toDto(car.getDriveType()));
//
//        var request = new UpdateCarRequest();
//        request.setCarId(car.getId());
//        request.setCarModel(carModel);
//
//        // Act
//        ResponseEntity<?> response = carHandler.updateCar(request);
//
//        // Assert
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        var updatedCar = carJpaRepository.findById(car.getId()).orElseThrow();
//        assertThat(updatedCar.getBrand()).isEqualTo("Updated Brand");
//    }
//
//    @Test
//    void removeCar_ShouldDeleteFromDatabase() {
//        // Arrange
//        var car = saveNewCar();
//        var request = new RemoveCarRequest();
//        request.setId(car.getId());
//
//        // Act
//        ResponseEntity<?> response = carHandler.removeCar(request);
//
//        // Assert
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(carJpaRepository.findById(car.getId())).isEmpty();
//    }
//
//    @Test
//    @Transactional
//    void addCarWithDetails_ShouldSaveManyToManyRelationship() {
//        // Arrange
//        var sparePart =
//[3/19/26 7:02 PM] Шапка: sparePartJpaRepository.saveAndFlush(createSparePartEntity());
//
//        var car = createCarEntity();
//        car.setDetails(List.of(sparePart));
//
//        carJpaRepository.saveAndFlush(car);
//
//        // Assert
//        var foundCar = carJpaRepository.findById(car.getId()).orElseThrow();
//        assertThat(carJpaRepository.count()).isEqualTo(1);
//        assertThat(foundCar.getDetails().getFirst().getName()).isEqualTo(sparePart.getName());
//    }
//
//    private CarEntity saveNewCar() {
//        var car = createCarEntity();
//        return carJpaRepository.saveAndFlush(car);
//    }
//}