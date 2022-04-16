package springcloudstudy.usermicroservices.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudstudy.usermicroservices.api.exception.dto.ErrorDetailDto;
import springcloudstudy.usermicroservices.api.exception.dto.ErrorDto;
import springcloudstudy.usermicroservices.api.user.controller.dto.UserDto;
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

        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Unique Error!");
        checkDuplicate(getName, getEmail, errorDto);
        if(errorDto.getErrors().size() != 0){
            return errorDto;
        }

        User user = User.createUser()
                .userId(uuid)
                .email(getEmail)
                .encryptedPwd(encoder.encode(userDto.getPwd()))
                .username(getName)
                .build();
        userRepository.save(user);
        return user;
    }

    private void checkDuplicate(String getName, String getEmail, ErrorDto errorDto) {
        if(userRepository.findByUsername(getName) != null){
            errorDto.getErrors().add(new ErrorDetailDto("Name", "Name already used"));
        }
        if(userRepository.findByEmail(getEmail) != null){
            errorDto.getErrors().add(new ErrorDetailDto("Email", "Email already used"));
        }
    }
}
