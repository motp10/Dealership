package ru.matvey.dealership.infrastracture.mappers.carMapper;

import org.mapstruct.Mapper;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Body.BodyType;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.Interior.Interior;
import ru.matvey.dealership.domain.details.Interior.InteriorType;
import ru.matvey.dealership.domain.details.SteeringWheel.SteeringWheel;
import ru.matvey.dealership.domain.details.SteeringWheel.SteeringWheelType;
import ru.matvey.dealership.domain.details.Wheel.Wheel;
import ru.matvey.dealership.domain.details.Wheel.WheelType;
import ru.matvey.dealership.domain.details.color.PaintType;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutType;
import ru.matvey.dealership.domain.details.fuel.Fuel;
import ru.matvey.dealership.domain.details.fuel.FuelType;
import ru.matvey.dealership.domain.details.color.CarPaint;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.Transmission.TransmissionType;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.infrastracture.jpa.DetailEntity;

import java.util.HashSet;

@Mapper(componentModel = "spring")
public interface DetailMapper {

    default Body toBody(DetailEntity entity) {
        if (entity == null) return null;
        return new Body(entity.getId(), new BodyType(entity.getValue()), new Cost(entity.getCost()), entity.getCompatibleModels());
    }

    default Fuel toFuel(DetailEntity entity) {
        if (entity == null) return null;
        return new Fuel(entity.getId(), new FuelType(entity.getValue()), new Cost(entity.getCost()), entity.getCompatibleModels());
    }

    default CarPaint toPaint(DetailEntity entity) {
        if (entity == null) return null;
        return new CarPaint(entity.getId(), new PaintType(entity.getValue()), new Cost(entity.getCost()), entity.getCompatibleModels());
    }

    default Transmission toTransmission(DetailEntity entity) {
        if (entity == null) return null;
        return new Transmission(entity.getId(), new TransmissionType(entity.getValue()), new Cost(entity.getCost()), entity.getCompatibleModels());
    }

    default DriveLayout toDriveLayout(DetailEntity entity) {
        if (entity == null) return null;
        return new DriveLayout(entity.getId(), new DriveLayoutType(entity.getValue()), new Cost(entity.getCost()), entity.getCompatibleModels());
    }

    default Interior toInterior(DetailEntity entity) {
        if (entity == null) return null;
        return new Interior(entity.getId(), new InteriorType(entity.getValue()), new Cost(entity.getCost()), entity.getCompatibleModels());
    }

    default SteeringWheel toSteeringWheel(DetailEntity entity) {
        if (entity == null) return null;
        return new SteeringWheel(entity.getId(), new SteeringWheelType(entity.getValue()), new Cost(entity.getCost()), entity.getCompatibleModels());
    }

    default Wheel toWheel(DetailEntity entity) {
        if (entity == null) return null;
        return new Wheel(entity.getId(), new WheelType(entity.getValue()), new Cost(entity.getCost()), entity.getCompatibleModels());
    }

    default Detail toDomain(DetailEntity entity) {
        if (entity == null || entity.getType() == null) return null;
        return switch (entity.getType().toUpperCase()) {
            case "BODY" -> this.toBody(entity);
            case "FUEL" -> this.toFuel(entity);
            case "COLOR" -> this.toPaint(entity);
            case "TRANSMISSION" -> this.toTransmission(entity);
            case "DRIVELAYOUT" -> this.toDriveLayout(entity);
            case "INTERIOR" -> this.toInterior(entity);
            case "STEERINGWHEEL" -> this.toSteeringWheel(entity);
            case "WHEEL" -> this.toWheel(entity);
            default -> throw new IllegalArgumentException("Unknown detail type: " + entity.getType());
        };
    }

    default DetailEntity toEntity(Detail detail) {
        if (detail == null) return null;

        DetailEntity entity = new DetailEntity();
        entity.setId(detail.getId());

        entity.setType(detail.getClass().getSimpleName().toUpperCase());

        entity.setValue(detail.Info().Value());
        entity.setCost(detail.Cost());

        if (detail.CompatibleModels() != null) {
            entity.setCompatibleModels(new HashSet<>(detail.CompatibleModels()));
        }

        return entity;
    }

    default Cost mapDoubleToCost(Double value) {
        if (value == null) return new Cost(0.0);
        return new Cost(value);
    }
}