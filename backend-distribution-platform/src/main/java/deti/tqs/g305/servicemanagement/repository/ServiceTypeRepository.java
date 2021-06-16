package deti.tqs.g305.servicemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import deti.tqs.g305.servicemanagement.model.ServiceType;

import java.util.Optional;
import java.util.List;

/**
 * ServiceTypeRepository
 */
@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long>{

    public ServiceType findById(long id);
    public Optional<ServiceType> findByName(String name);
    public List<ServiceType> findAll();
    public List<ServiceType> findByNameContains(String name);
    
}