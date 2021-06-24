package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.Provider;
import deti.tqs.g305.servicemanagement.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProviderUserServiceImpl implements ProviderUserService {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

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
            provider.setPassword(bcryptEncoder.encode(provider.getPassword()));
            return Optional.of(providerRepository.saveAndFlush(provider));
        }
        return Optional.empty();
    }


    @Override
    public Optional<Provider> updateProvider(String email, Provider provider) {
        Optional<Provider> p = providerRepository.findByEmail(email);
        if(p.isPresent()) {
            Provider final_provider = p.get();
            if (provider.getEmail() != null) { final_provider.setEmail(provider.getEmail()); }
            if (provider.getFull_name() != null) { final_provider.setFull_name(provider.getFull_name()); }
            if (provider.getBirthdate() != null) { final_provider.setBirthdate(provider.getBirthdate()); }
            if (provider.getPassword() != null) { final_provider.setPassword(bcryptEncoder.encode(provider.getPassword())); }
            if (provider.getWorking_hours() != null) { final_provider.setWorking_hours(provider.getWorking_hours()); }
            if (provider.getLocation_city() != null) { final_provider.setLocation_city(provider.getLocation_city()); }
            if (provider.getLocation_district() != null) { final_provider.setLocation_district(provider.getLocation_district()); }
            if (provider.getCategory() != null) { final_provider.setCategory(provider.getCategory()); }
            if (provider.getNif() != null) { provider.setNif(final_provider.getNif()); }
            System.out.println(final_provider);
            return Optional.of(providerRepository.saveAndFlush(final_provider));
        }
        return Optional.empty();
    }

}
