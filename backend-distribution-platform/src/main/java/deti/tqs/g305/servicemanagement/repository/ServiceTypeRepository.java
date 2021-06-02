package deti.tqs.g305.servicemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import deti.tqs.g305.servicemanagement.model.ServiceType;

/**
 * ServiceTypeRepository
 */
@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long>{

    
}