package avtetika.com.authorization.service;

import avtetika.com.authorization.dto.JwtRequestDto;
import avtetika.com.authorization.dto.JwtResponseDto;
import avtetika.com.entity.User;
import avtetika.com.exception.ApiException;
import avtetika.com.exception.user.UserNotFoundApiException;
import com.sun.istack.NotNull;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserSecurityService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;

    private final Map<UUID, String> refreshStorage = new HashMap<>();

    @Autowired
    public AuthServiceImpl(UserSecurityServiceImpl userService, PasswordEncoder encoder, JwtProvider jwtProvider) {
        this.userService = userService;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public JwtResponseDto login(@NonNull JwtRequestDto authRequest) {
        final User user = userService.findByLoginOrEmail(authRequest.getLogin())
                .orElseThrow(UserNotFoundApiException::new);
        if (encoder.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getUserId(), refreshToken);
            return new JwtResponseDto(accessToken, refreshToken);
        }
        throw new ApiException("Incorrect password");
    }

    @Override
    public JwtResponseDto generateTokens(@NotNull UUID userId) {
        final User user = userService.findById(userId)
                .orElseThrow(UserNotFoundApiException::new);
        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshStorage.put(user.getUserId(), refreshToken);
        return new JwtResponseDto(accessToken, refreshToken);
    }

    @Override
    public JwtResponseDto refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String userId = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(UUID.fromString(userId));
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.findById(UUID.fromString(userId))
                        .orElseThrow(UserNotFoundApiException::new);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getUserId(), newRefreshToken);
                return new JwtResponseDto(accessToken, newRefreshToken);
            }
        }
        throw new ApiException("Невалидный JWT токен");
    }

}
