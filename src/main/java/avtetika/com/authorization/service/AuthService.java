package avtetika.com.authorization.service;

import avtetika.com.authorization.dto.JwtRequestDto;
import avtetika.com.authorization.dto.JwtResponseDto;
import lombok.NonNull;

import java.util.UUID;

public interface AuthService {

    JwtResponseDto login(@NonNull JwtRequestDto authRequest);

    JwtResponseDto generateTokens(@NonNull UUID userId);

    JwtResponseDto refresh(@NonNull String refreshToken);
}
