package avtetika.com.user.service;

import avtetika.com.dto.enums.ParameterType;
import avtetika.com.dto.user.UserResponseDto;
import avtetika.com.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> findUsers(Collection<UUID> request);

    User findUser(UUID userId);

    List<UserResponseDto> searchUsers(String login, Integer size);

    Boolean isFreeParameter(String login, ParameterType type);

    Boolean updateLogin(UUID userId, String login);
}
