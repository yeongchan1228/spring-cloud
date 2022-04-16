package springcloudstudy.usermicroservices.domain.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@EntityListeners(value = AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String encryptedPwd;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = true)
    private String username;

    @CreatedDate
    private LocalDate createdDate;

    @Builder(builderMethodName = "createUser")
    public User(String email, String encryptedPwd, String userId, String username, LocalDate createdDate) {
        this.email = email;
        this.encryptedPwd = encryptedPwd;
        this.userId = userId;
        this.username = username;
        this.createdDate = createdDate;
    }
}
