package social.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResult {
    private String token;
}
