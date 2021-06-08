package deti.tqs.g305.servicemanagement.restcontroller;

import deti.tqs.g305.servicemanagement.configuration.JwtTokenUtil;
import deti.tqs.g305.servicemanagement.exception.ResourceNotFoundException;
import deti.tqs.g305.servicemanagement.model.JwtRequest;
import deti.tqs.g305.servicemanagement.model.JwtResponse;
import deti.tqs.g305.servicemanagement.model.User;
import deti.tqs.g305.servicemanagement.repository.UserRepository;
import deti.tqs.g305.servicemanagement.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        log.info("createAuthenticationToken() given request: {}", authenticationRequest);

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        log.info("USER AUTHENTICATED, finding user...");

        User u = userRepository.findById(authenticationRequest.getUsername()).orElseThrow(() -> new ResourceNotFoundException("Person not found for this email: " + authenticationRequest.getUsername()));;

        log.info("Getting UserDetails for {}", u);

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

        log.info("Got them: {} // Generating token...", userDetails);

        final String token = jwtTokenUtil.generateToken(userDetails);

        log.info("Token generated! {} // Returning", token);

        return ResponseEntity.ok(new JwtResponse(token, userDetails.getAuthorities().iterator().next(), u.getFull_name(), u.getEmail()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            log.info("ERROR: User disabled!");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            log.info("ERROR: Invalid credentials!");
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
