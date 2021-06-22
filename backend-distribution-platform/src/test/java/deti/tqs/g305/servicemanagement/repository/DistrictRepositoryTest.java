package deti.tqs.g305.servicemanagement.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import deti.tqs.g305.servicemanagement.model.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.time.LocalDate;



import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

/**
 * DistrictRepositoryTest
 */
@DataJpaTest
public class DistrictRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DistrictRepository districtRepository;


    @Test
    public void whengetDistricts_thenReturnDistricts() {
        District d = new District("Braga");
        District d1 = new District("Porto");
        List<District> d2 = new ArrayList<District>(Arrays.asList(d,d1));

        entityManager.persistAndFlush(d);
        entityManager.persistAndFlush(d1);


        List<District> found = districtRepository.findAll();
        assertThat( found ).isEqualTo(d2);
    }

    

    @Test
    public void whengetDistrict_thenReturnDistrict() {
        District d = new District("Braga");

        entityManager.persistAndFlush(d);

        Optional<District> found = districtRepository.findById(d.getId());
        assertThat( found ).isEqualTo(Optional.of(d));
    }

    @Test
    public void whengetDistrictInvalid_thenReturnEmpty() {

        Optional<District> found = districtRepository.findById(99L);
        assertThat( found ).isEqualTo(Optional.empty());
    }

}