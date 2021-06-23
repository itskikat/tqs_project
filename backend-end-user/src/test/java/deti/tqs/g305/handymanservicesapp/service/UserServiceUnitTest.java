package deti.tqs.g305.handymanservicesapp.service;

import deti.tqs.g305.handymanservicesapp.configuration.RequestsHelper;
import deti.tqs.g305.handymanservicesapp.exceptions.UnauthorizedException;
import deti.tqs.g305.handymanservicesapp.model.*;
import org.h2.engine.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Value("${enduser.apiurl}")
    String apiBaseUrl;

    @Value("${enduser.token}")
    String token;

    @Mock(lenient = true)
    private RestTemplate restTemplate;

    @Mock(lenient = true)
    private RequestsHelper requestsHelper;

    @InjectMocks
    private UserService userService;

    private HttpHeaders headers;
    private HttpHeaders headersAuthorization;

    @BeforeEach
    void setUp() {
        // Mock RequestHelper
        headers = new HttpHeaders();
        headers.set("APIToken", token);
        when(requestsHelper.getHeaders()).thenReturn(headers);

        headersAuthorization = new HttpHeaders();
        headersAuthorization.set("APIToken", token);
        headersAuthorization.set("Authorization", token);
        when(requestsHelper.getEntityWithAuthorization(any())).thenReturn(new HttpEntity(headersAuthorization));
    }

    @Test
    void whenLogInValidClient_thenReturnJwtResponse() throws UnauthorizedException {
        // Mock API
        JwtRequest clientPos = new JwtRequest("client", "right");
        JwtResponse responseClient = new JwtResponse("token", new UserAuthority("CLIENT"), "Many", "many@ua.pt");
        when(restTemplate.postForEntity(apiBaseUrl + "/users/login", new HttpEntity(clientPos, headers), JwtResponse.class)).thenReturn(ResponseEntity.of(Optional.of(responseClient)));

        // Call service
        JwtResponse jwt = userService.logIn(clientPos);

        // Validate response
        assertThat(jwt.getToken()).isEqualTo(responseClient.getToken());
        assertThat(jwt.getEmail()).isEqualTo(responseClient.getEmail());

        // Validate API usage
        verify(restTemplate, times(1)).postForEntity(eq(apiBaseUrl + "/users/login"), any(), eq(JwtResponse.class));
    }

    @Test
    void whenLoginInvalidCredentials_thenReturn401InvalidCredentials() throws UnauthorizedException {
        // Mock API
        JwtRequest clientNeg = new JwtRequest("client", "wrong");
        when(restTemplate.postForEntity(eq(apiBaseUrl + "/users/login"), any(), eq(JwtResponse.class))).thenThrow(new HttpServerErrorException(HttpStatus.UNAUTHORIZED));

        // Call service and validate that it throws
        assertThatThrownBy(() -> userService.logIn(clientNeg))
            .isInstanceOf(UnauthorizedException.class)
            .hasMessageContaining("Invalid credentials!");

        // Validate API usage
        verify(restTemplate, times(1)).postForEntity(eq(apiBaseUrl + "/users/login"), any(), eq(JwtResponse.class));
    }

    @Test
    void whenLoginValidCredentials_InvalidRole_thenReturn401AccessDenied() throws UnauthorizedException {
        // Mock API
        JwtRequest business = new JwtRequest("business", "password");
        JwtResponse responseBusiness = new JwtResponse("token", new UserAuthority("BUSINESS"), "Many", "many@ua.pt");
        when(restTemplate.postForEntity(eq(apiBaseUrl + "/users/login"), any(), eq(JwtResponse.class))).thenReturn(ResponseEntity.of(Optional.of(responseBusiness)));


        // Call service and validate that it throws
        assertThatThrownBy(() -> userService.logIn(business))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining(String.format("Your authority \"%s\" does not grant you access to this service!", responseBusiness.getType().getAuthority()));

        // Validate API usage
        verify(restTemplate, times(1)).postForEntity(eq(apiBaseUrl + "/users/login"), any(), eq(JwtResponse.class));
    }

    @Test
    void whenGetUserLoggedValidRequest_thenReturnUserResponse() throws UnauthorizedException {
        // Mock API
        UserResponse ur = new UserResponse(new UserAuthority("CLIENT"), "name", "email@ua.pt");
        when(restTemplate.exchange(eq(apiBaseUrl + "/users/logged"), eq(HttpMethod.GET), any(), eq(UserResponse.class))).thenReturn(ResponseEntity.of(Optional.of(ur)));

        // Call service
        UserResponse response = userService.getUserLogged(new MockHttpServletRequest());

        // Validate response
        assertThat(response.getEmail()).isEqualTo(ur.getEmail());
        assertThat(response.getType()).isEqualTo(ur.getType());

        // Validate API usage
        verify(restTemplate, times(1)).exchange(eq(apiBaseUrl + "/users/logged"), eq(HttpMethod.GET), any(), eq(UserResponse.class));
    }

    @Test
    void whenGetUserLoggedInvalidRequest_thenReturnUserResponse() throws UnauthorizedException {
        // Mock API
        when(restTemplate.exchange(eq(apiBaseUrl + "/users/logged"), eq(HttpMethod.GET), any(), eq(UserResponse.class))).thenThrow(new HttpServerErrorException(HttpStatus.UNAUTHORIZED));

        // Call service and validate that it throws
        assertThatThrownBy(() -> userService.getUserLogged(new MockHttpServletRequest()))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("Session expired!");

        // Validate API usage
        verify(restTemplate, times(1)).exchange(eq(apiBaseUrl + "/users/logged"), eq(HttpMethod.GET), any(), eq(UserResponse.class));
    }

    @Test
    void whenUpdateUser_thenReturnUser() {
        // Mock API
        Client client = new Client("client@ua.pt", "abc", "First Last Name", "Client's address street", null, LocalDate.now());
        when(restTemplate.exchange(contains(apiBaseUrl + "/client/" + client.getEmail()), eq(HttpMethod.PUT), any(), eq(Optional.class))).thenReturn(ResponseEntity.ok(Optional.of(client)));

        // Call service
        Optional<Client> response = userService.update(client.getEmail(), new Client(), new MockHttpServletRequest());

        // Validate response
        assertThat(response.get().getEmail()).isEqualTo(client.getEmail());

        // Validate API usage
        verify(restTemplate, times(1)).exchange(contains(apiBaseUrl + "/client/" + client.getEmail()), eq(HttpMethod.PUT), any(), eq(Optional.class));
    }

    @Test
    void whenGetLogged_thenReturnLogged() {
        // Mock API
        Client client = new Client("client@ua.pt", "abc", "First Last Name", "Client's address street", null, LocalDate.now());
        when(restTemplate.exchange(contains(apiBaseUrl + "/client/"), eq(HttpMethod.GET), any(), eq(Optional.class))).thenReturn(ResponseEntity.ok(Optional.of(client)));

        // Call service
        Optional<Client> response = userService.getClientLogged(new MockHttpServletRequest());

        // Validate response
        assertThat(response.get().getEmail()).isEqualTo(client.getEmail());

        // Validate API usage
        verify(restTemplate, times(1)).exchange(contains(apiBaseUrl + "/client/"), eq(HttpMethod.GET), any(), eq(Optional.class));
    }



}
