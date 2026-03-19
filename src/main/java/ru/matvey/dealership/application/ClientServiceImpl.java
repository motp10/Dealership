package ru.matvey.dealership.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.matvey.dealership.abstraction.PersistanceRepository;
import ru.matvey.dealership.contracts.mappers.CarDtoMapper;
import ru.matvey.dealership.contracts.models.CarDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.operations.MakeOrder;
import ru.matvey.dealership.contracts.services.ClientService;
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
import ru.matvey.dealership.domain.exceptions.EntityNotFoundException;
import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;
import ru.matvey.dealership.domain.orders.stockCarOrder.OrderStockCar;
import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveOrder;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final PersistanceRepository persistence;

    @Override
    public List<CarDto> getAllCars() {
        List<Car> cars = persistence.getCarRepository().getAll();
        return cars.stream().map(car -> new CarDto(car)).toList();
    }
    @Override
    public UUID makeStockCarOrder(MakeOrder.Request request) {
        UUID managerId = persistence.getUserRepository().getRandomManager();
        OrderStockCar order = new OrderStockCar(request.userId(), request.carId(), managerId);
        persistence.getStockCarOrderRepository().addOrder(order);
        return order.getId();
    }

    @Override
    public UUID makeOrderWithComplictation(CarComplictation request, UUID clientId) {
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

        UUID managerId = persistence.getUserRepository().getRandomManager();
        ConfigurationOrder order = new ConfigurationOrder(clientId, car, managerId);

        persistence.getComplictaionOrderRepository().add(order);
        return order.getId();
    }

    @Override
    public UUID makeTestDriveOrder(MakeOrder.Request request) {
        UUID managerId = persistence.getUserRepository().getRandomManager();
        TestDriveOrder order = new TestDriveOrder(request.userId(), request.carId(), managerId);
        persistence.getTestDriveRepository().add(order);
        return order.getId();
    }

    @Override
    public CarDto getCar(UUID id) {
        return persistence.getCarRepository().findById(id)
                .map(CarDtoMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
    }

    @Override
    public List<CarDto> getCars(CarComplictation request) {
        List<Car> cars = persistence.getCarRepository().findByCriteria(
                new Cost(request.maxPrice()),
                request.brand().isEmpty() ? null : request.brand(),
                request.model().isEmpty() ? null : request.model(),
                request.bodyType().isEmpty() ? null : new BodyType(request.bodyType()),
                request.fuelType().isEmpty() ? null : new FuelType(request.fuelType()),
                request.transmissionType().isEmpty() ? null : new TransmissionType(request.transmissionType()),
                request.drivelayoutType().isEmpty() ? null : new DriveLayoutType(request.drivelayoutType()),
                request.color().isEmpty() ? null : new PaintType(request.color()),
                request.wheel().isEmpty() ? null : new WheelType(request.wheel())
        );

        return cars.stream().map(car -> new CarDto(car)).toList();
    }
}