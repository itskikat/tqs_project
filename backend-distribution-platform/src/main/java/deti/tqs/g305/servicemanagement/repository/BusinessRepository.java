package deti.tqs.g305.servicemanagement.repository;

import deti.tqs.g305.servicemanagement.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import deti.tqs.g305.servicemanagement.model.Business;

import java.util.List;
import java.util.Optional;

/**
 * BusinessRepository
 */
@Repository
public interface BusinessRepository extends JpaRepository<Business, String>{

    Optional<Business> findByEmail(String email);
    Optional<Business> findByApikey(String apiKey);
    List<Business> findAll();
}