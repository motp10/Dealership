package ru.matvey.dealership.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.matvey.dealership.abstraction.PersistanceRepository;
import ru.matvey.dealership.contracts.mappers.CarDtoMapper;
import ru.matvey.dealership.contracts.mappers.ConfigurationOrderDtoMapper;
import ru.matvey.dealership.contracts.mappers.OrderDtoMapper;
import ru.matvey.dealership.contracts.models.CarDto;
import ru.matvey.dealership.contracts.models.ConfigurationOrderDto;
import ru.matvey.dealership.contracts.models.DetailDto;
import ru.matvey.dealership.contracts.models.OrderDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.operations.DetailOperation;
import ru.matvey.dealership.contracts.services.AdminService;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Body.BodyType;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.DetailsFactory;
import ru.matvey.dealership.domain.details.Detail;
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
import ru.matvey.dealership.domain.users.*;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemAdminServiceImpl implements AdminService {
    private final PersistanceRepository persistence;

    @Override
    public UUID addCar(CarComplictation complictation) {
        Body body = (Body) persistence.getDetailsRepository().getDetails(new BodyType(complictation.bodyType())).getFirst();
        CarPaint color = (CarPaint) persistence.getDetailsRepository().getDetails(new PaintType(complictation.color())).getFirst();
        Fuel fuel = (Fuel) persistence.getDetailsRepository().getDetails(new FuelType(complictation.fuelType())).getFirst();
        Transmission transmission = (Transmission) persistence.getDetailsRepository().getDetails(new TransmissionType(complictation.transmissionType())).getFirst();
        DriveLayout driveLayout = (DriveLayout) persistence.getDetailsRepository().getDetails(new DriveLayoutType(complictation.drivelayoutType())).getFirst();

        Car car = new Car(
                complictation.brand(),
                complictation.model(),
                body,
                color,
                fuel,
                transmission,
                driveLayout,
                new Cost(100)
        );
        persistence.getCarRepository().add(car);
        return car.getId();
    }

    @Override
    public void updateCar(UUID id, CarComplictation complictation) {

    }

    @Override
    public List<CarDto> getAllCars() {
    return persistence.getCarRepository().getAll().stream()
            .map(CarDtoMapper::toDto)
            .toList();
    }

    @Override
    public UUID addDetail(DetailOperation.Request request) {
//        persistence.getDetailsRepository().getDetails();
        return UUID.randomUUID();
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

        return cars.stream().map(CarDto::new).toList();
    }

    @Override
    public void deleteCar(UUID id) {
        persistence.getCarRepository().delete(id);
    }

    @Override
    public CarDto getCar(UUID id) {
        return persistence.getCarRepository().findById(id).map(CarDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no such car id: " + id));
    }

    @Override
    public void updateDetail(UUID id, DetailOperation.Request updatedDetail) {
        DetailsFactory factory = new DetailsFactory();
        Detail detail = factory.createDetail(updatedDetail.detailType(), updatedDetail.detailInfo(), updatedDetail.price(), updatedDetail.suitableCars());
        persistence.getDetailsRepository().delete(id);
        persistence.getDetailsRepository().add(detail);
    }

    @Override
    public void deleteDetail(UUID id) {
        persistence.getDetailsRepository().delete(id);
    }

    @Override
    public DetailDto getDetail(UUID id) {
        return null;
    }

    @Override
    public List<DetailDto> getAllDetails() {
        return persistence.getDetailsRepository().getDetails().stream()
                .map(DetailDto::new)
                .toList();
    }

    @Override
    public List<ConfigurationOrderDto> getAllConfigurationOrders() {
        return persistence.getComplictaionOrderRepository().getAll().stream()
                .map(ConfigurationOrderDto::new)
                .toList();
    }

    @Override
    public List<OrderDto> getAllStockCarOrders() {
        return List.of();
    }

    @Override
    public List<OrderDto> getAllTestDriveOrders() {
        return persistence.getTestDriveRepository().getAll().stream()
                .map(order -> new OrderDto(
                        order.getClientId(),
                        order.getManagerId(),
                        order.getCarId(),
                        order.getStatus()))
                .toList();
    }

    @Override
    public UUID addUser(String name, String password, String fullname, String role) {
        UserRole userRole = UserRole.fromString(role);
        User user = new User(name, password, fullname, userRole);
        persistence.getUserRepository().addUser(user);
        return user.getId();
    }

    @Override
    public void deleteUser(UUID id) {
        persistence.getUserRepository().delete(id);
    }

    @Override
    public void updateOrderStatus(UUID orderId) {
        persistence.getTestDriveRepository().findById(orderId).
                                             orElseThrow(() -> new EntityNotFoundException("no order with this id:" + orderId)).nextStep();
    }

    @Override
    public void cancelOrder(UUID orderId) {
        persistence.getTestDriveRepository().findById(orderId).
                                             orElseThrow(() -> new EntityNotFoundException("no order with this id:" + orderId)).cancel();
    }

    @Override
    public ConfigurationOrderDto getConfigurationOrder(UUID id) {
        return persistence.getComplictaionOrderRepository().findById(id).map(ConfigurationOrderDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id));
    }

    @Override
    public OrderDto getStockCarOrder(UUID id) {
        return persistence.getComplictaionOrderRepository().findById(id).map(OrderDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id));

    }

    @Override
    public OrderDto getTestDriveOrder(UUID id) {
        return persistence.getComplictaionOrderRepository().findById(id).map(OrderDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id));
    }

}
