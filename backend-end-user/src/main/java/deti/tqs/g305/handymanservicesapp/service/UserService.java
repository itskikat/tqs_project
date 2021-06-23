package deti.tqs.g305.handymanservicesapp.service;

import deti.tqs.g305.handymanservicesapp.configuration.RequestsHelper;
import deti.tqs.g305.handymanservicesapp.exceptions.UnauthorizedException;
import deti.tqs.g305.handymanservicesapp.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserDetails.class);

    @Autowired
    private RequestsHelper requestsHelper;

    @Value("${enduser.apiurl}")
    String apiBaseUrl;

    private RestTemplate restTemplate = new RestTemplate();

    public JwtResponse logIn(JwtRequest request) throws UnauthorizedException {
        // Try to log in on backbone API, if bad credentials, return 401
        JwtResponse response = null;
        HttpEntity entity = new HttpEntity(request, requestsHelper.getHeaders());
        try {
            ResponseEntity<JwtResponse> r = restTemplate.postForEntity(apiBaseUrl + "/users/login", entity, JwtResponse.class);
            response = r.getBody();
            log.info("User logged! Token generated: {}", response.getToken());
        } catch (HttpStatusCodeException e) {
            log.error("Backbone service returned exception with code {}, returning invalid credentials...", e.getStatusCode());
            throw new UnauthorizedException("Invalid credentials!");
        }
        // Validate that user has authority CLIENT, else return 401
        if (!response.getType().getAuthority().equals("CLIENT")) {
            log.error("Stop! User is {} (not CLIENT). Returning 401...", response.getType().getAuthority());
            throw new UnauthorizedException(String.format("Your authority \"%s\" does not grant you access to this service!", response.getType().getAuthority()));
        }
        return response;
    }

    public UserResponse getUserLogged(HttpServletRequest request) throws UnauthorizedException {
        UserResponse ud = null;
        try {
            ud = restTemplate.exchange(apiBaseUrl + "/users/logged", HttpMethod.GET, requestsHelper.getEntityWithAuthorization(request.getHeader("Authorization")), UserResponse.class).getBody();
        } catch (HttpStatusCodeException e) {
            throw new UnauthorizedException("Session expired!");
        }
        return ud;
    }

    public Optional create(Client c, HttpServletRequest request) {
        HttpEntity<Client> entity = new HttpEntity<>(c, requestsHelper.getHeadersWithAuthorization(request.getHeader("Authorization")));
        return restTemplate.exchange(apiBaseUrl + "/client/", HttpMethod.POST, entity, Optional.class).getBody();
    }

    public Optional update(String email, Client c, HttpServletRequest request) {
        log.info("PUT to {}/client/{}", apiBaseUrl, email);
        HttpEntity<Client> entity = new HttpEntity<>(c, requestsHelper.getHeadersWithAuthorization(request.getHeader("Authorization")));
        return restTemplate.exchange(apiBaseUrl + "/client/" + email, HttpMethod.PUT, entity, Optional.class).getBody();
    }

    public Optional getClientLogged(HttpServletRequest request) {
        log.info("Getting client logged for user ", request.getUserPrincipal());
        return restTemplate.exchange(apiBaseUrl + "/client/", HttpMethod.GET, requestsHelper.getEntityWithAuthorization(request.getHeader("Authorization")), Optional.class).getBody();
    }


}
