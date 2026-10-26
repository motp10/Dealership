package ru.matvey.dealership.presentation.controllers;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.models.DetailDto;
import ru.matvey.dealership.contracts.operations.DetailOperation;
import ru.matvey.dealership.contracts.services.DetailService;
import ru.matvey.dealership.presentation.models.DetailOperationRequest;

import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestController
@RequestMapping("api/details")
@RequiredArgsConstructor
public class DetailController {
    private final DetailService detailService;
    private final MeterRegistry meterRegistry;

    @PostConstruct
    void registerBusinessMetrics() {
        meterRegistry.counter("dealership_details_created_total");
        meterRegistry.counter("dealership_details_deleted_total");
    }

    @PostMapping
    public UUID addDetail(@RequestBody DetailOperationRequest request) {
        DetailOperation.Request detail = new DetailOperation.Request(
                request.type,
                request.information,
                request.cost
        );
        UUID detailId = detailService.addDetail(detail);
        meterRegistry.counter("dealership_details_created_total").increment();
        log.info("Detail creation request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "DetailController"),
            kv("operation", "create"),
            kv("detailId", detailId),
            kv("detailType", request.type));
        return detailId;
    }
    @PatchMapping
    public void updateDetail(@RequestBody DetailOperationRequest request) {
        DetailOperation.Request detail = new DetailOperation.Request(
                request.type,
                request.information,
                request.cost
        );
        detailService.updateDetail(request.id, detail);
        log.info("Detail update request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "DetailController"),
            kv("operation", "update"),
            kv("detailId", request.id),
            kv("detailType", request.type));
    }
    @DeleteMapping
    public void deleteDetail(@RequestBody UUID id) {
        detailService.deleteDetail(id);
        meterRegistry.counter("dealership_details_deleted_total").increment();
        log.info("Detail deletion request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "DetailController"),
            kv("operation", "delete"),
            kv("detailId", id));
    }
    @GetMapping("/{id}")
    public DetailDto getDetail(@PathVariable UUID id) {
        DetailDto detail = detailService.getDetail(id);
        log.debug("Detail retrieval request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "DetailController"),
            kv("operation", "getById"),
            kv("detailId", id));
        return detail;
    }
    @GetMapping
    public void getAllDetails() {
        int count = detailService.getAllDetails().size();
        log.debug("Detail list request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "DetailController"),
            kv("operation", "getAll"),
            kv("count", count));
    }
}