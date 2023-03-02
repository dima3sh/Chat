package avtetika.com.user.repository;

import avtetika.com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllByUserIdIn(List<UUID> request);

    @Query(value = "SELECT u.* from \"user\" u where u.\"login\" LIKE concat(?1, '%') LIMIT ?2", nativeQuery = true)
    List<User> findAllByLoginStartsWith(String request, Integer size);

    @Query("SELECT u FROM User u WHERE u.login=:identification")
    Optional<User> findUserByLogin(String identification);

    Boolean existsByLogin(String nickname);

}
