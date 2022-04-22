package springCloudStudy.springCloudClient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope // Actuator 변경 감지 범위
public class ConfigClientController {

    @Value("${test.str}")
    private String configStr;

    @GetMapping("/test")
    public String test(){
        return configStr;
    }
}
