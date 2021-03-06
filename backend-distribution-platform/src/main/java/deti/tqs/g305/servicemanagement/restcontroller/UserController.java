package deti.tqs.g305.servicemanagement.restcontroller;

import deti.tqs.g305.servicemanagement.configuration.JwtTokenUtil;
import deti.tqs.g305.servicemanagement.exception.UnauthorizedException;
import deti.tqs.g305.servicemanagement.model.*;
import deti.tqs.g305.servicemanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpServletRequest request) throws Exception {

        log.info("createAuthenticationToken() given request: {}", authenticationRequest);

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        log.info("USER AUTHENTICATED, finding user...");

        User u = userService.getUserByEmail(authenticationRequest.getUsername()).get();

        log.info("Getting UserDetails for {}", u);

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

        log.info("Got them: {}", userDetails);

        // If CLIENT, check that has API token
        if (userDetails.getAuthorities().contains(new UserAuthority(UserAuthorities.CLIENT.name()))) {
            String APIToken = request.getHeader("APIToken");
            log.info("CLIENT! Validating Business key: {}", APIToken);
            if (APIToken==null) {
                log.error("Client logged without Business API key!");
                throw new UnauthorizedException("CLIENT login must be made through an end service.");
            } else if (userService.getBusinessByApiKey(APIToken).isEmpty()) {
                log.error("Client logged with INVALID Business API key!");
                throw new UnauthorizedException("Invalid API token.");
            }
        } else {
            log.info("User is not client! Generating token...");
        }

        final String token = jwtTokenUtil.generateToken(userDetails);

        log.info("Token generated! {} // Returning", token);

        return ResponseEntity.ok(new JwtResponse(token, (UserAuthority) userDetails.getAuthorities().iterator().next(), u.getFull_name(), u.getEmail()));
    }

    @GetMapping("/logged")
    public ResponseEntity<?> getUserLogged(HttpServletRequest request) {
        // Get user logged
        Principal principal = request.getUserPrincipal();
        log.info("principal is {}", principal);
        // Get User instance
        User u = userService.getUserByEmail(principal.getName()).get();
        final UserDetails userDetails = userService.loadUserByUsername(principal.getName());

        return ResponseEntity.ok(new UserResponse((UserAuthority) userDetails.getAuthorities().iterator().next(), u.getFull_name(), u.getEmail()));
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
