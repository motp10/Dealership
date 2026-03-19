package ru.matvey.dealership.presentation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.models.DetailDto;
import ru.matvey.dealership.contracts.operations.DetailOperation;
import ru.matvey.dealership.contracts.services.AdminService;
import ru.matvey.dealership.contracts.services.ClientService;
import ru.matvey.dealership.presentation.models.DetailOperationRequest;

import java.util.UUID;

@RestController
@RequestMapping("api/details")
@RequiredArgsConstructor
public class DetailController {
    private final AdminService adminService;
    private final ClientService clientService;

    @PostMapping
    public UUID addDetail(@RequestBody DetailOperationRequest request) {
        DetailOperation.Request detail = new DetailOperation.Request(
                request.type,
                request.information,
                request.cost,
                request.compatitiveModels
        );
        return adminService.addDetail(detail);
    }
    @PatchMapping
    public void updateDetail(@RequestBody DetailOperationRequest request) {
        DetailOperation.Request detail = new DetailOperation.Request(
                request.type,
                request.information,
                request.cost,
                request.compatitiveModels
        );
        adminService.updateDetail(request.id, detail);
    }
    @DeleteMapping
    public void deleteDetail(@RequestBody UUID id) {
        adminService.deleteDetail(id);
    }
    @GetMapping("/{id}")
    public DetailDto getDetail(@RequestBody UUID id) {
        return adminService.getDetail(id);
    }
    @GetMapping
    public void getAllDetails() {
        adminService.getAllDetails();
    }
}