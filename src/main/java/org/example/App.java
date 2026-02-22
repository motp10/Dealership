package org.example;

import ru.matvey.dealership.Abstraction.PersistanceRepository;
import ru.matvey.dealership.application.ClientServiceImpl;
import ru.matvey.dealership.contracts.Models.CarDto;
import ru.matvey.dealership.contracts.Operations.CarComplictation;
import ru.matvey.dealership.contracts.Operations.MakeOrder;
import ru.matvey.dealership.contracts.Services.ClientService;
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
import ru.matvey.dealership.infrastracture.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        PersistanceRepository persistence = new PersistanceRepositoryImpl(
                new InMemoryDetailsRepository(),
                new InMemmoryManagersRepository(),
                new InMemoryComplictationOrderRepository(),
                new InMemoryCarRepository(),
                new InMemoryStockCarOrderRepository(),
                new InMemoryTestDriveRepository()
        );
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
        persistence.getCarRepository().addCar(testCar);

        System.out.println("--- Тест поиска ---");
        CarComplictation searchRequest = new CarComplictation(
                1000,"BMW", "", "", "", "", "", "", "");

        List<Car> foundCars = clientService.getCars(searchRequest);
        List<Car> testCars = persistence.getCarRepository().getdAll();
        System.out.println(foundCars.size());
        System.out.println(testCars.size());
    }
}
