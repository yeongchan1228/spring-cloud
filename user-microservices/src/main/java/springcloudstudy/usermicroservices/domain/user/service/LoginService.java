package springcloudstudy.usermicroservices.domain.user.service;

import springcloudstudy.usermicroservices.api.user.controller.dto.UserDto;

public interface LoginService {
    Object createUser(UserDto userDto);
}
