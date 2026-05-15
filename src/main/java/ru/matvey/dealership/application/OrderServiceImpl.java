package ru.matvey.dealership.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.matvey.dealership.abstraction.*;
import ru.matvey.dealership.contracts.mappers.ConfigurationOrderDtoMapper;
import ru.matvey.dealership.contracts.mappers.OrderDtoMapper;
import ru.matvey.dealership.contracts.models.ConfigurationOrderDto;
import ru.matvey.dealership.contracts.models.OrderDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.services.OrderService;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Body.BodyInfo;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.Transmission.TransmissionInfo;
import ru.matvey.dealership.domain.details.color.CarPaint;
import ru.matvey.dealership.domain.details.color.PaintInfo;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutInfo;
import ru.matvey.dealership.domain.details.fuel.Fuel;
import ru.matvey.dealership.domain.details.fuel.FuelInfo;
import ru.matvey.dealership.domain.exceptions.EntityNotFoundException;
import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;
import ru.matvey.dealership.domain.orders.stockCarOrder.OrderStockCar;
import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveOrder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final StockCarOrderRepository stockCarOrderRepository;
    private final TestDriveRepository testDriveRepository;
    private final ComplictaionOrderRepository complictaionOrderRepository;
    private final DetailsRepository detailsRepository;
    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', USER)")
    public UUID makeStockCarOrder(UUID carID, UUID clientId) {
        UUID managerId = null;
        OrderStockCar order = new OrderStockCar(clientId, carID, managerId);
        stockCarOrderRepository.addOrder(order);
        return order.getId();
    }


    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', USER)")
    public UUID makeOrderWithConfiguration(CarComplictation complictation, UUID clientId) {
        Body body = (Body) detailsRepository.getDetails("BODY").stream()
                .filter(d -> d.getInfo().equals(complictation.bodyType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.bodyType()));

        CarPaint color = (CarPaint) detailsRepository.getDetails("COLOR").stream()
                .filter(d -> d.getInfo().equals(complictation.color()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.color()));

        Fuel fuel = (Fuel) detailsRepository.getDetails("FUEL").stream()
                .filter(d -> d.getInfo().equals(complictation.fuelType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.fuelType()));

        Transmission transmission = (Transmission) detailsRepository.getDetails("TRANSMISSION").stream()
                .filter(d -> d.getInfo().equals(complictation.transmissionType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.transmissionType()));

        DriveLayout driveLayout = (DriveLayout) detailsRepository.getDetails("DRIVELAYOUT").stream()
                .filter(d -> d.getInfo().equals(complictation.drivelayoutType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.drivelayoutType()));

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

        UUID managerId = null;
        ConfigurationOrder order = new ConfigurationOrder(clientId, car, managerId);

        complictaionOrderRepository.add(order);
        return order.getId();
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', USER)")
    public UUID makeTestDriveOrder(UUID carId, UUID clientId) {
        UUID managerId = null;
        TestDriveOrder order = new TestDriveOrder(clientId, carId, managerId);
        testDriveRepository.add(order);
        return order.getId();
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', USER) or @SecurityChecker.isConfigOrderOwner(#id, #clientId)")
    public ConfigurationOrderDto getConfigurationOrder(UUID id, UUID clientId) {
        return complictaionOrderRepository.findById(id).map(ConfigurationOrderDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id));
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public List<ConfigurationOrderDto> getAllConfigurationOrders() {
        return complictaionOrderRepository.getAll().stream()
                .map(ConfigurationOrderDtoMapper::toDto)
                .toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', USER) or @SecurityChecker.isConfigOrderOwner(#id, #clientId)")
    public OrderDto getStockCarOrder(UUID id, UUID clientId) {
        return stockCarOrderRepository.findById(id).map(OrderDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id));
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public List<OrderDto> getAllStockCarOrders() {
        return stockCarOrderRepository.getAll().stream()
                .map(OrderDtoMapper::toDto)
                .toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN') or @SecurityChecker.isConfigOrderOwner(#id, #clientId)")
    public OrderDto getTestDriveOrder(UUID id, UUID clientId) {
        return testDriveRepository.findById(id).map(OrderDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id));
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public List<OrderDto> getAllTestDriveOrders() {
        return testDriveRepository.getAll().stream()
                .map(OrderDtoMapper::toDto)
                .toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public void updateOrderStatus(UUID id) {
        complictaionOrderRepository.findById(id).ifPresent(order -> order.nextStep());
        stockCarOrderRepository.findById(id).ifPresent(order -> order.nextStep());
        testDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id)).nextStep();
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN') or @SecurityChecker.isConfigOrderOwner(#id, #clientId)")
    public void cancelOrder(UUID id, UUID clientId) {
        complictaionOrderRepository.findById(id).ifPresent(order -> order.cancel());
        stockCarOrderRepository.findById(id).ifPresent(order -> order.cancel());
        testDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id)).cancel();
    }
}
