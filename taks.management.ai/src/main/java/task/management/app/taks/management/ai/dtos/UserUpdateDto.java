package task.management.app.taks.management.ai.dtos;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    private String firstname;
    private String lastname;
    private String email;      // Include this only if users are allowed to update their email
    private String profileImage;
    private LocalDateTime dob;

}

