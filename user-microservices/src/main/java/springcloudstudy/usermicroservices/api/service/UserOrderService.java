package springcloudstudy.usermicroservices.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springcloudstudy.usermicroservices.api.controller.dto.ResponseUserDto;
import springcloudstudy.usermicroservices.domain.user.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final UserService userService;

    public ResponseUserDto getOrders(ResponseUserDto userDto){
        String orderUrl = "http:/localhost:8000/order-service/orders/%s";

        return null;
    }
}
