package deti.tqs.g305.servicemanagement.repository;

import deti.tqs.g305.servicemanagement.model.Business;
import deti.tqs.g305.servicemanagement.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ProviderRepository
 */

@Repository
public interface ProviderRepository extends JpaRepository<Provider, String> {

    Optional<Provider> findByEmail(String email);

}
