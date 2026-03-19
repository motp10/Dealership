//package ru.matvey.dealership.application;
//
//import ru.matvey.dealership.abstraction.PersistanceRepository;
//import ru.matvey.dealership.contracts.Models.CarDto;
//import ru.matvey.dealership.contracts.Models.ConfigurationOrderDto;
//import ru.matvey.dealership.contracts.Models.OrderDto;
//import ru.matvey.dealership.contracts.Operations.CarComplictation;
//import ru.matvey.dealership.contracts.Operations.MakeOrder;
//import ru.matvey.dealership.contracts.Services.DealershipManagerService;
//import ru.matvey.dealership.domain.car.Car;
//import ru.matvey.dealership.domain.details.Body.BodyType;
//import ru.matvey.dealership.domain.details.Cost;
//import ru.matvey.dealership.domain.details.Transmission.TransmissionType;
//import ru.matvey.dealership.domain.details.Wheel.WheelType;
//import ru.matvey.dealership.domain.details.color.PaintType;
//import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutType;
//import ru.matvey.dealership.domain.details.fuel.FuelType;
//import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;
//import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveOrder;
//
//import java.util.List;
//import java.util.UUID;
//
//public class DealeshipManagerServiceImpl implements DealershipManagerService {
//    private final PersistanceRepository persistence;
//
//    public DealeshipManagerServiceImpl(PersistanceRepository persistence) {
//        this.persistence = persistence;
//    }
//
//    @Override
//    public List<CarDto> getAllCars() {
//        return persistence.getCarRepository().getdAll().stream()
//                .map(CarDto::new)
//                .toList();
//    }
//
//    @Override
//    public List<CarDto> getCars(CarComplictation request, UUID clientId) {
//        List<Car> cars = persistence.getCarRepository().findByCriteria(
//                new Cost(request.maxPrice()),
//                request.brand().isEmpty() ? null : request.brand(),
//                request.model().isEmpty() ? null : request.model(),
//                request.bodyType().isEmpty() ? null : new BodyType(request.bodyType()),
//                request.fuelType().isEmpty() ? null : new FuelType(request.fuelType()),
//                request.transmissionType().isEmpty() ? null : new TransmissionType(request.transmissionType()),
//                request.drivelayoutType().isEmpty() ? null : new DriveLayoutType(request.drivelayoutType()),
//                request.color().isEmpty() ? null : new PaintType(request.color()),
//                request.wheel().isEmpty() ? null : new WheelType(request.wheel())
//        );
//
//        return cars.stream().map(CarDto::new).toList();
//    }
//
//    @Override
//    public List<OrderDto> getStockCarOrders() {
//        return persistence.getStockCarOrderRepository().getAll().stream()
//                .map(order -> new OrderDto(
//                        order.getClientId(),
//                        order.getManagerId(),
//                        order.getCarId(),
//                        order.getStatus()))
//                .toList();
//    }
//
//    @Override
//    public ConfigurationOrderDto getConfigurationCarOrder(UUID orderId) {
//        ConfigurationOrder order = persistence.getComplictaionOrderRepository().getOrder(orderId);
//        return order != null ? new ConfigurationOrderDto(order) : null;
//    }
//
//    @Override
//    public void deleteTestDriveCar(UUID orderId) {
//        persistence.getTestDriveRepository().deleteById(orderId);
//    }
//
//    @Override
//    public void addTestDriveCar(MakeOrder.Request request) {
//        UUID managerId = persistence.getUserRepository().getRandomManager();
//        TestDriveOrder order = new TestDriveOrder(request.userId(), request.carId(), managerId);
//        persistence.getTestDriveRepository().addOrder(order);
//    }
//
//    @Override
//    public List<OrderDto> getTestDriveOrders() {
//        return persistence.getTestDriveRepository().getAll().stream()
//                .map(order -> new OrderDto(
//                        order.getClientId(),
//                        order.getManagerId(),
//                        order.getCarId(),
//                        order.getStatus()))
//                .toList();
//    }
//}