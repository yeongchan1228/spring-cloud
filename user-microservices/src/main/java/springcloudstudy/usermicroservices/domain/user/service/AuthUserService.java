package springcloudstudy.usermicroservices.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springcloudstudy.usermicroservices.api.exception.user.NotFoundUserException;
import springcloudstudy.usermicroservices.domain.user.entity.User;
import springcloudstudy.usermicroservices.domain.user.repository.UserRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User findUser = userRepository.findByEmail(username).orElseThrow(() -> {
            throw new NotFoundUserException("사용자를 찾을 수 없습니다.");
        });
        return new org.springframework.security.core.userdetails.User(findUser.getEmail(), findUser.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>()); // new ArrayList는 권한 자리
    }
}
