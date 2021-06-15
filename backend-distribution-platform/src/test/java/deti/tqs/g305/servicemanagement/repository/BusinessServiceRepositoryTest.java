package deti.tqs.g305.servicemanagement.repository;

import deti.tqs.g305.servicemanagement.model.Business;
import deti.tqs.g305.servicemanagement.model.BusinessService;
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

}
