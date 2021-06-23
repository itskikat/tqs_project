package deti.tqs.g305.handymanservicesapp.service;

import deti.tqs.g305.handymanservicesapp.configuration.RequestsHelper;
import deti.tqs.g305.handymanservicesapp.model.City;
import deti.tqs.g305.handymanservicesapp.model.District;
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
}
