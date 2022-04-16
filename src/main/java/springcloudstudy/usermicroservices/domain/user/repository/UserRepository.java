package springcloudstudy.usermicroservices.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springcloudstudy.usermicroservices.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String getName);

    User findByEmail(String getEmail);

    Optional<User> findByUserId(String userId);
}
