package avtetika.com.authorization.service;

import avtetika.com.authorization.dto.JwtRequestDto;
import avtetika.com.authorization.dto.JwtResponseDto;
import avtetika.com.authorization.dto.SignUpRequestDto;
import avtetika.com.entity.User;
import avtetika.com.exception.ApiException;
import avtetika.com.exception.user.UserNotFoundApiException;
import avtetika.com.user.repository.UserRepository;
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

    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    private final Map<UUID, String> refreshStorage = new HashMap<>();

    @Autowired
    public AuthServiceImpl(PasswordEncoder encoder,
                           JwtProvider jwtProvider,
                           UserRepository userRepository) {
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    @Override
    public JwtResponseDto login(@NonNull JwtRequestDto authRequest) {
        final User user = userRepository.findUserByLogin(authRequest.getLogin())
                .orElseThrow(UserNotFoundApiException::new);
        if (encoder.matches(authRequest.getPassword(), user.getPassword())) {
            return generateTokens(user);
        }
        throw new ApiException("Incorrect password");
    }

    @Override
    public JwtResponseDto signUp(SignUpRequestDto request) {
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(encoder.encode(request.getPassword()));
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new ApiException("User with this login: '" + request.getLogin() + " is already exists");
        }
        userRepository.save(user);
        return generateTokens(user);
    }

    @Override
    public JwtResponseDto refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String userId = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(UUID.fromString(userId));
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userRepository.findById(UUID.fromString(userId))
                        .orElseThrow(UserNotFoundApiException::new);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getUserId(), newRefreshToken);
                return new JwtResponseDto(accessToken, newRefreshToken);
            }
        }
        throw new ApiException("Невалидный JWT токен");
    }

    private JwtResponseDto generateTokens(User user) {
        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshStorage.put(user.getUserId(), refreshToken);
        return new JwtResponseDto(accessToken, refreshToken);
    }
}
