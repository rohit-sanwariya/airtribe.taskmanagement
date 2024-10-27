package task.management.app.taks.management.ai.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "teams")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamID;

    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "team_members",
            joinColumns = @JoinColumn(name = "teamID"),
            inverseJoinColumns = @JoinColumn(name = "userID")
    )
    private List<User> members;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
