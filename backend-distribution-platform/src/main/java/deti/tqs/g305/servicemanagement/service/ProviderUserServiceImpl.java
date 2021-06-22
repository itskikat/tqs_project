package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.Provider;
import deti.tqs.g305.servicemanagement.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProviderUserServiceImpl implements ProviderUserService {

    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public Optional<Provider> findByEmail(String email) {
        return providerRepository.findByEmail(email);
    }

    @Override
    public List<Provider> getProviders(){ return providerRepository.findAll(); }

    @Override
    public boolean deleteProvider(String email){
        Optional<Provider> provider = providerRepository.findById(email);
        if (provider.isPresent()) {
            providerRepository.delete(provider.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Provider> createProvider(Provider provider){

        Optional<Provider> p = providerRepository.findById(provider.getEmail());
        if(p.isEmpty()) {
            return Optional.of(providerRepository.save(provider));
        }
        return Optional.empty();
    }


    @Override
    public Optional<Provider> updateProvider(String email, Provider provider) {
        Optional<Provider> p = providerRepository.findByEmail(email);
        if(p.isEmpty()) {
            if (provider.getEmail() != null) { provider.setEmail(provider.getEmail()); }
            if (provider.getFull_name() != null) { provider.setFull_name(provider.getFull_name()); }
            if (provider.getBirthdate() != null) { provider.setBirthdate(provider.getBirthdate()); }
            if (provider.getPassword() != null) { provider.setPassword(provider.getPassword()); }
            if (provider.getWorking_hours() != null) { provider.setWorking_hours(provider.getWorking_hours()); }
            if (provider.getLocation_city() != null) { provider.setLocation_city(provider.getLocation_city()); }
            if (provider.getLocation_district() != null) { provider.setLocation_district(provider.getLocation_district()); }
            if (provider.getNif() != null) { provider.setNif(provider.getNif()); }

            return Optional.of(providerRepository.save(provider));
        }
        return Optional.empty();
    }

}
