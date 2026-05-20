package dealership.infrastracture.grpcEntities;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class GrpcServerRunner implements SmartLifecycle {

    private final GrpcServer grpcServerService;
    private Server server;
    private boolean isRunning = false;

    @Override
    public void start() {
        log.info("Starting native gRPC Server on port 9090...");
        this.server = ServerBuilder.forPort(9090)
                .addService(grpcServerService)
                .build();
        try {
            server.start();
            isRunning = true;
            log.info("Native gRPC Server started successfully, listening on 9090");
        } catch (Exception e) {
            log.error("Failed to start gRPC Server", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        if (server != null) {
            log.info("Stopping gRPC Server...");
            server.shutdown();
            isRunning = false;
        }
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}