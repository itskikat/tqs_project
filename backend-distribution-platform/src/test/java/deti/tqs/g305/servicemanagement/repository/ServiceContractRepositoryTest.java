package deti.tqs.g305.servicemanagement.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import deti.tqs.g305.servicemanagement.model.ServiceContract;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * ServiceContractRepositoryTest
 */
@DataJpaTest
public class ServiceContractRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ServiceContractRepository serviceContractRepository;

    @Test
    public void whenFindSCById_thenReturnSC() {
        ServiceContract sc = new ServiceContract();
        entityManager.persistAndFlush(sc); //ensure data is persisted at this point

        ServiceContract found = serviceContractRepository.findById(sc.getId());
        assertThat( found ).isEqualTo(sc);
    }

    @Test
    public void whenFindSCByIdInvalid_thenReturnNull() {
        ServiceContract sc = new ServiceContract();
        entityManager.persistAndFlush(sc); //ensure data is persisted at this point

        ServiceContract found = serviceContractRepository.findById(sc.getId()-1);
        assertThat( found ).isNull();
    }

    @Test
    public void whenFindSCById_thenReturnSC() {
        ServiceContract sc = new ServiceContract();
        entityManager.persistAndFlush(sc); //ensure data is persisted at this point

        ServiceContract found = serviceContractRepository.findById(sc.getId());
        assertThat( found ).isEqualTo(sc);
    }

    @Test
    public void whenFindSCById_thenReturnSC() {
        ServiceContract sc = new ServiceContract();
        entityManager.persistAndFlush(sc); //ensure data is persisted at this point

        ServiceContract found = serviceContractRepository.findById(sc.getId());
        assertThat( found ).isEqualTo(sc);
    }

}