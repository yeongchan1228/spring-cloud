package springcloudstudy.usermicroservices.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springcloudstudy.usermicroservices.domain.user.service.AuthUserService;
import springcloudstudy.usermicroservices.domain.user.service.UserService;
import springcloudstudy.usermicroservices.security.filter.AuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final AuthUserService authUserService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

//        http.authorizeRequests().antMatchers("/user/**").permitAll();

        http.authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/**")
                .hasIpAddress("192.168.0.8")
                .and()
                .addFilter(getAuthenticationFilter());
//                .hasIpAddress(IP 값);// 통과 IP 지정 가능


        /**
         *  .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
         *  UsernamePasswordAuthenticationFilter.class는 ./login 요청 시 실행되는 필터
         *  아이디랑 비밀번호 확인.
         *  ./login을 disable한 후 재 정의해서 jwt 저장 가능
         *  jwrFilter -> jwt를 확인해서 인증을 준다.
         *  ./login 요청이 아니면 jwtFilter만 동작하게 UsernamePasswordAuthenticationFilter.class전에 등록한다.
         *  ./login 요청이 오면 jwtFilter 동작 -> 아무것도 없이 다음 체인으로 넘김 -> UsernamePasswordAuthenticationFilter.class 작동
         */
        http.headers().frameOptions().disable(); // h2-console을 보기 위한 설정
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), userService, env);
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserService).passwordEncoder(passwordEncoder);
    }
}
