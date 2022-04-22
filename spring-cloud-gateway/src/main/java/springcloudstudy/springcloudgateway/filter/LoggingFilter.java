package springcloudstudy.springcloudgateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {super(Config.class);}

    @Override
    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            log.info("LoggingFilter message = {}", config.getMessage());
//            if(config.isPreLogger()) log.info("PreLoggingFilter requestId = {}", request.getId());
//
//            return chain.filter(exchange).then(Mono.fromRunnable(
//                    () -> {log.info("PostLoggingFilter responseId = {}", response.getStatusCode());}
//            ));
//        });

        // Ordered.HIGHEST_PRECEDENCE 우선 순위 제일 높음
        GatewayFilter filter = new OrderedGatewayFilter(((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("LoggingFilter message = {}", config.getMessage());
            if(config.isPreLogger()) log.info("PreLoggingFilter requestId = {}", request.getId());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("PostLoggingFilter responseId = {}", response.getStatusCode());
            }));
        }), Ordered.LOWEST_PRECEDENCE);

        return filter;
    }

    @Data
    public static class Config{
        private String message;
        private boolean preLogger;
        private boolean postLogger;
    }
}
