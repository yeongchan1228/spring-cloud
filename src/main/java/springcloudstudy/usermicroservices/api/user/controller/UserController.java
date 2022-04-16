package springcloudstudy.usermicroservices.api.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springcloudstudy.usermicroservices.api.user.common.Simple;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/") // 나중에 eureka 등록 시 변경
public class UserController {

//    private final Environment env;
    private final Simple simple;

    @GetMapping("/health-check")
    public String status(){
        return "Working!!!";
    }

    @GetMapping("/welcome")
    public String hello(){
//        String message = env.getProperty("simple.message");
        return simple.getMessage();
    }
}
