package springcloudstudy.usermicroservices.api.feign.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                break;
            case 404:
                if(methodKey.contains("getOrders")){
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "올바른 요청이 아닙니다.");
                }
                break;
            default:
                return new Exception(response.reason());
        }

        return null;
    }
}
