package avtetika.com.authorization.service;

import avtetika.com.authorization.dto.SignUpRequestDto;
import avtetika.com.entity.User;
import avtetika.com.exception.ApiException;
import avtetika.com.user.repository.UserRepository;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserSecurityServiceImpl(UserRepository userRepository,
                                   PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByLoginOrEmail(String nickname) {
        return userRepository.findUserByLoginOrEmail(nickname);
    }

    @Override
    public void signUp(SignUpRequestDto request) {
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(encoder.encode(request.getPassword()));
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new ApiException("User with this login: '" + request.getLogin() + "is already exists");
        }
        userRepository.save(user);
    }
}
