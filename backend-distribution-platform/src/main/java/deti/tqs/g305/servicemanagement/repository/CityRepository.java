package deti.tqs.g305.servicemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

import deti.tqs.g305.servicemanagement.model.City;

/**
 * CityRepository
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long>{

    Optional<City> findById(Long id);
    Optional<List<City>> findByDistrict_Id(Long id);
    List<City> findAll();
}