package avtetika.com.authorization.service;

import avtetika.com.authorization.dto.RestoreAccessRequestDto;
import avtetika.com.authorization.dto.SignUpRequestDto;
import avtetika.com.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserSecurityService {

    Optional<User> findById(UUID userId);

    Optional<User> findByLoginOrEmail(String nickname);

    void signUp(SignUpRequestDto request);

}
