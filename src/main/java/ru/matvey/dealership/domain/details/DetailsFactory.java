package ru.matvey.dealership.domain.details;

import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Body.BodyType;
import ru.matvey.dealership.domain.details.Interior.Interior;
import ru.matvey.dealership.domain.details.Interior.InteriorType;
import ru.matvey.dealership.domain.details.SteeringWheel.SteeringWheel;
import ru.matvey.dealership.domain.details.SteeringWheel.SteeringWheelType;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.Transmission.TransmissionType;
import ru.matvey.dealership.domain.details.Wheel.Wheel;
import ru.matvey.dealership.domain.details.Wheel.WheelType;
import ru.matvey.dealership.domain.details.color.CarPaint;
import ru.matvey.dealership.domain.details.color.PaintType;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutType;
import ru.matvey.dealership.domain.details.fuel.Fuel;
import ru.matvey.dealership.domain.details.fuel.FuelType;

import java.util.Set;

public class DetailsFactory {
    public Detail createDetail(String type, String info, double price) {
        return switch (type.toLowerCase()) {
            case "paint" -> new CarPaint(new PaintType(info), new Cost(price));
            case "body" -> new Body(new BodyType(info), new Cost(price));
            case "drivelayout" -> new DriveLayout(new DriveLayoutType(info), new Cost(price));
            case "transmission" -> new Transmission(new TransmissionType(info), new Cost(price));
            case "fuel" -> new Fuel(new FuelType(info), new Cost(price));
            case "interior" -> new Interior(new InteriorType(info), new Cost(price));
            case "Steering wheel" -> new SteeringWheel(new SteeringWheelType(info), new Cost(price));
            case "wheel" -> new Wheel(new WheelType(info), new Cost(price));
            default -> throw new IllegalArgumentException("Unknown detail type: " + type);
        };
    }
}
