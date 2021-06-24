package deti.tqs.g305.servicemanagement.API;

import deti.tqs.g305.servicemanagement.configuration.JwtTokenUtil;
import deti.tqs.g305.servicemanagement.model.*;
import deti.tqs.g305.servicemanagement.repository.*;
import deti.tqs.g305.servicemanagement.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BusinessRESTAPITestIT {

    private static final Logger log = LoggerFactory.getLogger(UserRESTAPITestIT.class);

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer().withUsername("demo").withPassword("password").withDatabaseName("demo");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private BusinessServiceRepository businessServiceRepository;

    @Autowired
    private ProviderServiceRepository providerServiceRepository;

    @Autowired
    private ServiceContractRepository serviceContractRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private UserService userService;

    @AfterEach
    void cleanUp() {
        serviceContractRepository.deleteAll();
        businessServiceRepository.deleteAll();
        providerServiceRepository.deleteAll();
        serviceTypeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void whenGetServices_thenReturnServices() {

        // Create business and save to db
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);
        ServiceType st = new ServiceType("pool work", true);
        serviceTypeRepository.saveAndFlush(st);
        BusinessService bs = new BusinessService(25.0, st, b);
        businessServiceRepository.saveAndFlush(bs);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(b.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request and validate it
        ResponseEntity<Map> response = restTemplate.exchange(
                "/api/businesses/services",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("totalItems")).isEqualTo(1);

    }

    @Test
    void whenGetContracts_thenReturnContracts() {

        // Create business and save to db
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);
        Provider p = new Provider("provider@ua.pt", "First Last Name", bcryptEncoder.encode("abc"), new HashMap<>(), new ArrayList<>(), new ArrayList<>(), "123456789", LocalDate.now());
        userRepository.saveAndFlush(p);
        Client c = new Client("client@ua.pt", bcryptEncoder.encode("abc"), "First Last Name", "Client's address street", LocalDate.now());
        userRepository.saveAndFlush(c);
        ServiceType st = new ServiceType("pool work", true);
        serviceTypeRepository.saveAndFlush(st);
        BusinessService bs = new BusinessService(25.0, st, b);
        businessServiceRepository.saveAndFlush(bs);
        ProviderService ps = new ProviderService("Working on the Pool", p, st);
        providerServiceRepository.saveAndFlush(ps);
        ServiceContract sc = new ServiceContract(bs, ps, ServiceStatus.ACCEPTED, c, 5);
        serviceContractRepository.saveAndFlush(sc);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(b.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request and validate it
        ResponseEntity<Map> response = restTemplate.exchange(
                "/api/businesses/contracts",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("totalItems")).isEqualTo(1);
    }

    @Test
    void whenGetStatistics_thenReturnStatistics() {

        // Create business and save to db
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);
        Provider p = new Provider("provider@ua.pt", "First Last Name", bcryptEncoder.encode("abc"), new HashMap<>(), new ArrayList<>(), new ArrayList<>(), "123456789", LocalDate.now());
        userRepository.saveAndFlush(p);
        Client c = new Client("client@ua.pt", bcryptEncoder.encode("abc"), "First Last Name", "Client's address street", LocalDate.now());
        userRepository.saveAndFlush(c);
        ServiceType st = new ServiceType("pool work", true);
        serviceTypeRepository.saveAndFlush(st);
        BusinessService bs = new BusinessService(25.0, st, b);
        businessServiceRepository.saveAndFlush(bs);
        ProviderService ps = new ProviderService("Working on the Pool", p, st);
        providerServiceRepository.saveAndFlush(ps);
        ServiceContract sc = new ServiceContract(bs, ps, ServiceStatus.FINNISHED, c, 5);
        serviceContractRepository.saveAndFlush(sc);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(b.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request and validate it
        ResponseEntity<Map> response = restTemplate.exchange(
                "/api/businesses/statistics",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("TOTAL_CONTRACTS")).isEqualTo(1);
        assertThat(response.getBody().get("PROFIT")).isEqualTo(25.0);

    }

    @Test
    void whenGetValidServiceID_thenReturnValidServiceID() {
        // Create business and save to db
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);
        ServiceType st = new ServiceType("pool work", true);
        serviceTypeRepository.saveAndFlush(st);
        BusinessService bs = new BusinessService(25.0, st, b);
        businessServiceRepository.saveAndFlush(bs);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(b.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request and validate it
        ResponseEntity<BusinessService> response = restTemplate.exchange(
                "/api/businesses/services/"+bs.getId(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                BusinessService.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getBusiness()).isEqualTo(b);
    }

    @Test
    void whenGetInvalidServiceID_thenReturnBadRequest() {
        // Create business and save to db
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(b.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request and validate it
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/businesses/services/999",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenPostService_thenReturnService() {
        // Create business and save to db
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);
        ServiceType st = new ServiceType("pool work", true);
        serviceTypeRepository.saveAndFlush(st);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(b.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request
        BusinessService bs = new BusinessService(25.0, st, b);
        // Make request to API
        ResponseEntity<BusinessService> response = restTemplate.postForEntity(
                "/api/businesses/services",
                new HttpEntity<>(bs, headers),
                BusinessService.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getPrice()).isEqualTo(bs.getPrice());
    }

    @Test
    void whenUpdateValidService_thenReturnService() {
        // Create business and save to db
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);
        ServiceType st = new ServiceType("pool work", true);
        serviceTypeRepository.saveAndFlush(st);
        BusinessService bs = new BusinessService(25.0, st, b);
        businessServiceRepository.saveAndFlush(bs);
        BusinessService updatedbs = new BusinessService(55.0, st, b);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(b.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request and validate it
        ResponseEntity<BusinessService> response = restTemplate.exchange(
                "/api/businesses/services/"+bs.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(updatedbs, headers),
                BusinessService.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getPrice()).isEqualTo(updatedbs.getPrice());
    }

    @Test
    void whenUpdateInvalidService_thenReturnBadRequest() {
        // Create business and save to db
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(b.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request and validate it
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/businesses/services/9999",
                HttpMethod.PUT,
                new HttpEntity<>(headers),
                String.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenDeleteValidService_thenDeleteService() {
        // Create business and save to db
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);
        ServiceType st = new ServiceType("pool work", true);
        serviceTypeRepository.saveAndFlush(st);
        BusinessService bs = new BusinessService(25.0, st, b);
        businessServiceRepository.saveAndFlush(bs);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(b.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request and validate it
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/businesses/services/delete/"+bs.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                String.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void whenDeleteInvalidService_thenReturnBadRequest() {
        // Create business and save to db
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(b.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request and validate it
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/businesses/services/delete/9999",
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                String.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
