package ru.matvey.dealership.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.matvey.dealership.abstraction.*;
import ru.matvey.dealership.contracts.mappers.ConfigurationOrderDtoMapper;
import ru.matvey.dealership.contracts.mappers.OrderDtoMapper;
import ru.matvey.dealership.contracts.models.ConfigurationOrderDto;
import ru.matvey.dealership.contracts.models.OrderDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.operations.MakeOrder;
import ru.matvey.dealership.contracts.services.OrderService;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Body.BodyType;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.Transmission.TransmissionType;
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
public class OrderServiceImpl implements OrderService {
    private final StockCarOrderRepository stockCarOrderRepository;
    private final TestDriveRepository testDriveRepository;
    private final ComplictaionOrderRepository complictaionOrderRepository;
    private final UserRepository userRepository;
    private final DetailsRepository detailsRepository;
    @Override
    public UUID makeStockCarOrder(MakeOrder.Request request) {
        UUID managerId = userRepository.getRandomManager();
        OrderStockCar order = new OrderStockCar(request.userId(), request.carId(), managerId);
        stockCarOrderRepository.addOrder(order);
        return order.getId();
    }


    @Override
    public UUID makeOrderWithConfiguration(CarComplictation request, UUID clientId) {
        Body body = (Body) detailsRepository.getDetails(new BodyType(request.bodyType())).getFirst();
        CarPaint carPaint = (CarPaint) detailsRepository.getDetails(new PaintType(request.color())).getFirst();
        Fuel fuel = (Fuel) detailsRepository.getDetails(new FuelType(request.fuelType())).getFirst();
        Transmission transmission = (Transmission) detailsRepository.getDetails(new TransmissionType(request.transmissionType())).getFirst();
        DriveLayout drivelayout = (DriveLayout) detailsRepository.getDetails(new DriveLayoutType(request.drivelayoutType())).getFirst();

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

        UUID managerId = userRepository.getRandomManager();
        ConfigurationOrder order = new ConfigurationOrder(clientId, car, managerId);

        complictaionOrderRepository.add(order);
        return order.getId();
    }

    @Override
    public UUID makeTestDriveOrder(MakeOrder.Request request) {
        UUID managerId = userRepository.getRandomManager();
        TestDriveOrder order = new TestDriveOrder(request.userId(), request.carId(), managerId);
        testDriveRepository.add(order);
        return order.getId();
    }

    @Override
    public ConfigurationOrderDto getConfigurationOrder(UUID id) {
        return complictaionOrderRepository.findById(id).map(ConfigurationOrderDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id));
    }

    @Override
    public List<ConfigurationOrderDto> getAllConfigurationOrders() {
        return complictaionOrderRepository.getAll().stream()
                .map(ConfigurationOrderDtoMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto getStockCarOrder(UUID id) {
        return stockCarOrderRepository.findById(id).map(OrderDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id));
    }

    @Override
    public List<OrderDto> getAllStockCarOrders() {
        return stockCarOrderRepository.getAll().stream()
                .map(OrderDtoMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto getTestDriveOrder(UUID id) {
        return testDriveRepository.findById(id).map(OrderDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id));
    }

    @Override
    public List<OrderDto> getAllTestDriveOrders() {
        return testDriveRepository.getAll().stream()
                .map(OrderDtoMapper::toDto)
                .toList();
    }

    @Override
    public void updateOrderStatus(UUID id) {
        complictaionOrderRepository.findById(id).ifPresent(order -> order.nextStep());
        stockCarOrderRepository.findById(id).ifPresent(order -> order.nextStep());
        testDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id)).nextStep();
    }

    @Override
    public void cancelOrder(UUID id) {
        complictaionOrderRepository.findById(id).ifPresent(order -> order.cancel());
        stockCarOrderRepository.findById(id).ifPresent(order -> order.cancel());
        testDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id)).cancel();
    }
}
