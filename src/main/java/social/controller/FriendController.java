package social.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import social.service.FriendService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PutMapping(path = "/friend/set/{user_id}")
    public ResponseEntity<?> setFriend(@PathVariable("user_id") @NotNull UUID friendId,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        friendService.setFriend(userId, friendId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/friend/delete/{user_id}")
    public ResponseEntity<?> deleteFriend(@PathVariable("user_id") @NotNull UUID friendId,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        friendService.deleteFriend(userId, friendId);
        return ResponseEntity.ok().build();
    }
}
