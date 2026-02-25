package org.example;

import org.junit.jupiter.api.Test;
import ru.matvey.dealership.Abstraction.PersistanceRepository;
import ru.matvey.dealership.application.ClientServiceImpl;
import ru.matvey.dealership.application.SystemAdminServiceImpl;
import ru.matvey.dealership.contracts.Operations.CarComplictation;
import ru.matvey.dealership.contracts.Operations.MakeOrder;
import ru.matvey.dealership.contracts.Services.AdminService;
import ru.matvey.dealership.contracts.Services.ClientService;
import ru.matvey.dealership.domain.Exceptions.IncompatibleComponentException;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Body.BodyType;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Interior.Interior;
import ru.matvey.dealership.domain.details.Interior.InteriorType;
import ru.matvey.dealership.domain.details.SteeringWheel.SteeringWheel;
import ru.matvey.dealership.domain.details.SteeringWheel.SteeringWheelType;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.Transmission.TransmissionType;
import ru.matvey.dealership.domain.details.Wheel.Wheel;
import ru.matvey.dealership.domain.details.Wheel.WheelType;
import ru.matvey.dealership.domain.details.color.CarPaint;
import ru.matvey.dealership.domain.details.color.PaintType;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutType;
import ru.matvey.dealership.domain.details.fuel.Fuel;
import ru.matvey.dealership.domain.details.fuel.FuelType;
import ru.matvey.dealership.domain.users.ClientUser;
import ru.matvey.dealership.domain.users.DealeshipManagerUser;
import ru.matvey.dealership.infrastracture.*;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AppTest {
    PersistanceRepository MakeGlobalRepository() {
        PersistanceRepository persistence = new PersistanceRepositoryImpl(
                new InMemoryDetailsRepository(),
                new InMemmoryManagersRepository(),
                new InMemoryComplictationOrderRepository(),
                new InMemoryCarRepository(),
                new InMemoryStockCarOrderRepository(),
                new InMemoryTestDriveRepository()
        );

        AdminService adminService = new SystemAdminServiceImpl(persistence);

        Set<String> commonModels = Set.of("Sedan-X", "SUV-Pro", "Coupe-Sport");

        Body bodySedan = new Body(new BodyType("Sedan"), new Cost(5000.0), Set.of("Sedan-X"));
        Body bodySUV = new Body(new BodyType("SUV"), new Cost(8500.0), Set.of("SUV-Pro"));

        CarPaint paintBlack = new CarPaint(new PaintType("Metallic Black"), new Cost(1200.0), commonModels);
        CarPaint paintWhite = new CarPaint(new PaintType("Pearl White"), new Cost(1500.0), commonModels);

        DriveLayout driveAWD = new DriveLayout(new DriveLayoutType("AWD"), new Cost(3000.0), Set.of("SUV-Pro"));
        DriveLayout driveRWD = new DriveLayout(new DriveLayoutType("RWD"), new Cost(2500.0), Set.of("Coupe-Sport", "Sedan-X"));

        Fuel fuelGas = new Fuel(new FuelType("Gasoline"), new Cost(0.0), commonModels);
        Fuel fuelElectric = new Fuel(new FuelType("Electric"), new Cost(5000.0), Set.of("Sedan-X"));

        Interior interiorLeather = new Interior(new InteriorType("Leather Black"), new Cost(2000.0), commonModels);
        Interior interiorSport = new Interior(new InteriorType("Alcantara Sport"), new Cost(3500.0), Set.of("Coupe-Sport"));

        SteeringWheel steeringClassic = new SteeringWheel(new SteeringWheelType("Classic"), new Cost(400.0), commonModels);
        SteeringWheel steeringSport = new SteeringWheel(new SteeringWheelType("Sport Multi-function"), new Cost(950.0), commonModels);

        Transmission transAuto = new Transmission(new TransmissionType("Automatic 8-speed"), new Cost(2800.0), commonModels);
        Transmission transManual = new Transmission(new TransmissionType("Manual 6-speed"), new Cost(1800.0), Set.of("Coupe-Sport"));

        Wheel wheelAlloy = new Wheel(new WheelType("18-inch Alloy"), new Cost(1200.0), commonModels);
        Wheel wheelForged = new Wheel(new WheelType("21-inch Forged"), new Cost(4000.0), Set.of("SUV-Pro", "Coupe-Sport"));

        adminService.addDetail(bodySedan);
        adminService.addDetail(bodySUV);
        adminService.addDetail(paintBlack);
        adminService.addDetail(paintWhite);
        adminService.addDetail(driveAWD);
        adminService.addDetail(driveRWD);
        adminService.addDetail(fuelGas);
        adminService.addDetail(fuelElectric);
        adminService.addDetail(interiorLeather);
        adminService.addDetail(interiorSport);
        adminService.addDetail(steeringClassic);
        adminService.addDetail(steeringSport);
        adminService.addDetail(transAuto);
        adminService.addDetail(transManual);
        adminService.addDetail(wheelAlloy);
        adminService.addDetail(wheelForged);

        return persistence;
    }
    @Test
    void AddCarTest() {

        PersistanceRepository persistence = MakeGlobalRepository();

        AdminService adminService = new SystemAdminServiceImpl(persistence);

        ClientService clientService = new ClientServiceImpl(persistence);
        Set<String> ss = new HashSet<String>();
        Car testCar = new Car(
                "BMW",
                "X5",
                new Body(new BodyType("Black SUV"), new Cost(13), ss),
                new CarPaint(new PaintType("Green"), new Cost(123), ss),
                new Fuel(new FuelType("Gasoline"), new Cost(123), ss),
                new Transmission(new TransmissionType("Good transmission"), new Cost(123), ss),
                new DriveLayout(new DriveLayoutType("AWD"), new Cost(123), ss)
        );
        CarComplictation complictation = new CarComplictation(
                1000,
                "BMW",
                "X5",
                "petrol",
                "t",
                "d",
                "a",
                "greem",
                "round"
        );
        Car testCar2 = new Car(
                "Mersedes",
                "H7",
                new Body(new BodyType("Black SUV"), new Cost(13), ss),
                new CarPaint(new PaintType("Green"), new Cost(123), ss),
                new Fuel(new FuelType("Gasoline"), new Cost(123), ss),
                new Transmission(new TransmissionType("Good transmission"), new Cost(123), ss),
                new DriveLayout(new DriveLayoutType("AWD"), new Cost(123), ss)
        );
        persistence.getCarRepository().addCar(testCar);
        persistence.getCarRepository().addCar(testCar2);

        CarComplictation searchRequest = new CarComplictation(
                1000,"BMW", "", "", "", "", "", "", "");

        List<Car> foundCars = clientService.getCars(searchRequest);
        List<Car> allCars = clientService.getAllCars();

        assertEquals(foundCars.size(), 1);
        assertEquals(allCars.size(), 2);
    }

    @Test
    void AddTestDriveOrderTest() {
        PersistanceRepository persistence = MakeGlobalRepository();
        DealeshipManagerUser manager = new DealeshipManagerUser("manager", "132", "name");
        persistence.getManagerRepository().AddManager(manager);
        ClientService clientService = new ClientServiceImpl(persistence);
        AdminService adminService = new SystemAdminServiceImpl(persistence);
        Set<String> ss = new HashSet<String>();

        Car testCar = new Car(
                "BMW",
                "X5",
                new Body(new BodyType("Black SUV"), new Cost(13), ss),
                new CarPaint(new PaintType("Green"), new Cost(123), ss),
                new Fuel(new FuelType("Gasoline"), new Cost(123), ss),
                new Transmission(new TransmissionType("Good transmission"), new Cost(123), ss),
                new DriveLayout(new DriveLayoutType("AWD"), new Cost(123), ss)
        );
        Car testCar2 = new Car(
                "Mersedes",
                "H7",
                new Body(new BodyType("Black SUV"), new Cost(13), ss),
                new CarPaint(new PaintType("Green"), new Cost(123), ss),
                new Fuel(new FuelType("Gasoline"), new Cost(123), ss),
                new Transmission(new TransmissionType("Good transmission"), new Cost(123), ss),
                new DriveLayout(new DriveLayoutType("AWD"), new Cost(123), ss)
        );
        ClientUser clientUser = new ClientUser("SomeName", "1234", "FullName");
        MakeOrder.Request TestDrineRequest = new MakeOrder.Request(testCar.getId(), clientUser.GetId());
        persistence.getCarRepository().addCar(testCar);
        persistence.getCarRepository().addCar(testCar2);
        clientService.makeTestDriveOrder(TestDrineRequest);
        assertEquals(persistence.getTestDriveRepository().getAll().size(), 1);
    }

    @Test
    void DetailsCntTest() {
        PersistanceRepository persistence = MakeGlobalRepository();
        assertEquals(persistence.getDetailsRepository().getDetails().size(), 16);
    }

    @Test
    void CarOrderTest() {
        PersistanceRepository persistence = MakeGlobalRepository();
        DealeshipManagerUser manager = new DealeshipManagerUser("manager", "132", "name");
        persistence.getManagerRepository().AddManager(manager);
        ClientService clientService = new ClientServiceImpl(persistence);
        AdminService adminService = new SystemAdminServiceImpl(persistence);
        Set<String> ss = new HashSet<String>();

        Car testCar = new Car(
                "BMW",
                "X5",
                new Body(new BodyType("Black SUV"), new Cost(13), ss),
                new CarPaint(new PaintType("Green"), new Cost(123), ss),
                new Fuel(new FuelType("Gasoline"), new Cost(123), ss),
                new Transmission(new TransmissionType("Good transmission"), new Cost(123), ss),
                new DriveLayout(new DriveLayoutType("AWD"), new Cost(123), ss)
        );
        Car testCar2 = new Car(
                "Mersedes",
                "H7",
                new Body(new BodyType("Black SUV"), new Cost(13), ss),
                new CarPaint(new PaintType("Green"), new Cost(123), ss),
                new Fuel(new FuelType("Gasoline"), new Cost(123), ss),
                new Transmission(new TransmissionType("Good transmission"), new Cost(123), ss),
                new DriveLayout(new DriveLayoutType("AWD"), new Cost(123), ss)
        );
        ClientUser clientUser = new ClientUser("SomeName", "1234", "FullName");
        MakeOrder.Request order = new MakeOrder.Request(testCar.getId(), clientUser.GetId());
        persistence.getCarRepository().addCar(testCar);
        persistence.getCarRepository().addCar(testCar2);
        clientService.makeStockCarOrder(order);
        assertEquals(persistence.getStockCarOrderRepository().getAll().size(), 1);
    }
}
