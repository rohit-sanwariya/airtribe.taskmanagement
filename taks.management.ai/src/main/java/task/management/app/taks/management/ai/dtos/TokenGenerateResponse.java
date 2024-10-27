package task.management.app.taks.management.ai.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenGenerateResponse {
    private String token;
    private LocalDateTime  issued_at;
    private LocalDateTime expires_at;

}
