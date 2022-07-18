package springcloudstudy.ordersservice.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springcloudstudy.ordersservice.api.controller.dto.OrderDto;
import springcloudstudy.ordersservice.api.controller.dto.RequestOrderDto;
import springcloudstudy.ordersservice.api.controller.dto.ResponseOrderDto;
import springcloudstudy.ordersservice.domain.order.repository.kafka.KafkaProducer;
import springcloudstudy.ordersservice.domain.order.service.OrderService;
import springcloudstudy.ordersservice.domain.order.entity.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("order-service")
public class OrderController {

    private final Environment env;
    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;

    @GetMapping("/health-check")
    public String healthCheck(){
        return String.format("Working!!! port = %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<ResponseOrderDto>> getOrdersByUserId(
            @PathVariable String userId,
            HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        Iterable<Order> orders = orderService.getOrdersByUserId(userId);
        List<ResponseOrderDto> list = new ArrayList<>();
        orders.forEach(order -> {
            list.add(new ModelMapper().map(order, ResponseOrderDto.class));
        });
        return ResponseEntity.status(200).body(list);
    }

    @PostMapping("/orders/{userId}")
    public ResponseEntity<ResponseOrderDto> saveOrder(@PathVariable String userId,
                                                      @RequestBody RequestOrderDto requestOrderDto){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderDto orderDto = modelMapper.map(requestOrderDto, OrderDto.class);
        orderDto.setUserId(userId);

        Order createOrder = orderService.createOrder(orderDto);

        kafkaProducer.send("example-catalog-topic", orderDto);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(createOrder, ResponseOrderDto.class));
    }
}
