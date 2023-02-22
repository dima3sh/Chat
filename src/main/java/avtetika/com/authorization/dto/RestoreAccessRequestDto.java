package avtetika.com.authorization.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class RestoreAccessRequestDto {

    @NotNull
    private String password;
    private UUID restoreToken;
}
