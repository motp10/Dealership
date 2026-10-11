
package ru.matvey.dealership.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
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
        OrderStockCar order;

        try {
            order = new OrderStockCar(
                    request.userId(),
                    request.carId(),
                    managerId
            );
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to create stock car order",
                    kv("service_name", "OrderService"),
                    kv("carId", request.carId()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        try {
            stockCarOrderRepository.addOrder(order);
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to persist stock car order",
                    kv("service_name", "OrderService"),
                    kv("orderId", order.getId()),
                    kv("carId", request.carId()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        log.info(
                "Stock car order created successfully",
                kv("service_name", "OrderService"),
                kv("orderId", order.getId()),
                kv("carId", request.carId())
        );

        return order.getId();
    }

    @Override
    public UUID makeOrderWithConfiguration(
            CarComplictation request,
            UUID clientId
    ) {
        Car car;

        try {
            Body body = (Body) detailsRepository
                    .getDetails(new BodyType(request.bodyType()))
                    .getFirst();

            CarPaint carPaint = (CarPaint) detailsRepository
                    .getDetails(new PaintType(request.color()))
                    .getFirst();

            Fuel fuel = (Fuel) detailsRepository
                    .getDetails(new FuelType(request.fuelType()))
                    .getFirst();

            Transmission transmission = (Transmission) detailsRepository
                    .getDetails(new TransmissionType(request.transmissionType()))
                    .getFirst();

            DriveLayout drivelayout = (DriveLayout) detailsRepository
                    .getDetails(new DriveLayoutType(request.drivelayoutType()))
                    .getFirst();

            car = new Car(
                    request.brand(),
                    request.model(),
                    body,
                    carPaint,
                    fuel,
                    transmission,
                    drivelayout,
                    new Cost(100)
            );

        } catch (RuntimeException exception) {
            log.error(
                    "Failed to build configured car",
                    kv("service_name", "OrderService"),
                    kv("brand", request.brand()),
                    kv("model", request.model()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        UUID managerId = userRepository.getRandomManager();
        ConfigurationOrder order;

        try {
            order = new ConfigurationOrder(clientId, car, managerId);
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to create configuration order",
                    kv("service_name", "OrderService"),
                    kv("brand", request.brand()),
                    kv("model", request.model()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        try {
            complictaionOrderRepository.add(order);
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to persist configuration order",
                    kv("service_name", "OrderService"),
                    kv("orderId", order.getId()),
                    kv("model", request.model()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        log.info(
                "Configuration order created successfully",
                kv("service_name", "OrderService"),
                kv("orderId", order.getId()),
                kv("model", request.model())
        );

        return order.getId();
    }

    @Override
    public UUID makeTestDriveOrder(MakeOrder.Request request) {
        UUID managerId = userRepository.getRandomManager();
        TestDriveOrder order;

        try {
            order = new TestDriveOrder(
                    request.userId(),
                    request.carId(),
                    managerId
            );
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to create test drive order",
                    kv("service_name", "OrderService"),
                    kv("carId", request.carId()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        try {
            testDriveRepository.add(order);
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to persist test drive order",
                    kv("service_name", "OrderService"),
                    kv("orderId", order.getId()),
                    kv("carId", request.carId()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        log.info(
                "Test drive order created successfully",
                kv("service_name", "OrderService"),
                kv("orderId", order.getId()),
                kv("carId", request.carId())
        );

        return order.getId();
    }

    @Override
    public ConfigurationOrderDto getConfigurationOrder(UUID id) {
        ConfigurationOrderDto order = complictaionOrderRepository
                .findById(id)
                .map(ConfigurationOrderDtoMapper::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("no order with this id:" + id)
                );

        log.info(
                "Configuration order retrieved successfully",
                kv("service_name", "OrderService"),
                kv("orderId", id)
        );
        return order;
    }

    @Override
    public List<ConfigurationOrderDto> getAllConfigurationOrders() {
        List<ConfigurationOrderDto> orders = complictaionOrderRepository
                .getAll()
                .stream()
                .map(ConfigurationOrderDtoMapper::toDto)
                .toList();

        log.info(
                "Retrieved all configuration orders",
                kv("service_name", "OrderService"),
                kv("count", orders.size())
        );

        return orders;
    }

    @Override
    public OrderDto getStockCarOrder(UUID id) {
        OrderDto order = stockCarOrderRepository
                .findById(id)
                .map(OrderDtoMapper::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("no order with this id:" + id)
                );

        log.info(
                "Stock car order retrieved successfully",
                kv("service_name", "OrderService"),
                kv("orderId", id)
        );
        return order;
    }

    @Override
    public List<OrderDto> getAllStockCarOrders() {
        List<OrderDto> orders = stockCarOrderRepository
                .getAll()
                .stream()
                .map(OrderDtoMapper::toDto)
                .toList();

        log.info(
                "Retrieved all stock car orders",
                kv("service_name", "OrderService"),
                kv("count", orders.size())
        );

        return orders;
    }

    @Override
    public OrderDto getTestDriveOrder(UUID id) {
        OrderDto order = testDriveRepository
                .findById(id)
                .map(OrderDtoMapper::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("no order with this id:" + id)
                );

        log.info(
                "Test drive order retrieved successfully",
                kv("service_name", "OrderService"),
                kv("orderId", id)
        );
        return order;
    }

    @Override
    public List<OrderDto> getAllTestDriveOrders() {
        List<OrderDto> orders = testDriveRepository
                .getAll()
                .stream()
                .map(OrderDtoMapper::toDto)
                .toList();

        log.info(
                "Retrieved all test drive orders",
                kv("service_name", "OrderService"),
                kv("count", orders.size())
        );

        return orders;
    }

    @Override
    public void updateOrderStatus(UUID id) {
        complictaionOrderRepository
                .findById(id)
                .ifPresent(order -> order.nextStep());

        stockCarOrderRepository
                .findById(id)
                .ifPresent(order -> order.nextStep());

        testDriveRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("no order with this id:" + id)
                )
                .nextStep();

        log.info(
                "Order status advanced successfully",
                kv("service_name", "OrderService"),
                kv("orderId", id)
        );
    }

    @Override
    public void cancelOrder(UUID id) {
        complictaionOrderRepository
                .findById(id)
                .ifPresent(order -> order.cancel());

        stockCarOrderRepository
                .findById(id)
                .ifPresent(order -> order.cancel());

        testDriveRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("no order with this id:" + id)
                )
                .cancel();

        log.info(
                "Order cancelled successfully",
                kv("service_name", "OrderService"),
                kv("orderId", id)
        );
    }
}
