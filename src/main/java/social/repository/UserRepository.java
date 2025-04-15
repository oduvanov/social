package social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import social.entity.UserProfile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, String> {

    @Query(value = "SELECT * FROM user_profile where id = :id", nativeQuery = true)
    Optional<UserProfile> findUserById(UUID id);

    @Query(value = """
            INSERT INTO user_profile 
            VALUES (:#{#user.id}, :#{#user.password}, :#{#user.firstName}, :#{#user.secondName}, 
                                :#{#user.birthday}, :#{#user.biography}, :#{#user.city})""", nativeQuery = true)
    @Modifying
    UserProfile saveUser(UserProfile user);

    @Query(value = """
            SELECT * FROM user_profile 
            WHERE first_name LIKE :firstNamePath% AND second_name LIKE :lastNamePath%
            ORDER BY id""",
            nativeQuery = true)
    List<UserProfile> searchUserProfile(String firstNamePath, String lastNamePath);
}
