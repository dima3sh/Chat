package avtetika.com.user.repository;

import avtetika.com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.login=:identification")
    Optional<User> findUserByLogin(String identification);

    Boolean existsByLogin(String nickname);

}
