package deti.tqs.g305.handymanservicesapp.controller;

import deti.tqs.g305.handymanservicesapp.JsonUtil;
import deti.tqs.g305.handymanservicesapp.configuration.ClientBearerMatcher;
import deti.tqs.g305.handymanservicesapp.exceptions.UnauthorizedException;
import deti.tqs.g305.handymanservicesapp.model.*;
import deti.tqs.g305.handymanservicesapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ClientBearerMatcher clientBearerMatcher;

    @BeforeEach
    void setUp() {
        when(clientBearerMatcher.matches(any())).thenReturn(true);
    }

    @Test
    void whenPostValidClientLogin_thenReturnToken() throws Exception {
        // Mock service
        JwtRequest request = new JwtRequest("username", "password");
        JwtResponse response = new JwtResponse("token", new UserAuthority("CLIENT"), "Many", "many@ua.pt");
        when(userService.logIn(any())).thenReturn(response);

        // Call controller and validate response
        mvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(response.getName())))
            .andExpect(jsonPath("$.email", is(response.getEmail())))
            .andExpect(jsonPath("$.token", is(response.getToken())))
            .andExpect(jsonPath("$.type.authority", is(response.getType().getAuthority())));
        
        // Validate that service was called
        verify(userService, times(1)).logIn(any());
    }

    @Test
    void whenPostInvalidClientLogin_thenReturnException() throws Exception {
        // Mock service
        JwtRequest request = new JwtRequest("username", "password");
        when(userService.logIn(any())).thenThrow(UnauthorizedException.class);

        // Call controller and validate response
        mvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(request)))
            .andExpect(status().isUnauthorized());

        // Validate that service was called
        verify(userService, times(1)).logIn(any());
    }

    @Test
    void whenLoggedGetLogged_thenReturnUserResponse() throws Exception {
        // Mock service
        when(userService.getUserLogged(any())).thenReturn(new UserResponse(new UserAuthority("CLIENT"), "Person Name", "email@ua.pt"));

        // Call controller and validate response
        mvc.perform(get("/api/users/logged"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", is("email@ua.pt")));

        // Validate that service was called
        verify(userService, times(1)).getUserLogged(any());
    }

    @Test
    void whenExpiredGetLogged_thenReturnError() throws Exception {
        // Mock service
        when(userService.getUserLogged(any())).thenThrow(new UnauthorizedException("Session expired!"));

        // Call controller and validate response
        mvc.perform(get("/api/users/logged"))
                .andExpect(status().isUnauthorized());

        // Validate that service was called
        verify(userService, times(1)).getUserLogged(any());
    }

    @Test
    void whenGetClient_thenReturnClient() throws Exception {
        // Mock service
        Client client = new Client("client@ua.pt", "abc", "First Last Name", "Client's address street", null, LocalDate.now());
        when(userService.getClientLogged(any())).thenReturn(Optional.of(client));

        // Call controller and validate response
        mvc.perform(get("/api/users/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", is(client.getEmail())));

        // Validate that service was called
        verify(userService, times(1)).getClientLogged(any());
    }

}
