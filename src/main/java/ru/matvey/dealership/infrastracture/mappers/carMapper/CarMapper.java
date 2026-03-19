package ru.matvey.dealership.infrastracture.mappers.carMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.infrastracture.jpa.CarEntity;
import ru.matvey.dealership.domain.details.Cost;

@Mapper(componentModel = "spring", uses = {DetailMapper.class})
public interface CarMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "model", source = "model")
    @Mapping(target = "body", source = "body")
    @Mapping(target = "carPaint", source = "color")
    @Mapping(target = "fuel", source = "fuel")
    @Mapping(target = "transmission", source = "transmission")
    @Mapping(target = "drivelayout", source = "driveLayout")
    @Mapping(target = "defaultCost", source = "baseCost")
    Car toDomain(CarEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "model", source = "model")
    @Mapping(target = "baseCost", expression = "java(car.getDefaultCost().value())")
    @Mapping(target = "body", source = "body")
    @Mapping(target = "color", source = "carPaint")
    @Mapping(target = "fuel", source = "fuel")
    @Mapping(target = "transmission", source = "transmission")
    @Mapping(target = "driveLayout", source = "drivelayout")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "removed", ignore = true)
    CarEntity toEntity(Car car);
}