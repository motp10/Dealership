package dealership.runtimehints;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

public class MyRuntimeHints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {

        hints.reflection().registerType(
                TypeReference.of("java.util.UUID[]"),
                MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                MemberCategory.DECLARED_FIELDS
        );
        hints.reflection().registerType(
                TypeReference.of("java.util.UUID"),
                MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                MemberCategory.DECLARED_FIELDS
        );

        hints.reflection().registerType(
                TypeReference.of("io.grpc.netty.shaded.io.netty.channel.socket.nio.NioSocketChannel"),
                MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                MemberCategory.INVOKE_PUBLIC_METHODS
        );

        String[] jcToolsClasses = {
                "io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueueProducerFields",
                "io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueueConsumerFields",
                // ВОТ ЭТОТ КЛАСС БЫЛ ПРОПУЩЕН (он нужен для поля producerLimit):
                "io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueueColdProducerFields",
                "io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueConsumerIndexField",
                "io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueProducerLimitField",
                "io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueProducerIndexField"
        };

        for (String className : jcToolsClasses) {
            hints.reflection().registerType(
                    TypeReference.of(className),
                    MemberCategory.DECLARED_FIELDS, // Сохраняем приватные поля
                    MemberCategory.PUBLIC_FIELDS
            );
        }


        String[] nettyAllocators = {
                "io.grpc.netty.shaded.io.netty.buffer.AbstractByteBufAllocator",
                "io.grpc.netty.shaded.io.netty.buffer.UnpooledByteBufAllocator",
                "io.grpc.netty.shaded.io.netty.buffer.PooledByteBufAllocator"
        };

        for (String allocatorClass : nettyAllocators) {
            hints.reflection().registerType(
                    TypeReference.of(allocatorClass),
                    org.springframework.aot.hint.MemberCategory.INVOKE_DECLARED_METHODS,
                    org.springframework.aot.hint.MemberCategory.INVOKE_PUBLIC_METHODS
            );
        }

        hints.reflection().registerType(
                TypeReference.of("io.grpc.netty.shaded.io.netty.util.ReferenceCountUtil"),
                org.springframework.aot.hint.MemberCategory.INVOKE_DECLARED_METHODS,
                org.springframework.aot.hint.MemberCategory.INVOKE_PUBLIC_METHODS
        );
    }
}