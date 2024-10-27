package task.management.app.taks.management.ai.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import task.management.app.taks.management.ai.enums.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    private String firstname;
    private String lastname;
    private String email;
    private String password;


}