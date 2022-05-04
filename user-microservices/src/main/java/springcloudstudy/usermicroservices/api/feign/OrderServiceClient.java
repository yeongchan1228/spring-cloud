package springcloudstudy.usermicroservices.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import springcloudstudy.usermicroservices.api.service.dto.ResponseOrderDto;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

//    @GetMapping("/orders_ng/{userId}")
//    List<ResponseOrderDto> getOrders(@PathVariable(value = "userId") String userId);

    // JWT 토큰도 같이 보낸다.
    @GetMapping(value = "/orders/{userId}")
    List<ResponseOrderDto> getOrders(@PathVariable(value = "userId") String userId,
                                     @RequestHeader(name = "Authorization", required = true) String token);
}
