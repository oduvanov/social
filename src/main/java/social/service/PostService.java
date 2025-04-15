package social.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social.entity.Post;
import social.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private static final int FEED_LIMIT = 1000;

    @CacheEvict(value = "user_feed", allEntries = true)
    @Transactional
    public Post createPost(@NotBlank String text, UUID userId) {
        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setText(text);
        post.setUserId(userId);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.createPost(post);
        return post;
    }

    @Transactional
    public void updatePost(@NotNull UUID id, @NotBlank String text, UUID userId) {
        Post post = getPost(id)
                .filter(p -> p.getUserId().equals(userId))
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        post.setText(text);
        postRepository.updatePost(post);
    }

    @CacheEvict(value = "user_feed", allEntries = true)
    @Transactional
    public void deletePost(@NotNull UUID id, UUID userId) {
        postRepository.deletePost(id, userId);
    }

    public Optional<Post> getPost(@NotNull UUID id) {
        return postRepository.findPostById(id);
    }

    @Cacheable(cacheNames = "user_feed", key = "#userId", sync = true)
    public List<Post> getFeed(UUID userId) {
        return postRepository.findFeed(userId, FEED_LIMIT);
    }
}
