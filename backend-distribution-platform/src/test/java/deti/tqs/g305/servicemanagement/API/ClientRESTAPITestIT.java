package deti.tqs.g305.servicemanagement.API;

import deti.tqs.g305.servicemanagement.configuration.JwtTokenUtil;
import deti.tqs.g305.servicemanagement.model.*;
import deti.tqs.g305.servicemanagement.repository.*;
import deti.tqs.g305.servicemanagement.service.UserService;
import org.junit.jupiter.api.AfterEach;
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
import org.testcontainers.shaded.com.google.common.util.concurrent.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientRESTAPITestIT {

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
    void whenPostContract_thenReturnContract() {
        // Create client and save to db
        Client c = new Client("client@ua.pt", bcryptEncoder.encode("abc"), "First Last Name", "Client's address street", LocalDate.now());
        userRepository.saveAndFlush(c);

        ServiceType st = new ServiceType("pool work", true);
        serviceTypeRepository.saveAndFlush(st);
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);
        BusinessService bs = new BusinessService(25.0, st, b);
        businessServiceRepository.saveAndFlush(bs);
        Provider p = new Provider("provider@ua.pt", "First Last Name", bcryptEncoder.encode("abc"), new HashMap<>(), new ArrayList<>(), new ArrayList<>(), "123456789", LocalDate.now());
        userRepository.saveAndFlush(p);
        ProviderService ps = new ProviderService("Working on the Pool", p, st);
        providerServiceRepository.saveAndFlush(ps);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(c.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request
        ServiceContract sc = new ServiceContract(bs, ps, ServiceStatus.WAITING, c, 0);
        // Make request to API
        ResponseEntity<ServiceContract> response = restTemplate.postForEntity(
                "/api/clients/contracts",
                new HttpEntity<>(sc, headers),
                ServiceContract.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getClient()).isEqualTo(c);
    }

    @Test
    void whenGetContracts_thenReturnContracts() {
        // Create client and save to db
        Client c = new Client("client@ua.pt", bcryptEncoder.encode("abc"), "First Last Name", "Client's address street", LocalDate.now());
        userRepository.saveAndFlush(c);

        ServiceType st = new ServiceType("pool work", true);
        serviceTypeRepository.saveAndFlush(st);
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);
        BusinessService bs = new BusinessService(25.0, st, b);
        businessServiceRepository.saveAndFlush(bs);
        Provider p = new Provider("provider@ua.pt", "First Last Name", bcryptEncoder.encode("abc"), new HashMap<>(), new ArrayList<>(), new ArrayList<>(), "123456789", LocalDate.now());
        userRepository.saveAndFlush(p);
        ProviderService ps = new ProviderService("Working on the Pool", p, st);
        providerServiceRepository.saveAndFlush(ps);
        ServiceContract sc = new ServiceContract(bs, ps, ServiceStatus.FINNISHED, c, 0);
        serviceContractRepository.saveAndFlush(sc);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(c.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request to API
        ResponseEntity<Map> response = restTemplate.exchange(
                "/api/clients/contracts",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("totalItems")).isEqualTo(1);
    }

    @Test
    void whenGetValidContractID_thenReturnValidContractID() {
        // Create client and save to db
        Client c = new Client("client@ua.pt", bcryptEncoder.encode("abc"), "First Last Name", "Client's address street", LocalDate.now());
        userRepository.saveAndFlush(c);

        ServiceType st = new ServiceType("pool work", true);
        serviceTypeRepository.saveAndFlush(st);
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);
        BusinessService bs = new BusinessService(25.0, st, b);
        businessServiceRepository.saveAndFlush(bs);
        Provider p = new Provider("provider@ua.pt", "First Last Name", bcryptEncoder.encode("abc"), new HashMap<>(), new ArrayList<>(), new ArrayList<>(), "123456789", LocalDate.now());
        userRepository.saveAndFlush(p);
        ProviderService ps = new ProviderService("Working on the Pool", p, st);
        providerServiceRepository.saveAndFlush(ps);
        ServiceContract sc = new ServiceContract(bs, ps, ServiceStatus.FINNISHED, c, 0);
        serviceContractRepository.saveAndFlush(sc);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(c.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request to API
        ResponseEntity<ServiceContract> response = restTemplate.exchange(
                "/api/clients/contracts/"+sc.getId(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                ServiceContract.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getClient()).isEqualTo(c);
    }

    @Test
    void whenGetInvalidContractID_thenReturnNotFound() {
        // Create client and save to db
        Client c = new Client("client@ua.pt", bcryptEncoder.encode("abc"), "First Last Name", "Client's address street", LocalDate.now());
        userRepository.saveAndFlush(c);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(c.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request and validate it
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/clients/contracts/9999",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    void whenUpdateValidContract_thenReturnContract() {
        // Create client and save to db
        Client c = new Client("client@ua.pt", bcryptEncoder.encode("abc"), "First Last Name", "Client's address street", LocalDate.now());
        userRepository.saveAndFlush(c);

        ServiceType st = new ServiceType("pool work", true);
        serviceTypeRepository.saveAndFlush(st);
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);
        BusinessService bs = new BusinessService(25.0, st, b);
        businessServiceRepository.saveAndFlush(bs);
        Provider p = new Provider("provider@ua.pt", "First Last Name", bcryptEncoder.encode("abc"), new HashMap<>(), new ArrayList<>(), new ArrayList<>(), "123456789", LocalDate.now());
        userRepository.saveAndFlush(p);
        ProviderService ps = new ProviderService("Working on the Pool", p, st);
        providerServiceRepository.saveAndFlush(ps);
        ServiceContract sc = new ServiceContract(bs, ps, ServiceStatus.FINNISHED, c, 0);
        serviceContractRepository.saveAndFlush(sc);
        ServiceContract updatedsc = new ServiceContract(bs, ps, ServiceStatus.FINNISHED, c, 5);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(c.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request and validate it
        ResponseEntity<ServiceContract> response = restTemplate.exchange(
                "/api/clients/contracts/"+sc.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(updatedsc, headers),
                ServiceContract.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getReview()).isEqualTo(updatedsc.getReview());

    }

    @Test
    void whenUpdateInvalidContract_thenReturnBadRequest() {
        // Create client and save to db
        Client c = new Client("client@ua.pt", bcryptEncoder.encode("abc"), "First Last Name", "Client's address street", LocalDate.now());
        userRepository.saveAndFlush(c);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(c.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request and validate it
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/clients/contracts/9999",
                HttpMethod.PUT,
                new HttpEntity<>(headers),
                String.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenGetMatches_thenReturnMatches() {
        // Create client and save to db
        Client c = new Client("client@ua.pt", bcryptEncoder.encode("abc"), "First Last Name", "Client's address street", LocalDate.now());
        userRepository.saveAndFlush(c);

        ServiceType st = new ServiceType("pool work", true);
        serviceTypeRepository.saveAndFlush(st);
        Business b = new Business("business@ua.pt", "MyBusiness", bcryptEncoder.encode("abc"), "Sample Key", "FirstNameBus", "Random Address", "NIF1234");
        userRepository.saveAndFlush(b);
        BusinessService bs = new BusinessService(25.0, st, b);
        businessServiceRepository.saveAndFlush(bs);
        Provider p = new Provider("provider@ua.pt", "First Last Name", bcryptEncoder.encode("abc"), new HashMap<>(), new ArrayList<>(), new ArrayList<>(), "123456789", LocalDate.now());
        userRepository.saveAndFlush(p);
        ProviderService ps = new ProviderService("Working on the Pool", p, st);
        providerServiceRepository.saveAndFlush(ps);

        // Create token for user and use it to build request header
        String token = tokenUtil.generateToken(userService.loadUserByUsername(c.getEmail()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        // Make request and validate it
        ResponseEntity<List> response = restTemplate.exchange(
                "/api/clients/matches/"+ps.getId(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


}
