package springcloudstudy.usermicroservices.api.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springcloudstudy.usermicroservices.api.common.Simple;
import springcloudstudy.usermicroservices.api.exception.dto.ErrorDetailDto;
import springcloudstudy.usermicroservices.api.exception.dto.ErrorDto;
import springcloudstudy.usermicroservices.api.exception.user.NotFoundUserException;
import springcloudstudy.usermicroservices.api.user.controller.dto.ResponseUserDto;
import springcloudstudy.usermicroservices.api.user.controller.dto.UserDto;
import springcloudstudy.usermicroservices.domain.user.entity.User;
import springcloudstudy.usermicroservices.domain.user.service.LoginService;
import springcloudstudy.usermicroservices.domain.user.service.UserService;

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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetailDto NotFoundUser(NotFoundUserException e){
        ErrorDetailDto errorDetailDto = new ErrorDetailDto("Find User", e.getMessage());
        return errorDetailDto;
    }

    @GetMapping("/health-check")
    public String status(){
        return String.format("Working!!! on Port = %s", env.getProperty("local.server.port"));
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
    public ResponseEntity getUserId(@PathVariable String userId){
        User findUser = userService.getUserByUserId(userId);
        ResponseUserDto responseUserDto = new ModelMapper().map(findUser, ResponseUserDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseUserDto);
    }
}
