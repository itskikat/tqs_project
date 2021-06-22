package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.repository.ProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProviderUserServiceTest {

    @Mock( lenient = true)
    private ProviderRepository providertRepository;

    @InjectMocks
    private ProviderUserServiceImpl providerUserService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void getProviders() {
    }

    @Test
    void deleteProvider() {
    }

    @Test
    void createProvider() {
    }

    @Test
    void updateProvider() {
    }
}