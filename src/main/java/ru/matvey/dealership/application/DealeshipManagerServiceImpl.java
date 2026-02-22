package ru.matvey.dealership.application;

import ru.matvey.dealership.Abstraction.PersistanceRepository;
import ru.matvey.dealership.contracts.Models.CarDto;
import ru.matvey.dealership.contracts.Models.ConfigurationOrderDto;
import ru.matvey.dealership.contracts.Models.OrderDto;
import ru.matvey.dealership.contracts.Operations.CarComplictation;
import ru.matvey.dealership.contracts.Operations.MakeOrder;
import ru.matvey.dealership.contracts.Services.DealershipManagerService;
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

public class DealeshipManagerServiceImpl implements DealershipManagerService {
    PersistanceRepository persistence;
    public DealeshipManagerServiceImpl(PersistanceRepository persistence) {
        this.persistence = persistence;
    }

    @Override
    public List<Car> getAllCars() {
        return persistence.getCarRepository().getdAll();
    }

    @Override
    public List<Car> getCars(CarComplictation request, UUID clientId) {
        Body body = (Body) persistence.getDetailsRepository().getDetails(new BodyType(request.bodyType())).getFirst();
        CarPaint carPaint = (CarPaint) persistence.getDetailsRepository().getDetails(new BodyType(request.color())).getFirst();
        Fuel fuel = (Fuel) persistence.getDetailsRepository().getDetails(new BodyType(request.fuelType())).getFirst();
        Transmission transmission = (Transmission) persistence.getDetailsRepository().getDetails(new BodyType(request.transmissionType())).getFirst();
        DriveLayout drivelayout = (DriveLayout) persistence.getDetailsRepository().getDetails(new BodyType(request.drivelayoutType())).getFirst();
        Car car = new Car(request.brand(),
                request.model(),
                body,
                carPaint,
                fuel,
                transmission,
                drivelayout);
        UUID managerId = persistence.getManagerRepository().getRandomManager();
        ConfigurationOrder order = new ConfigurationOrder(clientId, car, managerId);
        persistence.getComplictaionOrderRepository().add(order);
        return persistence.getCarRepository().findByCriteria(new Cost(request.maxPrice()),
                request.brand(),
                request.model(),
                new BodyType(request.bodyType()),
                new FuelType(request.fuelType()),
                new TransmissionType(request.transmissionType()),
                new DriveLayoutType(request.drivelayoutType()),
                new PaintType(request.color()),
                new WheelType(request.wheel()));
    }

    @Override
    public List<OrderStockCar> getStockCarOrders() {
        return persistence.getStockCarOrderRepository().getAll();
    }

    @Override
    public ConfigurationOrderDto getConfigurationCarOrder(UUID orderId) {
        return new ConfigurationOrderDto(persistence.getComplictaionOrderRepository().getOrder(orderId));
    }

    @Override
    public void deleteTestDriveCar(UUID orderId) {
        persistence.getTestDriveRepository().deleteById(orderId);
    }

    @Override
    public void addTestDriveCar(MakeOrder.Request request) {
        UUID managerId = persistence.getManagerRepository().getRandomManager();
        TestDriveOrder order = new TestDriveOrder(request.userId(), request.carId(), managerId);
        persistence.getTestDriveRepository().addOrder(order);
    }

    @Override
    public List<TestDriveOrder> getTestDriveOrders() {
        return persistence.getTestDriveRepository().getAll();
    }
}
