package deti.tqs.g305.servicemanagement.API;

import deti.tqs.g305.servicemanagement.model.*;
import deti.tqs.g305.servicemanagement.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRESTAPITest {

    private static final Logger log = LoggerFactory.getLogger(UserRESTAPITest.class);

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
    private PasswordEncoder bcryptEncoder;

    @Test
    void whenPostLoginValidUser_Client_thenReturnClient() {
        // Create user and save to db
        Client c = new Client("client@ua.pt", bcryptEncoder.encode("abc"), "First Last Name", "Client's address street", LocalDate.now());
        userRepository.saveAndFlush(c);

        // Make request
        JwtRequest request = new JwtRequest(c.getEmail(), "abc");
        // Make request to API
        ResponseEntity<JwtResponse> response = restTemplate.postForEntity(
                "/api/users/login",
                request,
                JwtResponse.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getEmail()).isEqualTo(c.getEmail());
        assertThat(response.getBody().getName()).isEqualTo(c.getFull_name());
        assertThat(response.getBody().getType().getAuthority()).isEqualTo(UserAuthorities.CLIENT.toString());
        assertThat(response.getBody().getToken().length()).isGreaterThan(1);
    }

    @Test
    void whenPostLoginValidUser_Business_thenReturnBusiness() {
        // Create user and save to db
        Business b = new Business("business@ua.pt", "Business name", bcryptEncoder.encode("abc"), "apiKEY", "First Last Name", "Client's address street", "123456789");
        userRepository.saveAndFlush(b);

        // Make request
        JwtRequest request = new JwtRequest(b.getEmail(), "abc");
        // Make request to API
        ResponseEntity<JwtResponse> response = restTemplate.postForEntity(
                "/api/users/login",
                request,
                JwtResponse.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getEmail()).isEqualTo(b.getEmail());
        assertThat(response.getBody().getName()).isEqualTo(b.getFull_name());
        assertThat(response.getBody().getType().getAuthority()).isEqualTo(UserAuthorities.BUSINESS.toString());
        assertThat(response.getBody().getToken().length()).isGreaterThan(1);
    }

    @Test
    void whenPostLoginValidUser_Provider_thenReturnProvider() {
        // Create user and save to db
        Provider p = new Provider("provider@ua.pt", "First Last Name", bcryptEncoder.encode("abc"), new HashMap<>(), new ArrayList<>(), new ArrayList<>(), "123456789", LocalDate.now());
        userRepository.saveAndFlush(p);

        // Make request
        JwtRequest request = new JwtRequest(p.getEmail(), "abc");
        // Make request to API
        ResponseEntity<JwtResponse> response = restTemplate.postForEntity(
                "/api/users/login",
                request,
                JwtResponse.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getEmail()).isEqualTo(p.getEmail());
        assertThat(response.getBody().getName()).isEqualTo(p.getFull_name());
        assertThat(response.getBody().getType().getAuthority()).isEqualTo(UserAuthorities.PROVIDER.toString());
        assertThat(response.getBody().getToken().length()).isGreaterThan(1);
    }

    @Test
    void whenPostLoginInvalidUser_thenReturnError() {
        // Make request
        JwtRequest request = new JwtRequest("nonexistant@ua.pt", "abc");
        // Make request to API
        ResponseEntity<Exception> response = restTemplate.postForEntity(
                "/api/users/login",
                request,
                Exception.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void whenPostLoginValidUserInvalidPassword_thenReturnError() {
        // Create user and save to db
        Provider p = new Provider("provider@ua.pt", "First Last Name", bcryptEncoder.encode("abc"), new HashMap<>(), new ArrayList<>(), new ArrayList<>(), "123456789", LocalDate.now());
        userRepository.saveAndFlush(p);

        // Make request
        JwtRequest request = new JwtRequest(p.getEmail(), "abcd");
        // Make request to API
        ResponseEntity<Exception> response = restTemplate.postForEntity(
                "/api/users/login",
                request,
                Exception.class
        );

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

}
