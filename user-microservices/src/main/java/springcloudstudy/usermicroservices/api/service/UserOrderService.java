package springcloudstudy.usermicroservices.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springcloudstudy.usermicroservices.api.controller.dto.ResponseUserDto;
import springcloudstudy.usermicroservices.api.properties.UrlProperties;
import springcloudstudy.usermicroservices.api.service.dto.ResponseOrderDto;
import springcloudstudy.usermicroservices.domain.user.service.UserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final RestTemplate restTemplate;
    private final UrlProperties urlProperties;

    public ResponseUserDto getOrders(ResponseUserDto userDto){
//        String orderUrl = "http:/localhost:8000/order-service/orders/%s";

        // using RestTamplate
        // List로 반환 받기 위해 ParameterizedTypeReference 사용
//        String orderServiceUrl = String.format(urlProperties.getOrderService(), userDto.getUserId());
        String orderServiceDirectUrl = String.format(urlProperties.getOrderServiceDirect(), userDto.getUserId());

        // RestTemplate에 @LoadBalance가 붙어서 api-gateway로 접근해서 사용하기 불가
        // discovery에서 서비스 이름을 localhost:8000/order-service로 찾음
//        ResponseEntity<List<ResponseOrderDto>> response =
//                restTemplate.exchange(orderServiceUrl, HttpMethod.GET, null,
//                        new ParameterizedTypeReference<List<ResponseOrderDto>>() {});

        ResponseEntity<List<ResponseOrderDto>> responseDirect =
                restTemplate.exchange(orderServiceDirectUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<ResponseOrderDto>>() {});

        List<ResponseOrderDto> orderList = responseDirect.getBody();
        userDto.setOrders(orderList);

        return userDto;
    }
}
