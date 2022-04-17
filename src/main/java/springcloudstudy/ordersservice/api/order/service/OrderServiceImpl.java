package springcloudstudy.ordersservice.api.order.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudstudy.ordersservice.api.order.controller.dto.OrderDto;
import springcloudstudy.ordersservice.domain.order.entity.Order;
import springcloudstudy.ordersservice.domain.order.repository.OrderRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        String orderUUID = UUID.randomUUID().toString();

        Order createOrder = Order.createOrder()
                .orderId(orderUUID)
                .productId(orderDto.getProductId())
                .qty(orderDto.getQty())
                .unitPrice(orderDto.getUnitPrice())
                .totalPrice(orderDto.getQty() * orderDto.getUnitPrice())
                .userId(orderDto.getUserId())
                .build();

        orderRepository.save(createOrder);

        return new ModelMapper().map(createOrder, OrderDto.class);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        Order findOrder = orderRepository.findByOrderId(orderId);
        return new ModelMapper().map(findOrder, OrderDto.class);
    }

    @Override
    public Iterable<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
