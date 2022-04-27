package springcloudstudy.usermicroservices.api.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import java.time.LocalDate;

@Data
@NoArgsConstructor // restTemplate에서 필요
@EntityListeners(value = AuditingEntityListener.class)
public class ResponseOrderDto {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDate createdDate;
    private String orderId;
}
