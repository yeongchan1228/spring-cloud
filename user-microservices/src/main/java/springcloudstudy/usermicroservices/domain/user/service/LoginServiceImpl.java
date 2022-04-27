package springcloudstudy.usermicroservices.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudstudy.usermicroservices.api.controller.dto.UserDto;
import springcloudstudy.usermicroservices.domain.user.entity.User;
import springcloudstudy.usermicroservices.domain.user.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginServiceImpl implements LoginService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional // SpringDataJpa에 @Transactional이 붙어 있어서 없어도 된다.
    public Object createUser(UserDto userDto){
        String uuid = UUID.randomUUID().toString();
        String getName = userDto.getName();
        String getEmail = userDto.getEmail();

        User user = User.createUser()
                .userId(uuid)
                .email(getEmail)
                .encryptedPwd(encoder.encode(userDto.getPwd()))
                .username(getName)
                .build();

        userRepository.save(user);
        return user;
    }
}
