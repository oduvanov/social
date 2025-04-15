package social.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PostUpdateRequest {
    @NotNull
    private UUID id;
    @NotBlank
    private String text;
}
