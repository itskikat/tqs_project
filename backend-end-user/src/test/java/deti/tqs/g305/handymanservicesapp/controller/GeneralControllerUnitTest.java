package deti.tqs.g305.handymanservicesapp.controller;

import deti.tqs.g305.handymanservicesapp.JsonUtil;
import deti.tqs.g305.handymanservicesapp.configuration.ClientBearerMatcher;
import deti.tqs.g305.handymanservicesapp.exceptions.UnauthorizedException;
import deti.tqs.g305.handymanservicesapp.model.JwtRequest;
import deti.tqs.g305.handymanservicesapp.model.JwtResponse;
import deti.tqs.g305.handymanservicesapp.model.UserAuthority;
import deti.tqs.g305.handymanservicesapp.model.UserResponse;
import deti.tqs.g305.handymanservicesapp.service.GeneralService;
import deti.tqs.g305.handymanservicesapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GeneralController.class)
public class GeneralControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GeneralService generalService;

    @MockBean
    private ClientBearerMatcher clientBearerMatcher;

    @BeforeEach
    void setUp() {
        when(clientBearerMatcher.matches(any())).thenReturn(true);
    }

    @Test
    void whenGetContracts_thenReturnContracts() throws Exception {
        // Mock service
        Map<String, Object> contracts = new HashMap<>();
        contracts.put("currentPage", 1);
        contracts.put("data", Arrays.asList());
        when(generalService.getContracts(eq(3), any(), any(), any(), eq(99), any())).thenReturn(contracts);

        // Call controller and validate response
        mvc.perform(get("/api/contracts?page=3&status=FINISHED&sort=price&order=DESC&size=99"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.currentPage", is(1)))
            .andExpect(jsonPath("$.data", hasSize(0)));

        // Validate that service was called with right parameters
        verify(generalService, times(1)).getContracts(eq(3), eq("FINISHED"), eq("price"), eq("DESC"), eq(99), any());
    }

}
