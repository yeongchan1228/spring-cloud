package springcloudstudy.usermicroservices.domain.user.service;

import springcloudstudy.usermicroservices.domain.user.entity.User;

import java.util.Optional;

public interface UserService {
    User getUserByUserId(String userId);
    Iterable<User> getAllUser();
    Optional<User> getUserByEmail(String email);
}
