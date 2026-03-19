package ru.matvey.dealership.infrastracture.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.matvey.dealership.domain.orders.Status;
import ru.matvey.dealership.domain.orders.stockCarOrder.StockCarOrderStatusFactory;
import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveOrder;
import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveStatusFactory;
import ru.matvey.dealership.infrastracture.jpa.TestDriveOrderEntity;
import ru.matvey.dealership.infrastracture.jpa.CarEntity;
import ru.matvey.dealership.infrastracture.mappers.IdEntityMapper.IdEntityMapper;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {IdEntityMapper.class})
public interface TestDriveOrderMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "client", source = "clientId")
    @Mapping(target = "manager", source = "managerId")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "car", source = "carId", qualifiedByName = "uuidToCarEntity")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "removed", ignore = true)
    TestDriveOrderEntity toEntity(TestDriveOrder domain);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "clientId", source = "client")
    @Mapping(target = "managerId", source = "manager")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "carId", source = "car", qualifiedByName = "carToUUID")
    TestDriveOrder toDomain(TestDriveOrderEntity domain);

    @Named("uuidToCarEntity")
    default CarEntity uuidToCarEntity(UUID carId) {
        if (carId == null) return null;
        CarEntity car = new CarEntity();
        car.setId(carId);
        return car;
    }

    @Named("carToUUID")
    default UUID uuidToCarEntity(CarEntity car) {
        return car.getId();
    }

    default String uuidToCarEntity(Status status) {
        return status.getName();
    }

    default Status StringToStatus(String status) {
        TestDriveStatusFactory factory= new TestDriveStatusFactory();
        return factory.Create(status.toUpperCase());
    }
}