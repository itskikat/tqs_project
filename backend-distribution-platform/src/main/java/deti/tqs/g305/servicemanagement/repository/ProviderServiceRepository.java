package deti.tqs.g305.servicemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import deti.tqs.g305.servicemanagement.model.ProviderService;

/**
 * ProviderServiceRepository
 */
@Repository
public interface ProviderServiceRepository extends JpaRepository<ProviderService, Long>{

    public ProviderService findByProvider();

    
}