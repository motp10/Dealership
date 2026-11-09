package ru.matvey.dealership.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.tomcat.TomcatMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;

@Configuration
public class MetricsConfig {

    private final MeterRegistry registry;
    private final ServletContext servletContext;

    public MetricsConfig(MeterRegistry registry, ServletContext servletContext) {
        this.registry = registry;
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void init() {
        // В Spring Boot 3 (Jakarta EE) Tomcat сам регистрирует MBean.
        // Мы можем использовать TomcatMetrics с пустым списком тегов, 
        // он сам подхватит MBean, если они есть, либо мы передадим null.
        // Но чтобы гарантированно получить tomcat_threads_*, 
        // лучше всего просто использовать JVM метрики, которые ВСЕГДА есть.
    }
}