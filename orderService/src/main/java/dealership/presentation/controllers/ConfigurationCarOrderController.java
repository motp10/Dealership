package dealership.presentation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import dealership.contracts.models.ConfigurationOrderDto;
import dealership.contracts.operations.CarComplictation;
import dealership.contracts.services.OrderService;
import dealership.presentation.models.CarConfigurationRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/configurationCars")
@RequiredArgsConstructor
public class ConfigurationCarOrderController {
    private final OrderService orderService;
    @PostMapping
    public UUID makeOrder(@RequestBody CarConfigurationRequest request, JwtAuthenticationToken auth) {
        UUID clientId = UUID.fromString(auth.getToken().getSubject());
        CarComplictation complictation = new CarComplictation(0,
                request.brand(),
                request.model(),
                request.bodyType(),
                request.fuel(),
                request.transmission(),
                request.driveLayout(),
                request.paint(),
                request.wheel());
        return orderService.makeOrderWithConfiguration(complictation, clientId);
    }
    @PatchMapping("/update")
    public void updateOrderStatus(@RequestBody UUID id) {
        orderService.updateOrderStatus(id);
    }
    @PatchMapping("/cancel")
    public void cancelOrder(@RequestBody UUID id, JwtAuthenticationToken auth) {
        UUID clientId = UUID.fromString(auth.getToken().getSubject());
        orderService.cancelOrder(id, clientId);
    }
    @GetMapping("/{id}")
    public ConfigurationOrderDto getOrder(@RequestBody UUID id, JwtAuthenticationToken auth) {
        UUID clientId = UUID.fromString(auth.getToken().getSubject());
        return orderService.getConfigurationOrder(id, clientId);
    }
    @GetMapping
    public List<ConfigurationOrderDto> getAllOrders() {
        return orderService.getAllConfigurationOrders();
    }
}