package springcloudstudy.usermicroservices;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
@ConfigurationPropertiesScan(basePackages = "springcloudstudy.usermicroservices.api.properties")
public class UserMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroservicesApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder creatBCryptEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	@LoadBalanced // Gateway 안 거치고 바로 다른 서비스에 호출하기 위해서 사용, discovery에서 주소를 가져와서 바로 가게 해준다.
	public RestTemplate createRestTemplate(){
		return new RestTemplate();
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}