package avtetika.com.user.mapping;

import avtetika.com.dto.user.UserResponseDto;
import avtetika.com.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    UserResponseDto map(User user);

    List<UserResponseDto> map(List<User> users);
}
