package avtetika.com.authorization.service;

import avtetika.com.authorization.dto.JwtRequestDto;
import avtetika.com.authorization.dto.JwtResponseDto;
import avtetika.com.authorization.dto.SignUpRequestDto;
import lombok.NonNull;

public interface AuthService {

    JwtResponseDto login(@NonNull JwtRequestDto authRequest);

    JwtResponseDto refresh(@NonNull String refreshToken);

    JwtResponseDto signUp(SignUpRequestDto request);
}
