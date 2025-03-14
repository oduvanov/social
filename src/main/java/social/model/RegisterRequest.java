package social.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterRequest {
    @NotBlank
    @Length(min = 5, max = 255)
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String secondName;
    @NotNull
    private LocalDate birthday;
    private String biography;
    private String city;
}
