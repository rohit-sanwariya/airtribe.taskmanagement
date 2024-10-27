package task.management.app.taks.management.ai.dtos;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import task.management.app.taks.management.ai.enums.Role;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String profileImage;
    private LocalDateTime createdat;
    private LocalDateTime updatedat;
    private LocalDateTime dob;
    private Role role;

}
