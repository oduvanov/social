package social.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social.entity.UserProfile;
import social.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserProfile findUserById(UUID userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId.toString()));
    }

    @Override
    public UserProfile loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findUserById(UUID.fromString(userId))
                .orElseThrow(() -> new UsernameNotFoundException(userId));
    }

    @Transactional
    public UserProfile registerUser(UserProfile userProfile) {
        userRepository.findUserById(userProfile.getId())
                .ifPresent(u -> {throw new IllegalArgumentException("Username already exists");});
        return userRepository.save(userProfile);
    }

    public List<UserProfile> searchUsers(String firstNamePath, String lastNamePath) {
        return userRepository.searchUserProfile(firstNamePath, lastNamePath);
    }
}
