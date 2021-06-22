package deti.tqs.g305.servicemanagement.repository;

import deti.tqs.g305.servicemanagement.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.math.BigInteger;

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

    @Test
    void whengetTotalProfit_thenReturnProfit(){
        
        Provider p =saveData();
        Double profit = providerServiceRepository.getTotalProfit(p.getEmail(), LocalDate.now().minusWeeks(1),  LocalDate.now().plusWeeks(1));
        assertThat(profit).isEqualTo(40);
    }

    @Test
    void whengetTotalFinished_thenReturnNumberContracts(){
        
        Provider p =saveData();
        Integer profit = providerServiceRepository.getTotalFinished(p.getEmail(), LocalDate.now().minusWeeks(1),  LocalDate.now().plusWeeks(1));
        assertThat(profit).isEqualTo(3);
    }

    @Test
    void whengetTotalMostContractsProviderService_thenReturnProviderService(){

        Provider p =saveData();
        Long ps_id = providerServiceRepository.getTotalMostContractsProviderService(p.getEmail(), LocalDate.now().minusWeeks(1),  LocalDate.now().plusWeeks(1));
        assertThat(providerServiceRepository.findById(ps_id).get().getService().getName()).isEqualTo("Amareloooo");
    }

    @Test
    void whengetTotalMostProfitProviderService_thenReturnProviderService(){
        
        Provider p =saveData();
        Long ps_id = providerServiceRepository.getTotalMostProfitProviderService(p.getEmail(), LocalDate.now().minusWeeks(1),  LocalDate.now().plusWeeks(1));
        assertThat(providerServiceRepository.findById(ps_id).get().getService().getName()).isEqualTo("Amareloooo");
    } 
    
    @Test
    void whengetProfitHistory_thenReturnHistoryProfitList(){
        Provider p =saveData();
        List<Object[]>  hist = providerServiceRepository.getProfitHistory(p.getEmail(), LocalDate.now().minusWeeks(1),  LocalDate.now().plusWeeks(1));
        assertThat(hist.size()).isEqualTo(1);
        assertThat((Double) hist.get(0)[1]).isEqualTo(40.0);
    }
    
    @Test
    void whengetContractsHistory_thenReturnHistoryProfitList(){
        Provider p =saveData();
        List<Object[]>  hist = providerServiceRepository.getContractsHistory(p.getEmail(), LocalDate.now().minusWeeks(1),  LocalDate.now().plusWeeks(1));
        assertThat(hist.size()).isEqualTo(1);
        assertThat((BigInteger) hist.get(0)[1]).isEqualTo(BigInteger.valueOf(3L));
    }

    Provider saveData( ){
        Provider p = new Provider();
        p.setEmail("beubeu@mail.com");
        p.setPassword("anothersample");
        entityManager.persist(p);


        ServiceType s1 = new ServiceType("Canalização", false);
        ServiceType s2 = new ServiceType("Amareloooo", false);
        ProviderService bs3 = new ProviderService();
        bs3.setService(s1);
        ProviderService bs4 = new ProviderService();
        bs4.setService(s2);

        BusinessService bs5 = new BusinessService();
        bs5.setPrice(10);
        bs5.setService(s1);
        BusinessService bs6 = new BusinessService();
        bs6.setPrice(15);
        bs6.setService(s2);
        Business bu = new Business();
        bu.setEmail("anothersample@mail.com");
        bu.setPassword("anothersample");

        Client c = new Client();
        c.setEmail("sample@mail.com");
        c.setPassword("anothersample");

        ServiceContract sc = new ServiceContract(bs5, bs3, ServiceStatus.FINNISHED,c,0);
        ServiceContract sc1 = new ServiceContract(bs5, bs3, ServiceStatus.ACCEPTED,c,0);
        ServiceContract sc2 = new ServiceContract(bs6, bs4, ServiceStatus.FINNISHED,c,0);
        ServiceContract sc3 = new ServiceContract(bs6, bs4, ServiceStatus.FINNISHED,c,0);


        //BusinessService businessService,ProviderService providerService, ServiceStatus status, Client client, int review
        bs5.setBusiness(bu);

        bs3.setProvider(p);
        bs4.setProvider(p);
        bs5.setBusiness(bu);
        bs6.setBusiness(bu);

        entityManager.persist(s1);
        entityManager.persist(s2);
        entityManager.persist(bs3);
        entityManager.persist(bs4);
        entityManager.persist(bu);
        entityManager.persist(bs5);
        entityManager.persist(bs6);
        entityManager.persist(c);
        entityManager.persist(sc);
        entityManager.persist(sc1);
        entityManager.persist(sc2);
        entityManager.persist(sc3);

        entityManager.flush();

        return p;   
    }

}
