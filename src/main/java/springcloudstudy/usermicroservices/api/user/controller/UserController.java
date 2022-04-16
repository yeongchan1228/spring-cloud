package springcloudstudy.usermicroservices.api.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springcloudstudy.usermicroservices.api.common.Simple;
import springcloudstudy.usermicroservices.api.exception.dto.ErrorDto;
import springcloudstudy.usermicroservices.api.user.controller.dto.ResponseUserDto;
import springcloudstudy.usermicroservices.api.user.controller.dto.UserDto;
import springcloudstudy.usermicroservices.domain.user.entity.User;
import springcloudstudy.usermicroservices.domain.user.service.LoginService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/") // 나중에 eureka 등록 시 변경
public class UserController {

//    private final Environment env;
    private final Simple simple;
    private final LoginService loginService;

    @GetMapping("/health-check")
    public String status(){
        return "Working!!!";
    }

    @GetMapping("/welcome")
    public String hello(){
//        String message = env.getProperty("simple.message");
        return simple.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Validated @RequestBody UserDto userDto){
        Object result = loginService.createUser(userDto);
        HttpStatus httpStatus = HttpStatus.CREATED;

        if(result instanceof ErrorDto){
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(result, httpStatus);
        }

        User createdUser = (User) result;
        ResponseUserDto responseUserDto =
                new ResponseUserDto(createdUser.getEmail(), createdUser.getUsername(), createdUser.getUserId());
//        return new ResponseEntity<>(responseUserDto, httpStatus);
        return ResponseEntity.status(201).body(responseUserDto);
    }
}
