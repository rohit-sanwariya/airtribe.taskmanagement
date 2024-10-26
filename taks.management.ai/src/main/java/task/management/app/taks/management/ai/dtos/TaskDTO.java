package task.management.app.taks.management.ai.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import task.management.app.taks.management.ai.enums.Status;
import task.management.app.taks.management.ai.enums.TaskPriority;

import java.beans.ConstructorProperties;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

//@Builder
//@AllArgsConstructor
@Data
@NoArgsConstructor
public class TaskDTO {
    private Long taskId;
    private String title;
    private String description;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private Long userId;
    private String userEmail;

    public TaskDTO(Long taskId, String title, String description, LocalDateTime dueDate, Status status, Long userId, String userEmail) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate.toLocalDate();
        this.status = status;
        this.userId = userId;
        this.userEmail = userEmail;
    }
}