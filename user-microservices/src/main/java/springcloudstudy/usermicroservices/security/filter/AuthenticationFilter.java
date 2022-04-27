package springcloudstudy.usermicroservices.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springcloudstudy.usermicroservices.api.exception.user.NotFoundUserException;
import springcloudstudy.usermicroservices.api.controller.dto.RequestLoginDto;
import springcloudstudy.usermicroservices.domain.user.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

/**
 * UsernamePasswordAuthenticationFilter는
 * /login 요청시 동작하는 필터
 */
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Key key;
    private Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService,
                                Environment env) {
        super(authenticationManager);
        this.userService = userService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLoginDto creds = new ObjectMapper().readValue(request.getInputStream(), RequestLoginDto.class);

            /**
             * 입력 받은 사용자 이메일과 패스워드로 토큰을 만들어 줘야 한다.
             * new ArrayList<>() 부분은 권한 값을 설정한다.
             */
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>());

            return getAuthenticationManager().authenticate(authenticationToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 인증 성공 후 실행 메소드
     * 토큰 값 수정 가능
     * 만료 시간 등..
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();
        String userEmail = user.getUsername();

        springcloudstudy.usermicroservices.domain.user.entity.User findUser =
                userService.getUserByEmail(userEmail).orElseThrow(() -> new NotFoundUserException("유저를 찾을 수 없습니다."));
        String userId = findUser.getUserId();

        // secret 키는 Base64인코딩이 안되어 있기 때문에 StandardCharsets.UTF_8로 형태를 알려주어야 한다.
        // Base64로 인코딩 되어 있을 시 Decoders.BASE64.decode(secret)
        byte[] keyBytes = env.getProperty("token.secret").getBytes(StandardCharsets.UTF_8);
        key = Keys.hmacShaKeyFor(keyBytes);
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userId);
    }
}
