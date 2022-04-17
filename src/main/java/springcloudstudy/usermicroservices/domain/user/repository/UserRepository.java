package springcloudstudy.usermicroservices.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springcloudstudy.usermicroservices.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String getName);

    Optional<User> findByEmail(String getEmail);

    Optional<User> findByUserId(String userId);
}
