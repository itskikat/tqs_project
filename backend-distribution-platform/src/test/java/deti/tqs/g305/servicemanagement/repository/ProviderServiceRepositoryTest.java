package deti.tqs.g305.servicemanagement.repository;

import deti.tqs.g305.servicemanagement.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * ProviderServiceRepositoryTest
 */
@DataJpaTest
public class ProviderServiceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProviderServiceRepository providerServiceRepository;

    ProviderService bs;
    Pageable page;

    @BeforeEach
    void setUp() {
        bs = new ProviderService();
        page = PageRequest.of(0, 10);
    }

    @Test
    void whenFindBusinessServiceByValidId_thenReturnBusinessService() {
        entityManager.persistAndFlush(bs);

        Optional<ProviderService> found = providerServiceRepository.findById(bs.getId());

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get()).isEqualTo(bs);
    }

    @Test
    void whenFindBusinessServiceByInvalidId_thenReturnNull() {
        entityManager.persistAndFlush(bs);

        Optional<ProviderService> found = providerServiceRepository.findById(-99L);

        assertThat(found.isEmpty()).isTrue();
    }

    @Test
    void whenFindProviderServicesByValidProvider_thenReturnProviderServices() {
        ProviderService bs2 = new ProviderService();
        Provider b = new Provider();
        b.setEmail("sample@mail.com");
        b.setPassword("sample");

        bs.setProvider(b);
        bs2.setProvider(b);

        entityManager.persist(bs);
        entityManager.persist(bs2);
        entityManager.persist(b);

        List<ProviderService> found = providerServiceRepository.findByProvider_Email(b.getEmail(), page).getContent();
        assertThat(found.get(0)).isEqualTo(bs);
        assertThat(found.get(1)).isEqualTo(bs2);
    }

    @Test
    void whenFindProviderServicesByInvalidProvider_thenReturnProviderServices() {
        ProviderService bs3 = new ProviderService();
        Provider b2 = new Provider();
        b2.setEmail("anothersample@mail.com");
        b2.setPassword("anothersample");

        bs.setProvider(b2);
        bs3.setProvider(b2);

        entityManager.persist(bs);
        entityManager.persist(bs3);
        entityManager.persist(b2);

        List<ProviderService> found = providerServiceRepository.findByProvider_Email("someinvalid@mail.com", page).getContent();
        assertThat(found).isEqualTo(Collections.emptyList());
    }

    @Test
    void whenFindProviderServicesByName_ExistentName_thenReturnProviderServices() {
        ServiceType s1 = new ServiceType("Canalização", false);
        ServiceType s2 = new ServiceType("Amareloooo", false);
        ProviderService bs3 = new ProviderService();
        bs3.setService(s1);
        ProviderService bs4 = new ProviderService();
        bs4.setService(s2);
        Provider b2 = new Provider();
        b2.setEmail("anothersample@mail.com");
        b2.setPassword("anothersample");

        bs.setProvider(b2);
        bs3.setProvider(b2);
        bs4.setProvider(b2);

        entityManager.persist(s1);
        entityManager.persist(s2);
        entityManager.persist(bs);
        entityManager.persist(bs3);
        entityManager.persist(bs4);
        entityManager.persist(b2);
        entityManager.flush();

        String name = "Canaliz";

        List<ProviderService> found = providerServiceRepository.findByProvider_EmailAndService_NameContains(b2.getEmail(), page, name).getContent();
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0)).isEqualTo(bs3);
    }

    @Test
    void whenFindProviderServicesByName_InexistentName_thenReturnEmpty() {
        ServiceType s1 = new ServiceType("Canalização", false);
        ProviderService bs3 = new ProviderService();
        bs3.setService(s1);
        Provider b2 = new Provider();
        b2.setEmail("anothersample@mail.com");
        b2.setPassword("anothersample");

        bs.setProvider(b2);
        bs3.setProvider(b2);

        entityManager.persist(s1);
        entityManager.persist(bs);
        entityManager.persist(bs3);
        entityManager.persist(b2);
        entityManager.flush();

        String name = "Hell";

        List<ProviderService> found = providerServiceRepository.findByProvider_EmailAndService_NameContains(b2.getEmail(), page, name).getContent();
        assertThat(found.size()).isEqualTo(0);
    }

}
