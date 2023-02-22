package avtetika.com.authorization.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class JwtRequestDto {

    @NotNull
    private String login;

    @NotNull
    private String password;
}
