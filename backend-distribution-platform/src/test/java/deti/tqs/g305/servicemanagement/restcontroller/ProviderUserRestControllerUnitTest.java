package deti.tqs.g305.servicemanagement.restcontroller;

import deti.tqs.g305.servicemanagement.JsonUtil;
import deti.tqs.g305.servicemanagement.configuration.JwtAuthenticationEntryPoint;
import deti.tqs.g305.servicemanagement.configuration.JwtTokenUtil;
import deti.tqs.g305.servicemanagement.configuration.WebSecurityConfig;
import deti.tqs.g305.servicemanagement.configuration.BusinessMatcher;
import deti.tqs.g305.servicemanagement.model.*;
import deti.tqs.g305.servicemanagement.repository.BusinessRepository;
import deti.tqs.g305.servicemanagement.repository.ClientRepository;
import deti.tqs.g305.servicemanagement.repository.ProviderRepository;
import deti.tqs.g305.servicemanagement.repository.UserRepository;
import deti.tqs.g305.servicemanagement.service.ProviderUserService;
import deti.tqs.g305.servicemanagement.service.ProviderUserServiceImpl;
import deti.tqs.g305.servicemanagement.service.ServiceService;
import deti.tqs.g305.servicemanagement.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(ProviderRestController.class)
class ProviderUserRestControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProviderRepository providerRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProviderUserService providerUserService;

    @MockBean
    private JwtTokenUtil jwtToken;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuth;

    @MockBean
    private BusinessMatcher bm;

    @MockBean
    private UserService userService;

    @MockBean
    private ServiceService serviceService;

/*
    List<Provider> listProvider;
    List<District> districts = new ArrayList<>(List.of(new District("Porto"), new District("Aveiro"), new District( "Santarem")));
    List<City> cities = new ArrayList<>(List.of(new City( "Matosinhos", districts.get(0)), new City( "Ovar", districts.get(1)), new City( "Vila Nova da Barquinha", districts.get(2))));
    Map<Integer, String> working_hours = new HashMap<Integer, String>() {{
        put(1, "09:00-18:00"); put(2, "09:00-18:00"); put(3, "09:00-18:00"); put(4, "09:00-18:00"); put(5, "09:00-18:00"); put(6, ""); put(7, "");
    }};
    LocalDate date = LocalDate.of(2000, 1, 8);
    Provider pr1;

    @BeforeEach
    public void setUp(){

        pr1 = new Provider("prov1_xpto@ymail.com", "Pro Xpto Silva","pass1",null, null, null, "200000001", date);
        Provider pr2 = new Provider("prov2_xpto@ymail.com", "Pro Xpto Matos","pass1",working_hours, cities, districts, "200000002", date);
        Provider pr3 = new Provider("prov3_xpto@ymail.com", "Pro Xpto Bastos","pass1",working_hours, cities, districts, "200000003", date);

        listProvider = new ArrayList<Provider>();
        listProvider.add(pr1);
        listProvider.add(pr2);
        listProvider.add(pr3);
    }

    @Test
    public void should_CreateProvider_When_ValidRequest()  throws IOException, Exception {

        mvc.perform(post("/api/provider").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(pr1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category", is(null)))
                .andExpect(jsonPath("$.working_hours", is(working_hours)))
                .andExpect(jsonPath("$.location_city", is(cities)))
                .andExpect(jsonPath("$.location_district", is(districts)))
                .andExpect(jsonPath("$.nif", is("200000001")))
                .andExpect(jsonPath("$.birthdate", is(date)));


        when( userService.getUserByEmail(pr1.getEmail())).thenReturn(Optional.of(pr1));

        verify(providerUserService, times(1)).findByEmail(ArgumentMatchers.any());
    }

    @Test
    void whenLookForValidByEmail_thenReturnProvider() throws Exception {
        Provider pr = new Provider("prov_xpto@ymail.com", "Pro Xpto Ronaldo","xpto@123",working_hours, cities, districts, "200000009", date);
        when(providerUserService.findByEmail(pr.getEmail())).thenReturn(Optional.of(pr));
        mvc.perform(get("/api/provider/prov_xpto@ymail.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nif", is("200000009")));
        verify(providerUserService, times(1)).findByEmail(ArgumentMatchers.any());
        //Provider pr = new Provider("prov_xpto@ymail.com", "Pro Xpto Ronaldo","xpto@123",working_hours, cities, districts, "200000009", date);
        //System.out.println(mvc);

        when( providerUserService.findByEmail(pr.getEmail())).thenReturn(Optional.of(pr));
        mvc.perform(get("/api/provider/"+ pr.getEmail()).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(pr)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category", is(null)))
                .andExpect(jsonPath("$.working_hours", is(working_hours)))
                .andExpect(jsonPath("$.location_city", is(cities)))
                .andExpect(jsonPath("$.location_district", is(districts)))
                .andExpect(jsonPath("$.nif", is("200000001")))
                .andExpect(jsonPath("$.birthdate", is(date)));
        System.out.println(mvc);
        System.out.println(providerUserService.findByEmail(pr.getEmail()));
        when( providerUserService.findByEmail(pr.getEmail())).thenReturn(Optional.of(pr));

        verify(providerUserService, times(1)).findByEmail(ArgumentMatchers.any());


    }
 */
    @Test
    void createProviderUser() {
    }

    @Test
    void updateProviderUser() {
    }

    @Test
    void deleteProviderUser() {
    }
}