package springcloudstudy.usermicroservices.api.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springcloudstudy.usermicroservices.api.controller.dto.ResponseUserDto;
import springcloudstudy.usermicroservices.api.feign.OrderServiceClient;
import springcloudstudy.usermicroservices.api.service.dto.ResponseOrderDto;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserOrderService {

//    private final RestTemplate restTemplate;
//    private final UrlProperties urlProperties;
    private final OrderServiceClient orderServiceClient;

    public ResponseUserDto getOrders(ResponseUserDto userDto, String token){

        /**
         * Using RestTemplate
         */
//        String orderUrl = "http:/localhost:8000/order-service/orders/%s";

        // List로 반환 받기 위해 ParameterizedTypeReference 사용
//        String orderServiceUrl = String.format(urlProperties.getOrderService(), userDto.getUserId());
//        String orderServiceDirectUrl = String.format(urlProperties.getOrderServiceDirect(), userDto.getUserId());

        // RestTemplate에 @LoadBalance가 붙어서 api-gateway로 접근해서 사용하기 불가
        // discovery에서 서비스 이름을 localhost:8000/order-service로 찾음
//        ResponseEntity<List<ResponseOrderDto>> response =
//                restTemplate.exchange(orderServiceUrl, HttpMethod.GET, null,
//                        new ParameterizedTypeReference<List<ResponseOrderDto>>() {});

//        ResponseEntity<List<ResponseOrderDto>> responseDirect =
//                restTemplate.exchange(orderServiceDirectUrl, HttpMethod.GET, null,
//                        new ParameterizedTypeReference<List<ResponseOrderDto>>() {});

//        List<ResponseOrderDto> orderList = responseDirect.getBody();
//        userDto.setOrders(orderList);

        // 주소 값 오류 시 order 정보는 포함 안 하고 전달
        //        List<ResponseOrderDto> orders = null;
//        try {
//            orders = orderServiceClient.getOrders(userDto.getUserId());
//        } catch (FeignException e){
//            log.error(e.getMessage());
//        }

        // ErrorDecoder 사용
        List<ResponseOrderDto> orders = orderServiceClient.getOrders(userDto.getUserId(), token);
        userDto.setOrders(orders);

        return userDto;
    }
}
