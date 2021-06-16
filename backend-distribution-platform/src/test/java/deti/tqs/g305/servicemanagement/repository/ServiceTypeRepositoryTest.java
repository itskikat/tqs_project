package deti.tqs.g305.servicemanagement.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import deti.tqs.g305.servicemanagement.model.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
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
        entityManager.persistAndFlush(st); 
    }

    @Test
    public void whenFindSTbyId_thenReturnST() {
        ServiceType found = serviceTypeRepository.findById(st.getId());
        assertThat( found ).isEqualTo(st);
    }

    @Test
    public void whenFindSTbyIdInvalid_thenReturnNull() {
        ServiceType found = serviceTypeRepository.findById(-1L);
        assertThat( found ).isNull();
    }

    @Test
    public void whenFindSTbyName_thenReturnST() {
        
        Optional<ServiceType> found = serviceTypeRepository.findByName(st.getName());
        assertThat( found.get() ).isEqualTo(st);
    }

    @Test
    public void whenFindSTbyNameInvalid_thenReturnEmpty() {
        Optional<ServiceType> found = serviceTypeRepository.findByName("Invalid");
        assertThat( found ).isEqualTo(Optional.empty());
    }


    @Test
    public void whenFindSTbyNameContains_thenReturnST() {
        
        List<ServiceType> found = serviceTypeRepository.findByNameContains("ele");
        assertThat( found.get(0) ).isEqualTo(st);
    }

    @Test
    public void whenFindSTbyNameContainsInvalid_thenReturnEmpty() {
        List<ServiceType> found = serviceTypeRepository.findByNameContains("elex");
        assertThat( found ).isEqualTo(Collections.emptyList());
    }

    @Test
    public void whenFindAll_thenReturnAllST() {
        ServiceType st1= new ServiceType("jardinagem", false);
        entityManager.persistAndFlush(st1);
        ServiceType st2= new ServiceType("carpintaria", false);
        entityManager.persistAndFlush(st2);

        List<ServiceType> stlist= new ArrayList<ServiceType>();
        stlist.add(st);
        stlist.add(st1);
        stlist.add(st2);

        List<ServiceType>  found = serviceTypeRepository.findAll();
        assertThat( found ).isEqualTo(stlist);
    }

}