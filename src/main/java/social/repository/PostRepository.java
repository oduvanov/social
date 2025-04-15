package social.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import social.entity.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    @Query(value = """
            INSERT INTO post 
            VALUES (:#{#post.id}, :#{#post.text}, :#{#post.userId}, :#{#post.createdAt})""",
            nativeQuery = true)
    @Modifying
    void createPost(Post post);

    @Query(value = """
            UPDATE post
            SET text = :#{#post.text}
            WHERE id = :#{#post.id} AND user_id = :#{#post.userId}""",
            nativeQuery = true)
    @Modifying
    void updatePost(@NotNull Post post);

    @Query(value = """
            DELETE FROM post
            WHERE id = :id AND user_id = :userId""",
            nativeQuery = true)
    @Modifying
    void deletePost(@NotNull UUID id, @NotNull UUID userId);

    @Query(value = "SELECT * FROM post WHERE id = :id", nativeQuery = true)
    Optional<Post> findPostById(@NotNull UUID id);

    @Query(value = """
            SELECT p.* FROM friend f
            INNER JOIN post p ON f.friend_id = p.user_id
            WHERE f.user_id = :userId
            ORDER BY created_at DESC
            LIMIT :limit""",
            nativeQuery = true)
    List<Post> findFeed(UUID userId, int limit);
}
