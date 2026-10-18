package dealership.domain.details;

import dealership.domain.details.Body.Body;
import dealership.domain.details.Body.BodyInfo;
import dealership.domain.details.Interior.Interior;
import dealership.domain.details.Interior.InteriorInfo;
import dealership.domain.details.SteeringWheel.SteeringWheel;
import dealership.domain.details.SteeringWheel.SteeringWheelInfo;
import dealership.domain.details.Spoiler.Spoiler;
import dealership.domain.details.Spoiler.SpoilerInfo;
import dealership.domain.details.Transmission.Transmission;
import dealership.domain.details.Transmission.TransmissionInfo;
import dealership.domain.details.Wheel.Wheel;
import dealership.domain.details.Wheel.WheelInfo;
import dealership.domain.details.color.CarPaint;
import dealership.domain.details.color.PaintInfo;
import dealership.domain.details.drivelayout.DriveLayout;
import dealership.domain.details.drivelayout.DriveLayoutInfo;
import dealership.domain.details.fuel.Fuel;
import dealership.domain.details.fuel.FuelInfo;

import java.util.Set;

public class DetailsFactory {
    public Detail createDetail(String type, String info, double price, Set<String> compatitiveAuto) {
        return switch (type.toLowerCase()) {
            case "paint" -> new CarPaint(new PaintInfo(info), new Cost(price), compatitiveAuto);
            case "body" -> new Body(new BodyInfo(info), new Cost(price), compatitiveAuto);
            case "drivelayout" -> new DriveLayout(new DriveLayoutInfo(info), new Cost(price), compatitiveAuto);
            case "transmission" -> new Transmission(new TransmissionInfo(info), new Cost(price), compatitiveAuto);
            case "fuel" -> new Fuel(new FuelInfo(info), new Cost(price), compatitiveAuto);
            case "interior" -> new Interior(new InteriorInfo(info), new Cost(price), compatitiveAuto);
            case "Steering wheel" -> new SteeringWheel(new SteeringWheelInfo(info), new Cost(price), compatitiveAuto);
            case "wheel" -> new Wheel(new WheelInfo(info), new Cost(price), compatitiveAuto);
            case "spoiler" -> new Spoiler(new SpoilerInfo(info), new Cost(price), compatitiveAuto);
            default -> throw new IllegalArgumentException("Unknown detail type: " + type);
        };
    }
}
