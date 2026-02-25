package ru.matvey.dealership.application;

import ru.matvey.dealership.abstraction.PersistanceRepository;
import ru.matvey.dealership.contracts.Operations.CarComplictation;
import ru.matvey.dealership.contracts.Operations.MakeOrder;
import ru.matvey.dealership.contracts.Services.ClientService;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Body.BodyType;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.Transmission.TransmissionType;
import ru.matvey.dealership.domain.details.Wheel.WheelType;
import ru.matvey.dealership.domain.details.color.CarPaint;
import ru.matvey.dealership.domain.details.color.PaintType;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutType;
import ru.matvey.dealership.domain.details.fuel.Fuel;
import ru.matvey.dealership.domain.details.fuel.FuelType;
import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;
import ru.matvey.dealership.domain.orders.stockCarOrder.OrderStockCar;
import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveOrder;

import java.util.List;
import java.util.UUID;

public class ClientServiceImpl implements ClientService {
    private PersistanceRepository persistence;
    public ClientServiceImpl(PersistanceRepository persistence) {
        this.persistence = persistence;
    }

    @Override
    public List<Car> getAllCars() {
        return persistence.getCarRepository().getdAll();
    }
    @Override
    public void makeStockCarOrder(MakeOrder.Request request) {
        UUID managerId = persistence.getManagerRepository().getRandomManager();
        persistence.getStockCarOrderRepository().addOrder(new OrderStockCar(request.userId(), request.carId(), managerId));
    }

    @Override
    public void makeOrderWithComplictation(CarComplictation request, UUID clientId) {
        Body body = (Body) persistence.getDetailsRepository().getDetails(new BodyType(request.bodyType())).getFirst();
        CarPaint carPaint = (CarPaint) persistence.getDetailsRepository().getDetails(new PaintType(request.color())).getFirst();
        Fuel fuel = (Fuel) persistence.getDetailsRepository().getDetails(new FuelType(request.fuelType())).getFirst();
        Transmission transmission = (Transmission) persistence.getDetailsRepository().getDetails(new TransmissionType(request.transmissionType())).getFirst();
        DriveLayout drivelayout = (DriveLayout) persistence.getDetailsRepository().getDetails(new DriveLayoutType(request.drivelayoutType())).getFirst();

        Car car = new Car(
                request.brand(),
                request.model(),
                body,
                carPaint,
                fuel,
                transmission,
                drivelayout,
                new Cost(100)
        );

        UUID managerId = persistence.getManagerRepository().getRandomManager();
        ConfigurationOrder order = new ConfigurationOrder(clientId, car, managerId);

        persistence.getComplictaionOrderRepository().add(order);
    }

    @Override
    public void makeTestDriveOrder(MakeOrder.Request request) {
        UUID managerId = persistence.getManagerRepository().getRandomManager();
        TestDriveOrder order = new TestDriveOrder(request.userId(), request.carId(), managerId);
        persistence.getTestDriveRepository().addOrder(order);
    }

    @Override
    public List<Car> getCars(CarComplictation request) {
        return persistence.getCarRepository().findByCriteria(
                new Cost(request.maxPrice()),
                request.brand() == "" ? null : request.brand(),
                request.model() == "" ? null : request.model(),
                request.bodyType() =="" ? null : new BodyType(request.bodyType()),
                request.fuelType() =="" ? null : new FuelType(request.fuelType()),
                request.transmissionType() =="" ? null : new TransmissionType(request.transmissionType()),
                request.drivelayoutType() =="" ? null : new DriveLayoutType(request.drivelayoutType()),
                request.color() =="" ? null : new PaintType(request.color()),
                request.wheel() =="" ? null : new WheelType(request.wheel()));
    }
}