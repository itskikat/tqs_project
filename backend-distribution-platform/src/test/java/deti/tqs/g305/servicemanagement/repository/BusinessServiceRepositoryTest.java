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

import static org.assertj.core.api.Assertions.assertThat;


/**
 * BusinessServiceRepositoryTest
 */
@DataJpaTest
public class BusinessServiceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BusinessServiceRepository businessServiceRepository;

    BusinessService bs;
    Pageable page;

    @BeforeEach
    void setUp() {
        bs = new BusinessService();
        page = PageRequest.of(0, 10);
    }

    @Test
    void whenFindBusinessServiceByValidId_thenReturnBusinessService() {
        entityManager.persistAndFlush(bs);

        BusinessService found = businessServiceRepository.findById(bs.getId());

        assertThat(found).isEqualTo(bs);
    }

    @Test
    void whenFindBusinessServiceByInvalidId_thenReturnNull() {
        entityManager.persistAndFlush(bs);

        BusinessService found = businessServiceRepository.findById(-99L);

        assertThat(found).isNull();
    }

    @Test
    void whenFindBusinessServicesByValidBusiness_thenReturnBusinessServices() {
        BusinessService bs2 = new BusinessService();
        Business b = new Business();
        b.setEmail("sample@mail.com");
        b.setPassword("sample");

        bs.setBusiness(b);
        bs2.setBusiness(b);

        entityManager.persist(bs);
        entityManager.persist(bs2);
        entityManager.persist(b);

        List<BusinessService> found = businessServiceRepository.findByBusiness_Email(b.getEmail(), page).getContent();
        assertThat(found.get(0)).isEqualTo(bs);
        assertThat(found.get(1)).isEqualTo(bs2);
    }

    @Test
    void whenFindBusinessServicesByInvalidBusiness_thenReturnBusinessServices() {
        BusinessService bs3 = new BusinessService();
        Business b2 = new Business();
        b2.setEmail("anothersample@mail.com");
        b2.setPassword("anothersample");

        bs.setBusiness(b2);
        bs3.setBusiness(b2);

        entityManager.persist(bs);
        entityManager.persist(bs3);
        entityManager.persist(b2);

        List<BusinessService> found = businessServiceRepository.findByBusiness_Email("someinvalid@mail.com", page).getContent();
        assertThat(found).isEqualTo(Collections.emptyList());
    }

    @Test
    void whenFindBusinessServicesByName_ExistentName_thenReturnBusinessServices() {
        ServiceType s1 = new ServiceType("Canalização", false);
        ServiceType s2 = new ServiceType("Amareloooo", false);
        BusinessService bs3 = new BusinessService();
        bs3.setService(s1);
        BusinessService bs4 = new BusinessService();
        bs4.setService(s2);
        Business b2 = new Business();
        b2.setEmail("anothersample@mail.com");
        b2.setPassword("anothersample");

        bs.setBusiness(b2);
        bs3.setBusiness(b2);
        bs4.setBusiness(b2);

        entityManager.persist(s1);
        entityManager.persist(s2);
        entityManager.persist(bs);
        entityManager.persist(bs3);
        entityManager.persist(bs4);
        entityManager.persist(b2);
        entityManager.flush();

        String name = "Canaliz";

        List<BusinessService> found = businessServiceRepository.findByBusiness_EmailAndService_NameContains(b2.getEmail(), page, name).getContent();
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0)).isEqualTo(bs3);
    }

    @Test
    void whenFindBusinessServicesByName_InexistentName_thenReturnEmpty() {
        ServiceType s1 = new ServiceType("Canalização", false);
        BusinessService bs3 = new BusinessService();
        bs3.setService(s1);
        Business b2 = new Business();
        b2.setEmail("anothersample@mail.com");
        b2.setPassword("anothersample");

        bs.setBusiness(b2);
        bs3.setBusiness(b2);

        entityManager.persist(s1);
        entityManager.persist(bs);
        entityManager.persist(bs3);
        entityManager.persist(b2);
        entityManager.flush();

        String name = "Hell";

        List<BusinessService> found = businessServiceRepository.findByBusiness_EmailAndService_NameContains(b2.getEmail(), page, name).getContent();
        assertThat(found.size()).isEqualTo(0);
    }

    @Test
    void whenFindBusinessServicesMostRequestedServiceTypeIdByValidBusiness_thenReturnServiceTypeId() {
        ServiceType st = new ServiceType("canalizacao", true);
        BusinessService bs2 = new BusinessService();
        bs2.setService(st);

        Business b = new Business();
        b.setEmail("sample@mail.com");
        b.setPassword("sample");

        BusinessService bs = new BusinessService(10, st, b);

        bs2.setBusiness(b);

        entityManager.persist(bs);
        entityManager.persist(bs2);
        entityManager.persist(b);
        entityManager.persist(st);

        Long found = businessServiceRepository.findByBusiness_Email_MostRequestedServiceTypeId(b.getEmail());
        assertThat(found).isEqualTo(st.getId());
    }

}
