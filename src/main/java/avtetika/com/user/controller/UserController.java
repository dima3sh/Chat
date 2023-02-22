package avtetika.com.user.controller;

import avtetika.com.dto.enums.ParameterType;
import avtetika.com.dto.user.UserInfoDto;
import avtetika.com.dto.user.UserResponseDto;
import avtetika.com.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/check/{parameter}", params = {"type"})
    public ResponseEntity<Boolean> checkParameter(@PathVariable("parameter") String parameter, @RequestParam ParameterType type) {
        Boolean response = userService.isFreeParameter(parameter, type);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/user/login/{login}")
    public ResponseEntity<UserInfoDto> updateLogin(@RequestAttribute UUID userId, @PathVariable String login) {
        UserInfoDto response = userService.findInfo(userId);
        return ResponseEntity.ok(response);
    }
}
