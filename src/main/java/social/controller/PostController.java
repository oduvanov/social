package social.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import social.entity.Post;
import social.mapper.PostMapper;
import social.model.PostCreateRequest;
import social.model.PostUpdateRequest;
import social.model.PostDto;
import social.service.PostService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @PostMapping(path = "/post/create")
    public ResponseEntity<UUID> createPost(@RequestBody @Validated PostCreateRequest postCreateRequest,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        Post post = postService.createPost(postCreateRequest.getText(), userId);
        return ResponseEntity.ok(post.getId());
    }

    @PutMapping(path = "/post/update")
    public ResponseEntity<?> updatePost(@RequestBody @Validated PostUpdateRequest postUpdateRequest,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        postService.updatePost(postUpdateRequest.getId(), postUpdateRequest.getText(), userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/post/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable @NotNull UUID id,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        postService.deletePost(id, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/post/get/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable @NotNull UUID id) {
        PostDto postDto = postService.getPost(id)
                .map(postMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        return ResponseEntity.ok(postDto);
    }

    @GetMapping(path = "/post/feed")
    public ResponseEntity<List<PostDto>> getPost(
            @RequestParam int offset,
            @RequestParam int limit,
            @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        List<PostDto> postDto = postService.getFeed(userId).stream()
                .skip(offset)
                .limit(limit)
                .map(postMapper::toDto)
                .toList();
        return ResponseEntity.ok(postDto);
    }
}
