package avtetika.com.authorization.controller;

import avtetika.com.authorization.dto.JwtRefreshRequestDto;
import avtetika.com.authorization.dto.JwtRequestDto;
import avtetika.com.authorization.dto.JwtResponseDto;
import avtetika.com.authorization.dto.SignUpRequestDto;
import avtetika.com.authorization.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody JwtRequestDto authRequest) {
        final JwtResponseDto token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("signup")
    public ResponseEntity<JwtResponseDto> signUp(@Valid @RequestBody SignUpRequestDto request) {
        return ResponseEntity.ok(authService.signUp(request));
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponseDto> getNewRefreshToken(@RequestBody JwtRefreshRequestDto request) {
        JwtResponseDto token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("logout")
    @PreAuthorize("isAuthenticated()")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
