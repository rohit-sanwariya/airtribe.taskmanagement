package task.management.app.taks.management.ai.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import task.management.app.taks.management.ai.models.Task;
import task.management.app.taks.management.ai.models.User;

import java.time.LocalDateTime;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {


    private String content;

    private Long authorUserID;


    private Long taskID;




}
