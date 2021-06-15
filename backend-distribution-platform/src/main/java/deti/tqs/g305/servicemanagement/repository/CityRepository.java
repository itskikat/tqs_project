package deti.tqs.g305.servicemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import deti.tqs.g305.servicemanagement.model.City;

/**
 * CityRepository
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long>{

    
}