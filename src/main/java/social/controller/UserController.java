package social.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import social.entity.UserProfile;
import social.mapper.UserMapper;
import social.model.AuthResult;
import social.model.RegisterResult;
import social.model.UserDto;
import social.model.RegisterRequest;
import social.service.UserService;
import social.utils.JwtUtils;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> authenticateUser(UUID id, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(id, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(AuthResult.builder().token(jwt).build());
    }

    @PostMapping("/user/register")
    public ResponseEntity<RegisterResult> registerUser(@RequestBody @Validated RegisterRequest registerRequest) {
        UserProfile userProfile = userMapper.fromRegistrationDto(registerRequest);
        userProfile = userService.registerUser(userProfile);
        return ResponseEntity.ok(RegisterResult.builder().userId(userProfile.getId()).build());
    }

    @GetMapping(path = "/user/get/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable @NotNull UUID id) {
        UserProfile userProfile = userService.findUserById(id);
        UserDto dto = userMapper.toDto(userProfile);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(path = "/user/search")
    public ResponseEntity<List<UserDto>> searchUser(@RequestParam("first_name") @NotBlank String firstNamePath,
                                                    @RequestParam("last_name") @NotBlank String lastNamePath) {
        List<UserDto> dtos = userService.searchUsers(firstNamePath, lastNamePath).stream()
                .map(userMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }
}
