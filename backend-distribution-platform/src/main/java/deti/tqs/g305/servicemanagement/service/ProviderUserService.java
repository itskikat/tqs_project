package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.Provider;

import java.util.List;
import java.util.Optional;


public interface ProviderUserService {
    Optional<Provider> findByEmail(String email);
    List<Provider> getProviders();
    boolean deleteProvider(String email);
    Optional<Provider> createProvider(Provider provider);
    Optional<Provider> updateProvider(String email, Provider provider);
}
