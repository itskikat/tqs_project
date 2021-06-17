package deti.tqs.g305.servicemanagement.repository;

import deti.tqs.g305.servicemanagement.model.BusinessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import deti.tqs.g305.servicemanagement.model.ProviderService;

import java.util.Optional;

/**
 * ProviderServiceRepository
 */
@Repository
public interface ProviderServiceRepository extends JpaRepository<ProviderService, Long>{

    public Optional<ProviderService> findById(long id);
    public Page<ProviderService> findByProvider_Email(String provider_id, Pageable page);
    public Page<ProviderService> findByProvider_EmailAndService_NameContains(String provider_id, Pageable page, String name);
}