package springcloudstudy.springcloudgateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest; // reactive -> Webflug 지원
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 개별적으로 라우팅 정보에 지정해야 한다.
 */
@Slf4j
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Custom prefilter
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("PreFilter request Id = {}", request.getId());

            // Custom Post Filter
            // Mono -> 비동기 처리 때, 단일 값 전달
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("PostFilter response Id = {}", response.getStatusCode());
            }));
        });
    }

    public static class Config{
        // Configuration properties 추가
    }
}
