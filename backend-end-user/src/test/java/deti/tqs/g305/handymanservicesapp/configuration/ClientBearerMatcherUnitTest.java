package deti.tqs.g305.handymanservicesapp.configuration;

import deti.tqs.g305.handymanservicesapp.exceptions.UnauthorizedException;
import deti.tqs.g305.handymanservicesapp.model.UserResponse;
import deti.tqs.g305.handymanservicesapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientBearerMatcherUnitTest {

    @Value("${enduser.apiurl}")
    String apiBaseUrl;

    @Mock(lenient = true)
    private UserService userService;

    @InjectMocks
    private ClientBearerMatcher clientBearerMatcher;

    @Test
    void whenValidRequest_thenReturnTrue() throws UnauthorizedException {
        // Mock service
        when(userService.getUserLogged(any())).thenReturn(new UserResponse());

        // Call matcher
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "ABC");
        boolean response = clientBearerMatcher.matches(request);

        // Validate response
        assertThat(response).isTrue();
    }

    @Test
    void whenInvalidRequestToken_thenReturnFalse() throws UnauthorizedException {
        // Mock service
        when(userService.getUserLogged(any())).thenThrow(new UnauthorizedException());

        // Call matcher
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "ABC");
        boolean response = clientBearerMatcher.matches(request);

        // Validate response
        assertThat(response).isFalse();
    }

    @Test
    void whenMissingAuthorizationHeader_thenReturnFalse() throws UnauthorizedException {
        // Call matcher
        boolean response = clientBearerMatcher.matches(new MockHttpServletRequest());

        // Validate response
        assertThat(response).isFalse();
    }

}
