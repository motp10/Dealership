package dealership.application;

import dealership.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import dealership.abstraction.CarsRepository;
import dealership.abstraction.DetailsRepository;
import dealership.contracts.mappers.CarDtoMapper;
import dealership.contracts.models.CarDto;
import dealership.contracts.operations.CarComplictation;
import dealership.contracts.services.CarService;
import dealership.domain.car.Car;
import dealership.domain.details.Body.Body;
import dealership.domain.details.Body.BodyInfo;
import dealership.domain.details.Cost;
import dealership.domain.details.Transmission.Transmission;
import dealership.domain.details.Transmission.TransmissionInfo;
import dealership.domain.details.Wheel.WheelInfo;
import dealership.domain.details.color.CarPaint;
import dealership.domain.details.color.PaintInfo;
import dealership.domain.details.drivelayout.DriveLayout;
import dealership.domain.details.drivelayout.DriveLayoutInfo;
import dealership.domain.details.fuel.Fuel;
import dealership.domain.details.fuel.FuelInfo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final DetailsRepository detailsRepository;
    private final CarsRepository carsRepository;
    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public UUID addCar(CarComplictation complictation) {
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
                new Cost(complictation.maxPrice())
        );
        carsRepository.add(car);
        return car.getId();
    }

    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public void updateCar(UUID id, CarComplictation complictation) {
        carsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no such car id: " + id));

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

        Car updated = new Car(
                id,
                complictation.brand(),
                complictation.model(),
                body,
                color,
                fuel,
                transmission,
                driveLayout,
                new Cost(complictation.maxPrice())
        );
        carsRepository.delete(id);
        carsRepository.add(updated);
    }

    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public void deleteCar(UUID id) {
        carsRepository.delete(id);
    }

    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public CarDto getCar(UUID id, UUID clientId) {
        return carsRepository.findById(id).map(CarDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no such car id: " + id));
    }

    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public List<CarDto> getCars(CarComplictation filter) {
        List<Car> cars = carsRepository.findByCriteria(
                filter.maxPrice() > 0 ? new Cost(filter.maxPrice()) : null,
                filter.brand().isEmpty() ? null : filter.brand(),
                filter.model().isEmpty() ? null : filter.model(),
                filter.bodyType().isEmpty() ? null : new BodyInfo(filter.bodyType()),
                filter.fuelType().isEmpty() ? null : new FuelInfo(filter.fuelType()),
                filter.transmissionType().isEmpty() ? null : new TransmissionInfo(filter.transmissionType()),
                filter.drivelayoutType().isEmpty() ? null : new DriveLayoutInfo(filter.drivelayoutType()),
                filter.color().isEmpty() ? null : new PaintInfo(filter.color()),
                filter.wheel().isEmpty() ? null : new WheelInfo(filter.wheel())
        );
        return cars.stream().map(CarDtoMapper::toDto).toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public List<CarDto> getAllCars() {
        return carsRepository.getAll().stream().map(CarDtoMapper::toDto).toList();
    }
}
