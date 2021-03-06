package springcloudstudy.usermicroservices.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springcloudstudy.usermicroservices.api.common.Simple;
import springcloudstudy.usermicroservices.api.exception.dto.ErrorDetailDto;
import springcloudstudy.usermicroservices.api.exception.dto.ErrorDto;
import springcloudstudy.usermicroservices.api.exception.user.NotFoundUserException;
import springcloudstudy.usermicroservices.api.controller.dto.ResponseUserDto;
import springcloudstudy.usermicroservices.api.controller.dto.UserDto;
import springcloudstudy.usermicroservices.api.service.UserOrderService;
import springcloudstudy.usermicroservices.domain.user.entity.User;
import springcloudstudy.usermicroservices.api.service.LoginService;
import springcloudstudy.usermicroservices.domain.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping("/user-service") // 나중에 eureka 등록 시 변경 routing 정보 변경에 의해 주석 처리
public class UserController {

    private final Environment env;
    private final Simple simple;
    private final LoginService loginService;
    private final UserService userService;
    private final UserOrderService userOrderService;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetailDto NotFoundUser(NotFoundUserException e){
        ErrorDetailDto errorDetailDto = new ErrorDetailDto("Find User", e.getMessage());
        return errorDetailDto;
    }

    @GetMapping("/health-check")
    public String status(){
        return String.format("Working!!! on"
                + " Port(local.server.port) = " + env.getProperty("local.server.port")
                + ", Port(server.port) = " + env.getProperty("server.port")
                + ", token secret = " + env.getProperty("token.secret")
                + ", token expiration_time = " + env.getProperty("token.expiration_time")
        );
    }

    @GetMapping("/welcome")
    public String hello(){
//        String message = env.getProperty("simple.message");
        return simple.getMessage();
    }

    @PostMapping("/join")
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

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUserDto>> getAllUser(){
        Iterable<User> users = userService.getAllUser();
        List<ResponseUserDto> lists = new ArrayList<>();
        users.forEach(user -> {
            lists.add(new ModelMapper().map(user, ResponseUserDto.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(lists);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity getUserId(@PathVariable String userId, HttpServletRequest request){
        User findUser = userService.getUserByUserId(userId);
        ResponseUserDto responseUserDto = new ModelMapper().map(findUser, ResponseUserDto.class);

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        // jwt 토큰도 같이 보내기 위해
        ResponseUserDto result = userOrderService.getOrders(responseUserDto, token);

        return ResponseEntity.status(HttpStatus.      OK).body(result);
    }
}
