package task.management.app.taks.management.ai.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import task.management.app.taks.management.ai.dtos.AuthResponse;
import task.management.app.taks.management.ai.dtos.LoginRequest;
import task.management.app.taks.management.ai.dtos.TokenGenerateResponse;
import task.management.app.taks.management.ai.dtos.UserCreateDto;
import task.management.app.taks.management.ai.models.User;
import task.management.app.taks.management.ai.services.JwtTokenService;
import task.management.app.taks.management.ai.services.UserService;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginIn(@RequestBody  LoginRequest request){
        final String name =  request.getUsername();
        final String password = request.getPassword();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(name, password)
        );
        User user = userService.getUserByEmail(name);
        TokenGenerateResponse tokenGenerateResponse = jwtTokenService.generateToken(name);
        return ResponseEntity.ok(
                AuthResponse
                        .builder()
                        .access_token(tokenGenerateResponse.getToken())
                        .issued_at(tokenGenerateResponse.getIssued_at())
                        .expires_in(tokenGenerateResponse.getExpires_at())
                        .userid(user.getId())
                        .username(user.getEmail())
                        .build()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody UserCreateDto user){
        AuthResponse authResponse = new AuthResponse();
        User newUser = userService.create(user);
        authResponse.setUserid(newUser.getId());
        authResponse.setUsername(newUser.getEmail());
        TokenGenerateResponse response = jwtTokenService.generateToken(newUser.getEmail());
        authResponse.setAccess_token(response.getToken());
        authResponse.setIssued_at(response.getIssued_at());
        authResponse.setExpires_in(response.getExpires_at());
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("test")
    public String test(){
        return "test";
    }

}
