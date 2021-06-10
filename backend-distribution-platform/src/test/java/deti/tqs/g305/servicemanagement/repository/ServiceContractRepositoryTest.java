package deti.tqs.g305.servicemanagement.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import deti.tqs.g305.servicemanagement.model.*;

import java.util.List;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

/**
 * ServiceContractRepositoryTest
 */
@DataJpaTest
public class ServiceContractRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ServiceContractRepository serviceContractRepository;

    @Autowired
    private ClientRepository clientRepository;

    ServiceContract sc;
    Pageable page;

    @BeforeEach
    public void setUp() {
        sc = new ServiceContract();
        page = PageRequest.of(0,10);

    }

    @Test
    public void whenFindSCById_thenReturnSC() {
        entityManager.persistAndFlush(sc); //ensure data is persisted at this point

        ServiceContract found = serviceContractRepository.findById(sc.getId());
        assertThat( found ).isEqualTo(sc);
    }

    @Test
    public void whenFindSCByIdInvalid_thenReturnNull() {
        entityManager.persistAndFlush(sc); //ensure data is persisted at this point

        ServiceContract found = serviceContractRepository.findById(sc.getId()-1);
        assertThat( found ).isNull();
    }

    @Test
    public void whenFindSCByClient_thenReturnSC() {
        Client c1 = new Client("teste@ua.pt", "s", "s", "c", LocalDate.now()) ;
        
        c1 = entityManager.persistAndFlush(c1);
        sc.setClient(c1);
        entityManager.persistAndFlush(sc); //ensure data is persisted at this point

        List<ServiceContract> found = serviceContractRepository.findByClientEmail("teste@ua.pt",page).getContent();
        assertThat( found.get(0) ).isEqualTo(sc);
    }

    @Test
    public void whenFindSCByClientInvalidEmail_thenReturnSC() {
        Client c1 = new Client("teste1@ua.pt", "s", "s", "c", LocalDate.now()) ;
        sc.setClient(c1);
        entityManager.persistAndFlush(c1);
        entityManager.persistAndFlush(sc); //ensure data is persisted at this point

        List<ServiceContract> found = serviceContractRepository.findByClientEmail("Invalid", page).getContent();
        assertThat( found ).isEqualTo(Collections.emptyList());
    }

    @Test
    public void whenFindSCByProvider_thenReturnSC() {
        ProviderService p = new ProviderService() ;
        Provider p1 = new Provider();
        p1.setEmail("teste@ua.pt");
        p1.setPassword("test");
        p.setProvider(p1);

        sc.setProviderService(p);
        entityManager.persistAndFlush(p1);
        entityManager.persistAndFlush(p);
        entityManager.persistAndFlush(sc); //ensure data is persisted at this point

        Page<ServiceContract> page1 = serviceContractRepository.findByProviderService_Provider_Email(p1.getEmail(), page);
        List<ServiceContract> found = page1.getContent();
        assertThat( found.get(0) ).isEqualTo(sc);
    }

    @Test
    public void whenFindSCByProviderInvalidName_thenReturnSC() {
        ProviderService p = new ProviderService() ;
        Provider p1 = new Provider();
        p1.setEmail("teste1@ua.pt");
        p1.setPassword("test");
        p.setProvider(p1);

        sc.setProviderService(p);
        entityManager.persistAndFlush(p1);
        entityManager.persistAndFlush(p);
        entityManager.persistAndFlush(sc); //ensure data is persisted at this point

        Page<ServiceContract> page1 = serviceContractRepository.findByProviderService_Provider_Email("lala", page);
        List<ServiceContract> found = page1.getContent();

        assertThat( found ).isEqualTo(Collections.emptyList());
    }

    @Test
    public void whenFindSCByBusiness_thenReturnSC() {
        BusinessService b = new BusinessService() ;
        Business b1 = new Business();
        b1.setEmail("teste@ua.pt");
        b1.setPassword("test");
        b.setBusiness(b1);

        sc.setBusinessService(b);
        entityManager.persistAndFlush(b1);
        entityManager.persistAndFlush(b);
        entityManager.persistAndFlush(sc); //ensure data is persisted at this point

        List<ServiceContract>  found = serviceContractRepository.findByBusinessService_Business_Email(b1.getEmail(), page).getContent();
        assertThat( found.get(0) ).isEqualTo(sc);
    }

    @Test
    public void whenFindSCByBusinessInvalidEmail_thenReturnSC() {
        BusinessService b = new BusinessService() ;
        Business b1 = new Business();
        b1.setEmail("teste1@ua.pt");
        b1.setPassword("test");
        b.setBusiness(b1);

        sc.setBusinessService(b);
        entityManager.persistAndFlush(b1);
        entityManager.persistAndFlush(b);
        entityManager.persistAndFlush(sc); //ensure data is persisted at this point

        List<ServiceContract> found = serviceContractRepository.findByBusinessService_Business_Email("Invalid", page).getContent();
        assertThat( found ).isEqualTo(Collections.emptyList());
    }

}
