package task.management.app.taks.management.ai.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {
    private String access_token;
    private LocalDateTime expires_in;
    private LocalDateTime issued_at;
    private  Long userid;
    private String username;

}
