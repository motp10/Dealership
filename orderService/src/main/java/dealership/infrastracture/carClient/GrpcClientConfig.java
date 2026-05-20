package dealership.infrastracture.carClient;

import com.autosalon.grpc.CarStorageServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Value("${grpc.client.storage-service.address}")
    private String rawAddress;

    @Bean
    public ManagedChannel grpcChannel() {
        String cleanAddress = rawAddress.replace("static://", "");
        String[] parts = cleanAddress.split(":");
        String host = parts[0];
        int port = parts.length > 1 ? Integer.parseInt(parts[1]) : 9090;

        return ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext() // Отключаем TLS
                .build();
    }

    @Bean
    public CarStorageServiceGrpc.CarStorageServiceBlockingStub carStorageServiceBlockingStub(ManagedChannel channel) {
        // Создаем стандартный синхронный Stub
        return CarStorageServiceGrpc.newBlockingStub(channel);
    }
}