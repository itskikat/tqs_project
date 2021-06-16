package deti.tqs.g305.servicemanagement.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import deti.tqs.g305.servicemanagement.model.*;

import java.util.List;
import java.time.LocalDate;



import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

/**
 * ServiceContractRepositoryTest
 */
@DataJpaTest
public class ServiceTypeRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    ServiceType st;

    @BeforeEach
    public void setUp() {
        st= new ServiceType("eletricista", false);
    }

    @Test
    public void whenFindSTbyId_thenReturnST() {
        entityManager.persistAndFlush(st); 

        ServiceType found = serviceTypeRepository.findById(st.getId());
        assertThat( found ).isEqualTo(st);
    }

    @Test
    public void whenFindSTbyIdInvalid_thenReturnNull() {
        entityManager.persistAndFlush(st); 

        ServiceType found = serviceTypeRepository.findById(-1L);
        assertThat( found ).isNull();
    }

}