package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.Business;
import deti.tqs.g305.servicemanagement.model.City;
import deti.tqs.g305.servicemanagement.model.Client;
import deti.tqs.g305.servicemanagement.model.ProviderService;
import deti.tqs.g305.servicemanagement.repository.CityRepository;
import deti.tqs.g305.servicemanagement.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientUserServiceImpl implements ClientUserService {

    private static final Logger log = LoggerFactory.getLogger(ClientUserServiceImpl.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public Optional<Client> create(Client client) {
        Optional<Client> c = clientRepository.findById(client.getEmail());
        if(c.isEmpty()) {
            // Set location if id provided
            if (client.getLocation_city() != null) {
                Optional<City> city = cityRepository.findById(client.getLocation_city().getId());
                if(city.isPresent()){
                    client.setLocation_city(city.get());
                }
            }
            // Encrypt password
            client.setPassword(bcryptEncoder.encode(client.getPassword()));
            return Optional.of(clientRepository.save(client));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> update(String email, Client client) {
        return Optional.empty();
    }
}
