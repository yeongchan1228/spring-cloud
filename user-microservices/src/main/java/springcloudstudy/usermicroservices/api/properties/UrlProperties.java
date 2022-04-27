package springcloudstudy.usermicroservices.api.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "url")
@ConstructorBinding
public class UrlProperties {
    @JsonProperty("order-service")
    private final String orderService;
    @JsonProperty("order-service-direct")
    private final String orderServiceDirect;

    public UrlProperties(String orderService, String orderServiceDirect) {
        this.orderService = orderService;
        this.orderServiceDirect = orderServiceDirect;
    }
}
