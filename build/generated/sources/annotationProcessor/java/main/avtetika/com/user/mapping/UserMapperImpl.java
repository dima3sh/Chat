package avtetika.com.user.mapping;

import avtetika.com.dto.user.UserResponseDto;
import avtetika.com.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto map(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setUserId( user.getUserId() );
        userResponseDto.setLogin( user.getLogin() );

        return userResponseDto;
    }

    @Override
    public List<UserResponseDto> map(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserResponseDto> list = new ArrayList<UserResponseDto>( users.size() );
        for ( User user : users ) {
            list.add( map( user ) );
        }

        return list;
    }
}
