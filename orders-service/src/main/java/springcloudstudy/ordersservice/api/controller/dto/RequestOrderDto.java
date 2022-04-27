package springcloudstudy.ordersservice.api.controller.dto;

import lombok.Data;

@Data
public class RequestOrderDto {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
}
