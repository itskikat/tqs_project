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
 * CityTest
 */
@DataJpaTest
public class CityRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CityRepository cityRepository;


    @Test
    public void whengetCities_thenReturnCities() {
        District d = new District("Braga");
        City c = new City("Braga", d);
        City c1 = new City("Porto", d);
        List<City> cs = new ArrayList<City>(Arrays.asList(c,c1));

        entityManager.persistAndFlush(d);
        entityManager.persistAndFlush(c);
        entityManager.persistAndFlush(c1);

        List<City> found = cityRepository.findAll();
        assertThat( found ).isEqualTo(cs);
    }

    @Test
    public void whengetCitiesByDistrictId_thenReturnCities() {
        District d = new District("Braga");
        District d1 = new District("Coimbra");

        City c = new City("Braga", d);
        City c1 = new City("Porto", d);
        City c2 = new City("Coimbra", d1);
        List<City> cs = new ArrayList<City>(Arrays.asList(c,c1));

        entityManager.persistAndFlush(d);
        entityManager.persistAndFlush(d1);
        entityManager.persistAndFlush(c);
        entityManager.persistAndFlush(c1);
        entityManager.persistAndFlush(c2);


        Optional<List<City>> found = cityRepository.findByDistrict_Id(d.getId());
        assertThat( found ).isEqualTo(Optional.of(cs));
    }

    @Test
    public void whengetCity_thenReturnCity() {
        District d = new District("Braga");
        City c = new City("Braga", d);

        entityManager.persistAndFlush(d);
        entityManager.persistAndFlush(c);

        Optional<City> found = cityRepository.findById(c.getId());
        assertThat( found ).isEqualTo(Optional.of(c));
    }

    @Test
    public void whengetCityInvalid_thenReturnEmpty() {
        District d = new District("Braga");
        City c = new City("Braga", d);

        entityManager.persistAndFlush(d);
        entityManager.persistAndFlush(c);

        Optional<City> found = cityRepository.findById(99L);
        assertThat( found ).isEqualTo(Optional.empty());
    }

}