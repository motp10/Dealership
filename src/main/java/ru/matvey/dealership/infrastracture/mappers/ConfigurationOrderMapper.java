package ru.matvey.dealership.infrastracture.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.orders.Status;
import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;
import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrderStatusFactory;
import ru.matvey.dealership.infrastracture.jpa.ComplictationOrderEntity;
import ru.matvey.dealership.infrastracture.mappers.IdEntityMapper.IdEntityMapper;
import ru.matvey.dealership.infrastracture.mappers.carMapper.CarMapper;
import ru.matvey.dealership.infrastracture.mappers.carMapper.DetailMapper;

@Mapper(componentModel = "spring", uses = {IdEntityMapper.class, DetailMapper.class, CarMapper.class})
public interface ConfigurationOrderMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "car", source = "car")
    @Mapping(target = "finalPrice", expression = "java(domain.getCar().getTotalPrice())")
    @Mapping(target = "client", source = "clientId")
    @Mapping(target = "manager", source = "managerId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "removed", ignore = true)
    ComplictationOrderEntity toEntity(ConfigurationOrder domain);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "clientId", source = "client")
    @Mapping(target = "managerId", source = "manager")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "car", source = "car")
    ConfigurationOrder toDomain(ComplictationOrderEntity entity);

    default String statusToString(Status status) {
        return status.getName();
    }

    default Status StringToStatus(String status) {
        ConfigurationOrderStatusFactory factory= new ConfigurationOrderStatusFactory();
        return factory.Create(status.toUpperCase());
    }
}