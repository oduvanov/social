package social.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class UserProfile implements UserDetails {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;
    private String password;

    @NotBlank
    private String firstName;
    @NotBlank
    private String secondName;
    @NotNull
    private LocalDate birthday;
    private String biography;
    private String city;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return id.toString();
    }
}
