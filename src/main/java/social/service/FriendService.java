package social.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social.entity.Friend;
import social.entity.FriendId;
import social.repository.FriendRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames="user_feed", key="#userId"),
            @CacheEvict(cacheNames="user_feed", key="#friendId")
    })
    public void setFriend(UUID userId, @NotNull UUID friendId) {
        Friend friend = new Friend(new FriendId(userId, friendId));
        friendRepository.saveFriend(friend);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames="user_feed", key="#userId"),
            @CacheEvict(cacheNames="user_feed", key="#friendId")
    })
    public void deleteFriend(UUID userId, @NotNull UUID friendId) {
        Friend friend = new Friend(new FriendId(userId, friendId));
        friendRepository.deleteFriend(friend);
    }
}
