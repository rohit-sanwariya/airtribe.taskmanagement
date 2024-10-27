package task.management.app.taks.management.ai.models;

import jakarta.persistence.*;
import lombok.*;
import task.management.app.taks.management.ai.enums.Priority;
import task.management.app.taks.management.ai.enums.Role;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "created_at")
    private LocalDateTime createdat;

    @Column(name = "update_at")
    private LocalDateTime updatedat;

    @Column(name = "dob")
    private LocalDateTime dob;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(Long authorUserID) {
        this.id = authorUserID;
    }
}
