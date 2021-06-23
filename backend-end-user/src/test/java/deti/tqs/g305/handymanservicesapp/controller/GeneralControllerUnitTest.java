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

import java.time.LocalDate;
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
    void whenGetContractsList_thenReturnContracts() throws Exception {
        // Mock service
        Map<String, Object> contracts = new HashMap<>();
        contracts.put("currentPage", 1);
        contracts.put("data", Arrays.asList());
        when(generalService.getContracts(anyInt(), any(), any(), any(), anyInt(), any())).thenReturn(contracts);

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

    // Dynamic matching
    @Test
    void whenGetServices_thenReturnServicesList() throws Exception {
        // Mock service
        BusinessService bs1 = new BusinessService();
        bs1.setService(new ServiceType("Type1", true));
        BusinessService bs2 = new BusinessService();
        bs2.setService(new ServiceType("Type2", true));
        when(generalService.services(any())).thenReturn(Arrays.asList(bs1, bs2));

        // Call controller and validate response
        mvc.perform(get("/api/services/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].service.name", is(bs1.getService().getName())))
                .andExpect(jsonPath("$[1].service.name", is(bs2.getService().getName())));

        // Validate that service was called with right parameters
        verify(generalService, times(1)).services(any());
    }

    @Test
    void whenMatch_thenReturnList() throws Exception {
        // Mock service
        Provider p = new Provider("provider@ua.pt", "Provider Name", "abc", null, Arrays.asList(), Arrays.asList(), "123456789", LocalDate.now());
        ServiceType s = new ServiceType("Type1", true);
        ProviderService ps1 = new ProviderService();
        ps1.setService(s);
        ps1.setProvider(p);
        ProviderService ps2 = new ProviderService();
        ps2.setService(s);
        ps2.setProvider(p);
        when(generalService.match(any(), any())).thenReturn(Arrays.asList(ps1, ps2));

        // Call controller and validate response
        mvc.perform(get("/api/matches/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].service.name", is(ps1.getService().getName())))
                .andExpect(jsonPath("$[0].provider.email", is(p.getEmail())))
                .andExpect(jsonPath("$[1].service.name", is(ps2.getService().getName())))
                .andExpect(jsonPath("$[1].provider.full_name", is(p.getFull_name())));

        // Validate that service was called with right parameters
        verify(generalService, times(1)).match(eq(1L), any());
    }

    @Test
    void whenPostContract_thenReturnContract() throws Exception {
        // Mock service
        Provider p = new Provider("provider@ua.pt", "Provider Name", "abc", null, Arrays.asList(), Arrays.asList(), "123456789", null);
        Business b = new Business("business@ua.pt", "Business name", "abc", "token", "BUSINESS NAME", "Aveiro, Portugal", "125836589");
        Client c = new Client("client@ua.pt", "Client name", "abc", "Aveiro, Portugal", null);
        ServiceType s = new ServiceType("Type1", true);
        ProviderService ps1 = new ProviderService();
        ps1.setService(s);
        ps1.setProvider(p);
        BusinessService bs1 = new BusinessService();
        bs1.setService(s);
        bs1.setBusiness(b);
        ServiceContract sc = new ServiceContract(bs1, ps1, ServiceStatus.WAITING, c, 0);
        when(generalService.createContract(any(), any())).thenReturn(sc);

        // Call controller and validate response
        mvc.perform(post("/api/contracts").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(sc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.businessService.business.nif", is(bs1.getBusiness().getNif())))
                .andExpect(jsonPath("$.providerService.provider.nif", is(ps1.getProvider().getNif())))
                .andExpect(jsonPath("$.client.email", is(c.getEmail())))
                .andExpect(jsonPath("$.status", is(ServiceStatus.WAITING.name())));

        // Validate that service was called with right parameters
        verify(generalService, times(1)).createContract(eq(sc), any());
    }



}
