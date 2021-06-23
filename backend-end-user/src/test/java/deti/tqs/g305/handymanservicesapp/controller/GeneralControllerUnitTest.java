package deti.tqs.g305.handymanservicesapp.controller;

import deti.tqs.g305.handymanservicesapp.JsonUtil;
import deti.tqs.g305.handymanservicesapp.configuration.ClientBearerMatcher;
import deti.tqs.g305.handymanservicesapp.exceptions.UnauthorizedException;
import deti.tqs.g305.handymanservicesapp.model.*;
import deti.tqs.g305.handymanservicesapp.service.GeneralService;
import deti.tqs.g305.handymanservicesapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    // Past services
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

    @Test
    void whenGetContract_thenReturnContract() throws Exception {
        // Mock service
        ServiceContract s = new ServiceContract();
        s.setId(3);
        when(generalService.getContract(any(), any())).thenReturn(s);

        // Call controller and validate response
        mvc.perform(get("/api/contracts/" + 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)));

        // Validate that service was called with right parameters
        verify(generalService, times(1)).getContract(eq(3L), any());
    }

    @Test
    void whenUpdateContract_thenReturnUpdated() throws Exception {
        // Mock service
        ServiceContract s = new ServiceContract();
        s.setId(3L);
        s.setReview(5);
        when(generalService.updateContract(any(), any(), any())).thenReturn(s);

        // Call controller and validate response
        mvc.perform(put("/api/contracts/" + 3).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(s)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(3)))
            .andExpect(jsonPath("$.review", is(5)));

        // Validate that service was called with right parameters
        verify(generalService, times(1)).updateContract(eq(3L), any(), any());
    }

    // Register
    @Test
    void whenGetDistricts_thenReturnList() throws Exception {
        // Mock service
        District d1 = new District(1L, "Santarém");
        District d2 = new District(2L, "Aveiro");
        when(generalService.getDistricts(any())).thenReturn(Arrays.asList(d1, d2));

        // Call controller and validate response
        mvc.perform(get("/api/districts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(d1.getName())))
                .andExpect(jsonPath("$[1].name", is(d2.getName())));

        // Validate that service was called with right parameters
        verify(generalService, times(1)).getDistricts(any());
    }

    @Test
    void whenGetDistrictCities_thenReturnList() throws Exception {
        // Mock service
        District d1 = new District(1L, "Santarém");
        City c1 = new City(1L, "Vila Nova da Barquinha", d1);
        City c2 = new City(1L, "Entroncamento", d1);
        when(generalService.getDistrictCities(anyLong(), any())).thenReturn(Arrays.asList(c1, c2));

        // Call controller and validate response
        mvc.perform(get("/api/districts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(c1.getName())))
                .andExpect(jsonPath("$[0].district.name", is(c1.getDistrict().getName())))
                .andExpect(jsonPath("$[1].name", is(c2.getName())))
                .andExpect(jsonPath("$[1].district.name", is(c1.getDistrict().getName())));

        // Validate that service was called with right parameters
        verify(generalService, times(1)).getDistrictCities(eq(d1.getId()), any());
    }

}
