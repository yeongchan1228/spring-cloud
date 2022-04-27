package springcloudstudy.usermicroservices.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class ResponseOrder {
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    @CreatedDate
    private LocalDate createDate;
    private String orderId;
}
