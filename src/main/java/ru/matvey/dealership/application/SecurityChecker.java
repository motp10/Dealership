package ru.matvey.dealership.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.matvey.dealership.infrastracture.jparepositories.JpaConfigurationOrderRepository;

import java.util.UUID;

@Component("securityChecker")
@RequiredArgsConstructor
public class SecurityChecker {

    private final JpaConfigurationOrderRepository configOrderRepo;

    public boolean isConfigOrderOwner(UUID orderId, UUID currentUserId) {
        return configOrderRepo.findById(orderId)
                .map(order -> order.getClientId().equals(currentUserId))
                .orElse(false);
    }
}