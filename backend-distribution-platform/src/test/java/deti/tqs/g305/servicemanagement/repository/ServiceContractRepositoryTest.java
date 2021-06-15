package deti.tqs.g305.servicemanagement.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import deti.tqs.g305.servicemanagement.model.*;

import java.util.List;
import java.time.LocalDate;

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
    ServiceType st1;
    Pageable page;

    @BeforeEach
    public void setUp() {
        sc = new ServiceContract();

        Client c1 = new Client("teste@ua.pt", "s", "s", "c", LocalDate.now()) ;
        sc.setClient(c1);
        entityManager.persistAndFlush(c1);

        ProviderService p = new ProviderService() ;
        Provider p1 = new Provider();
        st1= new ServiceType("canalização",false);
        p1.setEmail("teste1@ua.pt");
        p1.setPassword("test");
        p.setProvider(p1);
        p.setService(st1);
        sc.setProviderService(p);

        entityManager.persistAndFlush(st1);
        entityManager.persistAndFlush(p1);
        entityManager.persistAndFlush(p);
        
        BusinessService b = new BusinessService() ;
        Business b1 = new Business();
        b1.setEmail("teste2@ua.pt");
        b1.setPassword("test");
        b.setBusiness(b1);

        sc.setBusinessService(b);
        entityManager.persistAndFlush(b1);
        entityManager.persistAndFlush(b);

        sc.setStatus(ServiceStatus.WAITING);

        entityManager.persistAndFlush(sc);
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

        List<ServiceContract> found = serviceContractRepository.findByClientEmail("teste@ua.pt",page).getContent();
        assertThat( found.get(0) ).isEqualTo(sc);
    }

    @Test
    public void whenFindSCByClientInvalidEmail_thenReturnSC() {
        List<ServiceContract> found = serviceContractRepository.findByClientEmail("Invalid", page).getContent();
        assertThat( found ).isEqualTo(Collections.emptyList());
    }

    @Test
    public void whenFindSCByProvider_thenReturnSC() {
        List<ServiceContract> found  = serviceContractRepository.findByProviderService_Provider_Email("teste1@ua.pt", page).getContent();
        assertThat( found.get(0) ).isEqualTo(sc);
    }

    @Test
    public void whenFindSCByProviderInvalidName_thenReturnSC() {
        List<ServiceContract> found = serviceContractRepository.findByProviderService_Provider_Email("lala", page).getContent();
        assertThat( found ).isEqualTo(Collections.emptyList());
    }

    @Test
    public void whenFindSCByBusiness_thenReturnSC() {
        List<ServiceContract>  found = serviceContractRepository.findByBusinessService_Business_Email("teste2@ua.pt", page).getContent();
        assertThat( found.get(0) ).isEqualTo(sc);
    }

    @Test
    public void whenFindSCByBusinessInvalidEmail_thenReturnSC() {
        List<ServiceContract> found = serviceContractRepository.findByBusinessService_Business_Email("Invalid", page).getContent();
        assertThat( found ).isEqualTo(Collections.emptyList());
    }


    @Test
    public void whenFindSCByStatus_thenReturnSC() {

        //Client
        List<ServiceContract> found = serviceContractRepository.findByStatusAndClientEmail(ServiceStatus.WAITING,"teste@ua.pt", page).getContent();
        assertThat( found.get(0) ).isEqualTo(sc);

        //Provider
        found = serviceContractRepository.findByStatusAndProviderService_Provider_Email(ServiceStatus.WAITING,"teste1@ua.pt", page).getContent();
        assertThat( found.get(0) ).isEqualTo(sc);
    }

    @Test
    public void whenFindSCByType_thenReturnSC() {
        //Client
        List<ServiceContract> found = serviceContractRepository.findByProviderService_Service_IdAndClientEmail(st1.getId(),"teste@ua.pt", page).getContent();
        assertThat( found.get(0) ).isEqualTo(sc);

        //Provider
        found = serviceContractRepository.findByProviderService_Service_IdAndProviderService_Provider_Email(st1.getId(),"teste1@ua.pt", page).getContent();
        assertThat( found.get(0) ).isEqualTo(sc);
    }

    @Test
    public void whenFindSCByTypeAndStatus_thenReturnSC() {
       //Client
       List<ServiceContract> found = serviceContractRepository.findByStatusAndProviderService_Service_IdAndClientEmail(ServiceStatus.WAITING,st1.getId(),"teste@ua.pt", page).getContent();
       assertThat( found.get(0) ).isEqualTo(sc);

       //Provider
       found = serviceContractRepository.findByStatusAndProviderService_Service_IdAndProviderService_Provider_Email(ServiceStatus.WAITING,st1.getId(),"teste1@ua.pt", page).getContent();
       assertThat( found.get(0) ).isEqualTo(sc);
    }

    @Test
    public void whenFindSCByStatusInvalid_thenReturnSC() {
        //Client
        List<ServiceContract> found = serviceContractRepository.findByStatusAndClientEmail(ServiceStatus.FINNISHED,"teste@ua.pt", page).getContent();
        assertThat( found ).isEqualTo(Collections.emptyList());

        //Provider
        found = serviceContractRepository.findByStatusAndProviderService_Provider_Email(ServiceStatus.FINNISHED,"teste1@ua.pt", page).getContent();
        assertThat( found ).isEqualTo(Collections.emptyList());
    }

    @Test
    public void whenFindSCByTypeInvalid_thenReturnSC() {
        //Client
        List<ServiceContract> found = serviceContractRepository.findByProviderService_Service_IdAndClientEmail(-1L,"teste@ua.pt", page).getContent();
        assertThat( found ).isEqualTo(Collections.emptyList());

        //Provider
        found = serviceContractRepository.findByProviderService_Service_IdAndProviderService_Provider_Email(-1L,"teste1@ua.pt", page).getContent();
        assertThat( found ).isEqualTo(Collections.emptyList());
    }

    @Test
    public void whenFindSCByTypeAndStatusInvalid_thenReturnSC() {
        //Client
       List<ServiceContract> found = serviceContractRepository.findByStatusAndProviderService_Service_IdAndClientEmail(ServiceStatus.REJECTED,-1L,"teste@ua.pt", page).getContent();
       assertThat( found ).isEqualTo(Collections.emptyList());

       //Provider
       found = serviceContractRepository.findByStatusAndProviderService_Service_IdAndProviderService_Provider_Email(ServiceStatus.ACCEPTED,-10L,"teste1@ua.pt", page).getContent();
       assertThat( found ).isEqualTo(Collections.emptyList());
    }

}
