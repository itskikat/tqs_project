package deti.tqs.g305.handymanservicesapp.service;

import deti.tqs.g305.handymanservicesapp.configuration.RequestsHelper;
import deti.tqs.g305.handymanservicesapp.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GeneralServiceUnitTest {

    @Mock(lenient = true)
    private RestTemplate restTemplate;

    @Mock(lenient = true)
    private RequestsHelper requestsHelper;

    @InjectMocks
    private GeneralService generalService;

    // Dynamic matching
    @Test
    void whenGetServices_thenReturnServicesList() {
        // Mock API
        BusinessService bs1 = new BusinessService();
        bs1.setService(new ServiceType("Type1", true));
        BusinessService bs2 = new BusinessService();
        bs2.setService(new ServiceType("Type2", true));
        when(restTemplate.exchange(contains("/businesses/allservices"), any(), any(), eq(List.class))).thenReturn(new ResponseEntity<List>(Arrays.asList(bs1, bs2), HttpStatus.OK));

        // Call service
        List<BusinessService> response = generalService.services(new MockHttpServletRequest());

        // Validate response
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0).getService().getName()).isEqualTo("Type1");
        assertThat(response.get(1).getService().getName()).isEqualTo("Type2");

        // Validate that API was called with right parameters
        verify(restTemplate, times(1)).exchange(contains("/businesses/allservices"), eq(HttpMethod.GET), any(), eq(List.class));
    }

    @Test
    void whenMatch_thenReturnList() {
        // Mock API
        Provider p = new Provider("provider@ua.pt", "Provider Name", "abc", null, Arrays.asList(), Arrays.asList(), "123456789", LocalDate.now());
        ServiceType s = new ServiceType("Type1", true);
        ProviderService ps1 = new ProviderService();
        ps1.setService(s);
        ps1.setProvider(p);
        ProviderService ps2 = new ProviderService();
        ps2.setService(s);
        ps2.setProvider(p);
        when(restTemplate.exchange(contains("/clients/matches"), any(), any(), eq(List.class))).thenReturn(new ResponseEntity<List>(Arrays.asList(ps1, ps2), HttpStatus.OK));

        // Call service
        List<ProviderService> response = generalService.match(2L, new MockHttpServletRequest());

        // Validate response
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0).getService().getName()).isEqualTo(s.getName());
        assertThat(response.get(0).getProvider().getEmail()).isEqualTo(p.getEmail());
        assertThat(response.get(1).getService().getName()).isEqualTo(s.getName());
        assertThat(response.get(1).getProvider().getFull_name()).isEqualTo(p.getFull_name());

        // Validate that API was called with right parameters
        verify(restTemplate, times(1)).exchange(contains("/clients/matches/2"), eq(HttpMethod.GET), any(), eq(List.class));
    }

    @Test
    void whenPostContract_thenReturnContract() {
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
        when(restTemplate.exchange(contains("/clients/contracts"), any(), any(), eq(ServiceContract.class))).thenReturn(new ResponseEntity<ServiceContract>(sc, HttpStatus.OK));

        // Call service
        ServiceContract response = generalService.createContract(any(), new MockHttpServletRequest());

        // Validate response
        assertThat(response.getBusinessService().getBusiness().getName()).isEqualTo(b.getName());
        assertThat(response.getProviderService().getProvider().getNif()).isEqualTo(p.getNif());
        assertThat(response.getClient().getEmail()).isEqualTo(c.getEmail());
        assertThat(response.getStatus()).isEqualTo(sc.getStatus());

        // Validate that API was called with right parameters
        verify(restTemplate, times(1)).exchange(contains("/clients/contracts"), eq(HttpMethod.POST), any(), eq(ServiceContract.class));
    }
}
