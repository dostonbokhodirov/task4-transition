package intern.task4.authorization.entity;

import intern.task4.authorization.enums.Status;
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

    @Column(columnDefinition = "timestamp with time zone")
    private LocalDateTime lastLoginTime;

    @CreatedDate
    @CreationTimestamp
    @Column(columnDefinition = "timestamp with time zone default now()")
    private LocalDateTime registrationTime;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Column
    private boolean selected;

}
