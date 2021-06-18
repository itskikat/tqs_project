package deti.tqs.g305.handymanservicesapp.service;

import deti.tqs.g305.handymanservicesapp.exceptions.UnauthorizedException;
import deti.tqs.g305.handymanservicesapp.model.JwtRequest;
import deti.tqs.g305.handymanservicesapp.model.JwtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserDetails.class);

    String url = "http://localhost:8080/api/users";

    private RestTemplate restTemplate = new RestTemplate();

    public UserDetails getUserByToken(String token) {
        return restTemplate.getForObject(url + "/logged", UserDetails.class);
    }

    public JwtResponse logIn(JwtRequest request) throws UnauthorizedException {
        // Try to log in on backbone API, if bad credentials, return 401
        JwtResponse response = null;
        try {
            response = restTemplate.postForObject(url + "/login", request, JwtResponse.class);
        } catch (HttpStatusCodeException e) {
            throw new UnauthorizedException("Invalid credentials!");
        }
        // Validate that user has authority CLIENT, else return 401
        if (!response.getType().getAuthority().equals("CLIENT")) {
            throw new UnauthorizedException(String.format("Your authority \"%s\" does not grant you access to this service!", response.getType().getAuthority()));
        }
        return response;
    }
}
