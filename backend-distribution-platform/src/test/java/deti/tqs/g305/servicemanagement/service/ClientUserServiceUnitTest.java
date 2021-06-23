package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.*;
import deti.tqs.g305.servicemanagement.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientUserServiceUnitTest {

    @Mock(lenient = true)
    private ClientRepository clientRepository;

    @Mock(lenient = true)
    private CityRepository cityRepository;

    @Mock(lenient = true)
    private PasswordEncoder bcryptEncoder;

    @InjectMocks
    private ClientUserServiceImpl clientUserService;

    @Test
    void givenNewUserCreated_returnUser() {
        // Mock repository
        Client c = new Client("client@ua.pt", "abc", "First Last Name", "Client's address street", LocalDate.now());
        Mockito.when(clientRepository.findById(c.getEmail())).thenReturn(Optional.empty());
        Mockito.when(clientRepository.save(any())).thenReturn(c);

        // Call service
        Optional<Client> cop = clientUserService.create(c);

        // Validate response
        assertThat(cop.isPresent()).isTrue();
        assertThat(cop.get().getEmail()).isEqualTo(c.getEmail());

        // Validate mocks calls
        verify(clientRepository, times(1)).findById(any());
        verify(clientRepository, times(1)).save(any());
        verify(bcryptEncoder, times(1)).encode(any());
    }

    @Test
    void givenNewUserCreate_withLocation_returnUser() {
        // Mock repository
        City city = new City();
        city.setId(1L);
        city.setName("Aveiro");
        Client c = new Client("client@ua.pt", "abc", "First Last Name", "Client's address street", city, LocalDate.now());
        Mockito.when(clientRepository.findById(c.getEmail())).thenReturn(Optional.empty());
        Mockito.when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));
        Mockito.when(clientRepository.save(any())).thenReturn(c);

        // Call service
        Optional<Client> cop = clientUserService.create(c);

        // Validate response
        assertThat(cop.isPresent()).isTrue();
        assertThat(cop.get().getEmail()).isEqualTo(c.getEmail());
        assertThat(cop.get().getLocation_city().getName()).isEqualTo(city.getName());

        // Validate mocks calls
        verify(clientRepository, times(1)).findById(any());
        verify(clientRepository, times(1)).save(any());
        verify(bcryptEncoder, times(1)).encode(any());
    }

    @Test
    void givenUserExistent_returnEmpty() {
        // Mock repository
        Mockito.when(clientRepository.findById(any())).thenReturn(Optional.of(new Client()));

        // Call service
        Client c2 = new Client("abc@ua.pt", "abc", "First Last Name", "Client's address street", LocalDate.now());
        Optional<Client> cop = clientUserService.create(c2);

        // Validate response
        assertThat(cop.isEmpty()).isTrue();

        // Validate mocks calls
        verify(clientRepository, times(1)).findById(any());
        verify(clientRepository, times(0)).save(any());
        verify(bcryptEncoder, times(0)).encode(any());
    }

    @Test
    void updateWithoutChanges_thenReturnOriginal() {
        // Mock repository
        City city = new City();
        city.setId(1L);
        city.setName("Aveiro");
        Client c = new Client("client@ua.pt", "abc", "First Last Name", "Client's address street", city, LocalDate.now());
        Mockito.when(clientRepository.findById(any())).thenReturn(Optional.of(c));
        Mockito.when(clientRepository.save(any())).thenReturn(c);

        // Call service
        Optional<Client> cop = clientUserService.update(c.getEmail(), new Client());

        // Validate response
        assertThat(cop.isPresent()).isTrue();
        assertThat(cop.get().getEmail()).isEqualTo(c.getEmail());
        assertThat(cop.get().getFull_name()).isEqualTo(c.getFull_name());

        // Validate mocks calls
        verify(clientRepository, times(1)).findById(eq(c.getEmail()));
        verify(clientRepository, times(1)).save(any());
        verify(cityRepository, times(0)).findById(any());
        verify(bcryptEncoder, times(0)).encode(any());
    }

    @Test
    void updateWithChanges_thenReturnEditted() {
        // Mock repository
        City city = new City();
        city.setId(1L);
        city.setName("Aveiro");
        City city2 = new City();
        city2.setName("Porto");
        Client original = new Client("client@ua.pt", "abc", "First Last Name", "Client's address street", city, LocalDate.now());
        Client editted = new Client("client@ua.pt", "def", "MANUEL", "NEW ADDRESS", city, LocalDate.now());
        Mockito.when(clientRepository.findById(any())).thenReturn(Optional.of(original));
        Mockito.when(clientRepository.save(any())).thenReturn(editted);

        // Call service
        Optional<Client> cop = clientUserService.update(original.getEmail(), editted);

        // Validate response
        assertThat(cop.isPresent()).isTrue();

        // Validate mocks calls
        verify(clientRepository, times(1)).findById(eq(original.getEmail()));
        verify(clientRepository, times(1)).save(eq(editted));
        verify(cityRepository, times(1)).findById(eq(editted.getLocation_city().getId()));
        verify(bcryptEncoder, times(1)).encode(eq(editted.getPassword()));
    }

    @Test
    void whenGetLogged_thenReturnUser() {
        // Mock repository
        Mockito.when(clientRepository.findById(any())).thenReturn(Optional.of(new Client()));

        // Call service
        String email = "abc@ua.pt";
        Optional<Client> cop = clientUserService.getLogged(email);

        // Validate response
        assertThat(cop.isPresent()).isTrue();

        // Validate mocks calls
        verify(clientRepository, times(1)).findById(eq(email));
    }


}