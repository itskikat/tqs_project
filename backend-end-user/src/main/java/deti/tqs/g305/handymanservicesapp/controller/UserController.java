package deti.tqs.g305.handymanservicesapp.controller;

import deti.tqs.g305.handymanservicesapp.model.*;
import deti.tqs.g305.handymanservicesapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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

    @PostMapping(path = {"/"})
    public Optional create(@RequestBody Client client, HttpServletRequest request){
        log.info("Creating client {} with password {}", client.getEmail(), client.getPassword());
        return userService.create(client, request);
    }

    @PutMapping(value="/{email}")
    public ResponseEntity<?> update(@PathVariable("email") String email, @RequestBody Client client, HttpServletRequest request) {
        return null;
        /*
        Optional<Client> b = userService.update(email, client);
        if(b.isPresent()){
            return ResponseEntity.ok().body(b.get());
        }
        return new ResponseEntity<String>("Bad business", HttpStatus.BAD_REQUEST);
        */
    }

}
