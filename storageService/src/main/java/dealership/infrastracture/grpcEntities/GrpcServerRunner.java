package dealership.infrastracture.grpcEntities;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptor;
import io.grpc.ServerInterceptors;
import io.opentelemetry.instrumentation.grpc.v1_6.GrpcTelemetry;
import io.opentelemetry.api.OpenTelemetry;
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

    private final OpenTelemetry openTelemetry;

    static {
        try {
            Class.forName("io.opentelemetry.api.internal.InstrumentationUtil");
            Class.forName("io.opentelemetry.instrumentation.api.instrumenter.SpanSuppressors$ByContextKey");
        } catch (ClassNotFoundException e) {
            log.warn("OTel classes not found, GraalVM might drop them", e);
        }
    }

    @Override
    public void start() {
        log.info("Starting native gRPC Server on port 9090...");

        ServerInterceptor tracingInterceptor = GrpcTelemetry.create(openTelemetry).newServerInterceptor();

        this.server = ServerBuilder.forPort(9090)
                .addService(ServerInterceptors.intercept(grpcServerService, tracingInterceptor))
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