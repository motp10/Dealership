//package ru.matvey.dealership.application;
//
//import ru.matvey.dealership.abstraction.PersistanceRepository;
//import ru.matvey.dealership.contracts.Models.CarDto;
//import ru.matvey.dealership.contracts.Models.DetailDto;
//import ru.matvey.dealership.contracts.Operations.CarComplictation;
//import ru.matvey.dealership.contracts.Operations.DetailOperation;
//import ru.matvey.dealership.contracts.Services.WarehouseAdminService;
//import ru.matvey.dealership.domain.car.Car;
//import ru.matvey.dealership.domain.details.Body.Body;
//import ru.matvey.dealership.domain.details.Body.BodyType;
//import ru.matvey.dealership.domain.details.Cost;
//import ru.matvey.dealership.domain.details.DetailsFactory;
//import ru.matvey.dealership.domain.details.Detail;
//import ru.matvey.dealership.domain.details.Transmission.Transmission;
//import ru.matvey.dealership.domain.details.Transmission.TransmissionType;
//import ru.matvey.dealership.domain.details.color.CarPaint;
//import ru.matvey.dealership.domain.details.color.PaintType;
//import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
//import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutType;
//import ru.matvey.dealership.domain.details.fuel.Fuel;
//import ru.matvey.dealership.domain.details.fuel.FuelType;
//
//import java.util.List;
//import java.util.UUID;
//
//public class WarehouseAdminServiceImpl implements WarehouseAdminService {
//    private PersistanceRepository persistence;
//    public WarehouseAdminServiceImpl(PersistanceRepository persistence) {
//        this.persistence = persistence;
//    }
//    @Override
//    public List<CarDto> getAllCars() {
//        return persistence.getCarRepository().getdAll().stream().map((x)->{return new CarDto(x);}).toList();
//    }
//
//    @Override
//    public List<DetailDto> getAllDetails() {
//        return persistence.getDetailsRepository().getDetails().stream().map((x)->{return new DetailDto(x);}).toList();
//    }
//
//    @Override
//    public CarDto getCar(UUID carId) {
//        return new CarDto(persistence.getCarRepository().findById(carId));
//    }
//
//    @Override
//    public DetailDto getDetail(UUID detailId) {
//        return new DetailDto(persistence.getDetailsRepository().getDetail(detailId));
//    }
//
//    @Override
//    public void addCar(CarComplictation complictation) {
//        Body body = (Body) persistence.getDetailsRepository().getDetails(new BodyType(complictation.bodyType())).getFirst();
//        CarPaint color = (CarPaint) persistence.getDetailsRepository().getDetails(new PaintType(complictation.color())).getFirst();
//        Fuel fuel = (Fuel) persistence.getDetailsRepository().getDetails(new FuelType(complictation.fuelType())).getFirst();
//        Transmission transmission = (Transmission) persistence.getDetailsRepository().getDetails(new TransmissionType(complictation.transmissionType())).getFirst();
//        DriveLayout driveLayout = (DriveLayout) persistence.getDetailsRepository().getDetails(new DriveLayoutType(complictation.drivelayoutType())).getFirst();
//
//        Car car = new Car(
//                complictation.brand(),
//                complictation.model(),
//                body,
//                color,
//                fuel,
//                transmission,
//                driveLayout,
//                new Cost(100)
//        );
//        persistence.getCarRepository().addCar(car);
//    }
//
//    @Override
//    public void addDetail(DetailOperation.Request request) {
//        DetailsFactory factory = new DetailsFactory();
//        Detail detail = factory.createDetail(request.detailType(), request.detailType(), request.price(), request.suitableCars());
//        persistence.getDetailsRepository().addDetail(detail);
//    }
//
//    @Override
//    public void deleteCar(UUID carId) {
//        persistence.getCarRepository().deleteById(carId);
//    }
//
//    @Override
//    public void deleteDetail(UUID detailId) {
//        persistence.getDetailsRepository().deleteDetail(detailId);
//    }
//}
