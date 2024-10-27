package task.management.app.taks.management.ai.models;


import jakarta.persistence.*;
import lombok.*;
import task.management.app.taks.management.ai.enums.Status;
import task.management.app.taks.management.ai.enums.TaskPriority;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Builder

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "duedate")
    private LocalDateTime dueDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;


    @ManyToOne
    @JoinColumn(name = "assignedUserID", referencedColumnName = "user_id")
    private User assignedUser;

    public Task(Long taskID) {
        this.id = taskID;
    }
}
