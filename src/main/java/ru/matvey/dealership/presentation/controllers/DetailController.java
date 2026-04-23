package ru.matvey.dealership.presentation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.models.DetailDto;
import ru.matvey.dealership.contracts.operations.DetailOperation;
import ru.matvey.dealership.contracts.services.DetailService;
import ru.matvey.dealership.presentation.models.DetailOperationRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/details")
@RequiredArgsConstructor
public class DetailController {
    private final DetailService detailService;

    @PostMapping
    public UUID addDetail(@RequestBody DetailOperationRequest request) {
        DetailOperation.Request detail = new DetailOperation.Request(
                request.type,
                request.information,
                request.cost,
                request.compatitiveModels
        );
        return detailService.addDetail(detail);
    }
    @PatchMapping
    public void updateDetail(@RequestBody DetailOperationRequest request) {
        DetailOperation.Request detail = new DetailOperation.Request(
                request.type,
                request.information,
                request.cost,
                request.compatitiveModels
        );
        detailService.updateDetail(request.id, detail);
    }
    @DeleteMapping
    public void deleteDetail(@RequestBody UUID id) {
        detailService.deleteDetail(id);
    }
    @GetMapping("/{id}")
    public DetailDto getDetail(@RequestBody UUID id) {
        return detailService.getDetail(id);
    }
    @GetMapping
    public List<DetailDto> getAllDetails() {
        return detailService.getAllDetails();
    }
}