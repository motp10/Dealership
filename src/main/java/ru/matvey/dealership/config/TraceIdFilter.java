
package ru.matvey.dealership.infrastructure.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TraceIdFilter extends OncePerRequestFilter {
    private final Counter totalRequests;
    private final Counter successfulRequests;

    public TraceIdFilter(MeterRegistry meterRegistry) {
        this.totalRequests = meterRegistry.counter("dealership_http_requests_total");
        this.successfulRequests = meterRegistry.counter("dealership_http_successful_requests_total");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String traceId = UUID.randomUUID().toString();

        try {
            MDC.put("traceId", traceId);
            response.setHeader("X-Trace-Id", traceId);

            filterChain.doFilter(request, response);
        } finally {
            totalRequests.increment();
            if (response.getStatus() >= HttpServletResponse.SC_OK
                    && response.getStatus() < HttpServletResponse.SC_MULTIPLE_CHOICES) {
                successfulRequests.increment();
            }
            MDC.remove("traceId");
        }
    }
}
