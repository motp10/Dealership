package ru.matvey.dealership.application;

import ru.matvey.dealership.abstraction.PersistanceRepository;
import ru.matvey.dealership.contracts.Operations.CarComplictation;
import ru.matvey.dealership.contracts.Operations.DetailOperation;
import ru.matvey.dealership.contracts.Services.AdminService;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Body.BodyType;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.DetailsFactory;
import ru.matvey.dealership.domain.details.IDetail;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.Transmission.TransmissionType;
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

public class SystemAdminServiceImpl implements AdminService {
    private PersistanceRepository persistence;
    public SystemAdminServiceImpl(PersistanceRepository persistence) {
        this.persistence = persistence;
    }
    @Override
    public void addCar(CarComplictation complictation) {
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
        persistence.getCarRepository().addCar(car);
    }

    @Override
    public void updateCar(UUID id, CarComplictation complictation) {
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
        persistence.getCarRepository().deleteById(id);
        persistence.getCarRepository().addCar(car);
    }

    @Override
    public void deleteCar(UUID id) {
        persistence.getCarRepository().deleteById(id);
    }

    @Override
    public List<Car> getAllCars() {
        return persistence.getCarRepository().getdAll();
    }

    @Override
    public void addDetail(IDetail detail) {
        persistence.getDetailsRepository().addDetail(detail);
    }

    @Override
    public void updateDetail(UUID id, DetailOperation.Request updatedDetail) {
        DetailsFactory factory = new DetailsFactory();
        IDetail detail = factory.createDetail(updatedDetail.detailType(), updatedDetail.detailInfo(), updatedDetail.price(), updatedDetail.suitableCars());
        persistence.getDetailsRepository().deleteDetail(id);
        persistence.getDetailsRepository().addDetail(detail);
    }

    @Override
    public void removeDetail(UUID id) {
        persistence.getDetailsRepository().deleteDetail(id);
    }

    @Override
    public List<IDetail> getAllDetails() {
        return persistence.getDetailsRepository().getDetails();
    }

    @Override
    public List<ConfigurationOrder> getAllConfigurationOrders() {
        return persistence.getComplictaionOrderRepository().getAll();
    }

    @Override
    public void updateOrderConfigurationStatus(UUID orderId) {
        persistence.getComplictaionOrderRepository().getOrder(orderId).nextStep();
    }

    @Override
    public void cancelConfigurationOrder(UUID orderId) {
        persistence.getComplictaionOrderRepository().getOrder(orderId).cancel();
    }

    @Override
    public List<OrderStockCar> getAllStockOrders() {
        return persistence.getStockCarOrderRepository().getAll();
    }

    @Override
    public void updateStockOrderStatus(UUID orderId, String status) {
        persistence.getStockCarOrderRepository().findById(orderId).nextStep();
    }

    @Override
    public void cancelStockOrder(UUID orderId) {
        persistence.getStockCarOrderRepository().findById(orderId).cancelOrder();
    }

    @Override
    public List<TestDriveOrder> getAllTestDriveOrders() {
        return persistence.getTestDriveRepository().getAll();
    }

    @Override
    public void updateTestDriveStatus(UUID orderId, String status) {
        persistence.getTestDriveRepository().findById(orderId).nextStep();
    }

    @Override
    public void cancelTestDriveOrder(UUID orderId) {
        persistence.getTestDriveRepository().findById(orderId).cancel();
    }
}
