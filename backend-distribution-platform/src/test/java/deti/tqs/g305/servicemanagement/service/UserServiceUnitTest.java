package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.*;
import deti.tqs.g305.servicemanagement.repository.BusinessRepository;
import deti.tqs.g305.servicemanagement.repository.ClientRepository;
import deti.tqs.g305.servicemanagement.repository.ProviderRepository;
import deti.tqs.g305.servicemanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock(lenient = true)
    private UserRepository userRepository;

    @Mock(lenient = true)
    private BusinessRepository businessRepository;

    @Mock(lenient = true)
    private ProviderRepository providerRepository;

    @Mock(lenient = true)
    private ClientRepository clientRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        // Mock repositories
        Business b = new Business("business@ua.pt", "Business name", "abc", "apiKEY", "First Last Name", "Client's address street", "123456789");
        Provider p = new Provider("provider@ua.pt", "First Last Name", "abc", new HashMap<>(), new ArrayList<>(), new ArrayList<>(), "123456789", LocalDate.now());
        Client c = new Client("client@ua.pt", "abc", "First Last Name", "Client's address street", LocalDate.now());


        Mockito.when(userRepository.findByEmail(b.getEmail())).thenReturn(Optional.of(b));
        Mockito.when(userRepository.findByEmail(p.getEmail())).thenReturn(Optional.of(p));
        Mockito.when(userRepository.findByEmail(c.getEmail())).thenReturn(Optional.of(c));

        Mockito.when(businessRepository.findByEmail(b.getEmail())).thenReturn(Optional.of(b));
        Mockito.when(businessRepository.findByEmail(p.getEmail())).thenReturn(Optional.empty());
        Mockito.when(businessRepository.findByEmail(c.getEmail())).thenReturn(Optional.empty());

        Mockito.when(providerRepository.findByEmail(p.getEmail())).thenReturn(Optional.of(p));
        Mockito.when(providerRepository.findByEmail(c.getEmail())).thenReturn(Optional.empty());

        Mockito.when(clientRepository.findByEmail(c.getEmail())).thenReturn(Optional.of(c));
    }

    @Nested
    class LoadUserByUsername {

        @Test
        void givenExistentUser_Business_thenReturnUserDetails() {
            // Call service
            UserDetails ud = userService.loadUserByUsername("business@ua.pt");

            // Validate response
            assertThat(ud.getUsername()).isEqualTo("business@ua.pt");
            assertThat(ud.getPassword()).isEqualTo("abc");
            assertThat(ud.getAuthorities().iterator().next().getAuthority()).isEqualTo(UserAuthorities.BUSINESS.name());
        }

        @Test
        void givenExistentUser_Provider_thenReturnUserDetails() {
            // Call service
            UserDetails ud = userService.loadUserByUsername("provider@ua.pt");

            // Validate response
            assertThat(ud.getUsername()).isEqualTo("provider@ua.pt");
            assertThat(ud.getPassword()).isEqualTo("abc");
            assertThat(ud.getAuthorities().iterator().next().getAuthority()).isEqualTo(UserAuthorities.PROVIDER.name());
        }

        @Test
        void givenExistentUser_Client_thenReturnUserDetails() {
            // Call service
            UserDetails ud = userService.loadUserByUsername("client@ua.pt");

            // Validate response
            assertThat(ud.getUsername()).isEqualTo("client@ua.pt");
            assertThat(ud.getPassword()).isEqualTo("abc");
            assertThat(ud.getAuthorities().iterator().next().getAuthority()).isEqualTo(UserAuthorities.CLIENT.name());
        }

        @Test
        void givenInexistentUser_thenThrowError() {
            // Call service and validate it raises exception
            assertThatThrownBy(() -> userService.loadUserByUsername("unknown@ua.pt"))
                    .isInstanceOf(UsernameNotFoundException.class)
                    .hasMessage("User not found with email: unknown@ua.pt");
        }

    }

    @Nested
    class GetUserByEmail {

        @Test
        void givenExistentUser_thenReturnUser() {
            // Call service
            Optional<User> u = userService.getUserByEmail("business@ua.pt");

            // Validate response
            assertThat(u.isPresent()).isTrue();
            assertThat(u.get().getEmail()).isEqualTo("business@ua.pt");
            assertThat(u.get().getFull_name()).isEqualTo("Business name");
            assertThat(u.get().getPassword()).isEqualTo("abc");
        }

        @Test
        void givenInexistentUser_thenReturnEmpty() {
            // Call service
            Optional<User> u = userService.getUserByEmail("unknown@ua.pt");

            // Validate response
            assertThat(u.isPresent()).isFalse();
        }

    }


}
