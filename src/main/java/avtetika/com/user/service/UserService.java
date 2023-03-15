package avtetika.com.user.service;

import avtetika.com.dto.enums.ParameterType;
import avtetika.com.dto.user.UserResponseDto;
import avtetika.com.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UUID> findAll();

    User findUser(UUID userId);

    Boolean isFreeParameter(String login, ParameterType type);
}
