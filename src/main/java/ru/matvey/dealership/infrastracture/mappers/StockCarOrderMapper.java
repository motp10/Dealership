package ru.matvey.dealership.infrastracture.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.matvey.dealership.domain.orders.Status;
import ru.matvey.dealership.domain.orders.stockCarOrder.OrderStockCar;
import ru.matvey.dealership.domain.orders.stockCarOrder.StockCarOrderStatusFactory;
import ru.matvey.dealership.infrastracture.jpa.CarEntity;
import ru.matvey.dealership.infrastracture.jpa.StockCarOrderEntity;
import ru.matvey.dealership.infrastracture.mappers.IdEntityMapper.IdEntityMapper;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {IdEntityMapper.class})
public interface StockCarOrderMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "client", source = "clientId")
    @Mapping(target = "manager", source = "managerId")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "car", source = "carId", qualifiedByName = "uuidToCarEntity")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "removed", ignore = true)
    StockCarOrderEntity toEntity(OrderStockCar domain);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "clientId", source = "client")
    @Mapping(target = "managerId", source = "manager")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "carId", source = "car", qualifiedByName = "carToUUID")
    OrderStockCar toDomain(StockCarOrderEntity entity);
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

    default String statusToString(Status status) {
        return status.getName();
    }
    default Status StringToStatus(String status) {
        StockCarOrderStatusFactory factory= new StockCarOrderStatusFactory();
        return factory.Create(status.toUpperCase());
    }
}