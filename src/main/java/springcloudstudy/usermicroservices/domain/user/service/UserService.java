package springcloudstudy.usermicroservices.domain.user.service;

import springcloudstudy.usermicroservices.domain.user.entity.User;

public interface UserService {
    User getUserByUserId(String userId);
    Iterable<User> getAllUser();
}
