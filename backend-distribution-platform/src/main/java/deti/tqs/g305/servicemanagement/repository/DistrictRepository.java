package deti.tqs.g305.servicemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import deti.tqs.g305.servicemanagement.model.District;

import java.util.Optional;
import java.util.List;

/**
 * DistrictRepository
 */
@Repository
public interface DistrictRepository extends JpaRepository<District, Long>{

    Optional<District> findById(Long id);
    List<District> findAll();
}