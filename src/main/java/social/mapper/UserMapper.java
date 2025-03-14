package social.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import social.entity.UserProfile;
import social.model.UserDto;
import social.model.RegisterRequest;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    public abstract UserDto toDto(UserProfile userProfile);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(registerRequest.getPassword()))")
    public abstract UserProfile fromRegistrationDto(RegisterRequest registerRequest);
}
