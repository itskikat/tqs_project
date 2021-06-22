package deti.tqs.g305.handymanservicesapp.controller;

import deti.tqs.g305.handymanservicesapp.model.JwtRequest;
import deti.tqs.g305.handymanservicesapp.model.JwtResponse;
import deti.tqs.g305.handymanservicesapp.model.UserAuthority;
import deti.tqs.g305.handymanservicesapp.model.UserResponse;
import deti.tqs.g305.handymanservicesapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        log.info("Authentication attempt for user: {}", authenticationRequest.getUsername());

        // Forward request to backbone API
        JwtResponse jwtResponse = userService.logIn(authenticationRequest);
        log.info("Service returned response {}", jwtResponse);
        return ResponseEntity.ok(jwtResponse);
    }

    @GetMapping("/logged")
    public ResponseEntity<UserResponse> createAuthenticationToken(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userService.getUserLogged(request));
    }

}
