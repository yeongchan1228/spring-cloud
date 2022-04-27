package springcloudstudy.usermicroservices.api.service;

import springcloudstudy.usermicroservices.api.controller.dto.UserDto;

public interface LoginService {
    Object createUser(UserDto userDto);
}
