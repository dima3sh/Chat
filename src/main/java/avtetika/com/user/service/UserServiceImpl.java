package avtetika.com.user.service;

import avtetika.com.dto.enums.ParameterType;
import avtetika.com.dto.user.UserInfoDto;
import avtetika.com.dto.user.UserResponseDto;
import avtetika.com.entity.User;
import avtetika.com.exception.ApiException;
import avtetika.com.exception.user.UserNotFoundApiException;
import avtetika.com.user.mapping.UserMapper;
import avtetika.com.user.repository.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findUsers(Collection<UUID> request) {
        return userRepository.findAllByUserIdIn(new ArrayList<>(request));
    }

    @Override
    public User findUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundApiException::new);
    }

    @Override
    public List<UserResponseDto> searchUsers(String login, Integer size) {
        return Mappers.getMapper(UserMapper.class).map(userRepository.findAllByLoginStartsWith(login, size));
    }

    @Override
    public Boolean isFreeParameter(String parameter, ParameterType type) {
        return userRepository.existsByLogin(parameter);
    }

    @Override
    public Boolean updateLogin(UUID userId, String login) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundApiException::new);
        if (userRepository.existsByLogin(login)) {
            throw new ApiException("User with login has already created");
        }
        user.setLogin(login);
        userRepository.save(user);
        return true;
    }
}
