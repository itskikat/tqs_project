package deti.tqs.g305.servicemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import deti.tqs.g305.servicemanagement.model.BusinessService;

import java.util.Optional;

/**
 * BusinessServiceRepository
 */
@Repository
public interface BusinessServiceRepository extends JpaRepository<BusinessService, Long>{
    
    public Optional<BusinessService> findById(long id); 
}