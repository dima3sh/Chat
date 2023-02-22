package avtetika.com.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserResponseDto {

    private UUID userId;
    private String login;
}
