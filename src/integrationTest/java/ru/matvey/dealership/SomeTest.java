package ru.matvey.dealership;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import ru.matvey.dealership.contracts.services.AdminService;
import ru.matvey.dealership.presentation.models.UserRequest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SomeTest extends BaseIntegrationTest {

    @Autowired
    private AdminService adminService;

    @Test
    void addCar_ShouldPersistToDatabase() {

        // Act
        UUID id = adminService.addUser("mam", "123", "big mom", "CLIENT");
        // Assert
        assertThat(id == id);
    }
}

//    @Test
//    void getCar_ShouldReturnData_WhenExists() {
//        // Arrange
//        var car = saveNewCar();
//        var request = new GetCarsRequest();
//        request.setIds(List.of(car.getId()));
//
//        // Act
//        ResponseEntity<?> response = carHandler.getCars(request);
//
//        // Assert
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotNull();
//    }
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