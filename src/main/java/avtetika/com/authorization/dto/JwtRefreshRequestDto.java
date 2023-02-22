package avtetika.com.authorization.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRefreshRequestDto {

    public String refreshToken;
}
