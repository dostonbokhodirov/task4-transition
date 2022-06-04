package intern.task4.authorization.entity;

import intern.task4.authorization.enums.Role;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "auth_user")
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @CreatedDate
    @CreationTimestamp
    @Column(name = "registration_time", columnDefinition = "timestamp default now()")
    private LocalDateTime registrationTime;

    @Column(name = "is_active", columnDefinition = "BOOLEAN default false")
    private Boolean isActive;

}
