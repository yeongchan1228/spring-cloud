package springcloudstudy.ordersservice.api.order.service;

import springcloudstudy.ordersservice.api.order.controller.dto.OrderDto;
import springcloudstudy.ordersservice.domain.order.entity.Order;

public interface OrderService {
    Order createOrder(OrderDto orderDto);
    Order getOrderByOrderId(String orderId);
    Iterable<Order> getOrdersByUserId(String userId);
}
