package springcloudstudy.usermicroservices.api.user.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Simple {
    @Value("${simple.message}")
    private String message;
}
