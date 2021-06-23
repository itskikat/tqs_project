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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GeneralServiceUnitTest {

    @Mock(lenient = true)
    private RestTemplate restTemplate;

    @Mock(lenient = true)
    private RequestsHelper requestsHelper;

    @InjectMocks
    private GeneralService generalService;

    // Register
    @Test
    void whenGetDistricts_thenReturnList() throws Exception {
        // Mock API
        District d1 = new District(1L, "Santarém");
        District d2 = new District(2L, "Aveiro");
        when(restTemplate.exchange(contains("/districts"), any(), any(), eq(List.class))).thenReturn(new ResponseEntity<List>(Arrays.asList(d1, d2), HttpStatus.OK));

        // Call service
        List<District> response = generalService.getDistricts(new MockHttpServletRequest());

        // Validate response
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0).getName()).isEqualTo(d1.getName());
        assertThat(response.get(1).getId()).isEqualTo(d2.getId());

        // Validate that API was called with right parameters
        verify(restTemplate, times(1)).exchange(contains("/districts"), eq(HttpMethod.GET), any(), eq(List.class));
    }

    @Test
    void whenGetDistrictCities_thenReturnList() throws Exception {
        // Mock API
        District d1 = new District(1L, "Santarém");
        City c1 = new City(1L, "Vila Nova da Barquinha", d1);
        City c2 = new City(1L, "Entroncamento", d1);
        when(restTemplate.exchange(contains("/districts/"), any(), any(), eq(List.class))).thenReturn(new ResponseEntity<List>(Arrays.asList(c1, c2), HttpStatus.OK));

        // Call service
        List<City> response = generalService.getDistrictCities(d1.getId(), new MockHttpServletRequest());

        // Validate response
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0).getName()).isEqualTo(c1.getName());
        assertThat(response.get(1).getId()).isEqualTo(c2.getId());

        // Validate that API was called with right parameters
        verify(restTemplate, times(1)).exchange(contains("/districts/1"), eq(HttpMethod.GET), any(), eq(List.class));
    }
    
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
    void whenGetProviderService_thenReturnService() {
        // Mock service
        Provider p = new Provider("provider@ua.pt", "Provider Name", "abc", null, Arrays.asList(), Arrays.asList(), "123456789", null);
        ServiceType s = new ServiceType("Type1", true);
        ProviderService ps1 = new ProviderService();
        ps1.setId(1L);
        ps1.setService(s);
        ps1.setProvider(p);
        when(restTemplate.exchange(contains("/clients/services/"), any(), any(), eq(Optional.class))).thenReturn(new ResponseEntity<Optional>(Optional.of(ps1), HttpStatus.OK));

        // Call service
        Optional<ProviderService> response = generalService.getService(ps1.getId(), new MockHttpServletRequest());

        // Validate response
        assertThat(response.isPresent()).isTrue();
        assertThat(response.get().getProvider().getEmail()).isEqualTo(ps1.getProvider().getEmail());

        // Validate that API was called with right parameters
        verify(restTemplate, times(1)).exchange(contains("/clients/services"), eq(HttpMethod.GET), any(), eq(Optional.class));
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
