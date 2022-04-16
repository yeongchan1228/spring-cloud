package springcloudstudy.usermicroservices.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springcloudstudy.usermicroservices.api.exception.user.NotFoundUserException;
import springcloudstudy.usermicroservices.domain.user.entity.User;
import springcloudstudy.usermicroservices.domain.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User getUserByUserId(String userId) {
        User findUser = userRepository.
                findByUserId(userId).orElseThrow(() -> new NotFoundUserException("User Not Found!!"));
        return findUser;
    }

    @Override
    public Iterable<User> getAllUser() {
        return userRepository.findAll();
    }
}
