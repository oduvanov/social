package social.model;

import lombok.Data;

import java.util.UUID;

@Data
public class PostDto {
    private UUID postId;
    private String postText;
    private String userId;
}
