package springcloudstudy.ordersservice.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springcloudstudy.ordersservice.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderId(String orderId);
    Iterable<Order> findByUserId(String userId);
}
