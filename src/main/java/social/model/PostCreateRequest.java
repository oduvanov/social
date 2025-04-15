package social.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostCreateRequest {
    @NotBlank
    private String text;
}
