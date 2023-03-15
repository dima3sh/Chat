package avtetika.com.user.service;

import avtetika.com.dto.enums.ParameterType;
import avtetika.com.entity.User;
import avtetika.com.exception.user.UserNotFoundApiException;
import avtetika.com.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UUID> findAll() {
        return userRepository.findAll().stream().map(User::getUserId).collect(Collectors.toList());
    }

    @Override
    public User findUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundApiException::new);
    }

    @Override
    public Boolean isFreeParameter(String parameter, ParameterType type) {
        return userRepository.existsByLogin(parameter);
    }
}
