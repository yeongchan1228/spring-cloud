package springcloudstudy.springcloudgateway.filter;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            HttpHeaders headers = request.getHeaders();
            if(!headers.containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange, "No Authorization Header...", HttpStatus.UNAUTHORIZED);
            }

            log.info("httpheader = {}", headers);
            log.info("httpheader authorization = {}", headers.get(HttpHeaders.AUTHORIZATION));
            String authorization = headers.get(HttpHeaders.AUTHORIZATION).get(0);
            String token = authorization.replace("Bearer ", "");

            if(!isValid(token, exchange)){
                return onError(exchange, "Token is not valid...", HttpStatus.UNAUTHORIZED);
            }


            return chain.filter(exchange); // 다음 필터로
        });
    }

    // 유효한 jwt 토큰인지?
    private boolean isValid(String token, ServerWebExchange exchange) {
        boolean value = true;
        String subject = null;
        ServerHttpRequest request = exchange.getRequest();
        String userId = request.getHeaders().get("userId").get(0);

        try {
            byte[] secret = env.getProperty("token.secret").getBytes(StandardCharsets.UTF_8);
            Key key = Keys.hmacShaKeyFor(secret);
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
            subject = jwtParser.parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e){
            value = false;
        }

        if(!StringUtils.hasText(subject) || !userId.equals(subject)) {
            value = false;
        }


        return value;
    }

    /**
     * Spring Mvc 처리가 아닌 Spring WebFlux 처리
     * HttpServlet -> ServerHttp
     */
    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        try {
            byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
            response.setStatusCode(httpStatus);
            DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Flux.just(dataBuffer));
        }catch (Exception e){
            response.setStatusCode(httpStatus);
            return response.setComplete();
        }finally {
            log.error(message);
        }
    }

    static class Config{

    }
}
