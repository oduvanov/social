package social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import social.entity.Friend;
import social.entity.FriendId;

@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendId> {

    @Query(value = """
            INSERT INTO friend 
            VALUES (:#{#friend.friendId.userId}, :#{#friend.friendId.friendId})""",
            nativeQuery = true)
    @Modifying
    void saveFriend(Friend friend);

    @Query(value = """
            DELETE FROM friend
            WHERE user_id = :#{#friend.friendId.userId} AND friend_id = :#{#friend.friendId.friendId}""",
            nativeQuery = true)
    @Modifying
    void deleteFriend(Friend friend);
}
