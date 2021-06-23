package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.Client;
import deti.tqs.g305.servicemanagement.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientUserServiceImpl implements ClientUserService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public Optional<Client> create(Client client) {
        Optional<Client> c = clientRepository.findById(client.getEmail());
        if(c.isEmpty()) {
            // Encrypt password
            client.setPassword(bcryptEncoder.encode(client.getPassword()));
            return Optional.of(clientRepository.save(client));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> update(String email, Client client) {
        Optional<Client> c = clientRepository.findByEmail(email);
        if(c.isPresent()) {
            Client final_client = c.get();
            if (client.getEmail() != null) { final_client.setEmail(client.getEmail()); }
            if (client.getPassword() != null) { final_client.setPassword(bcryptEncoder.encode(client.getPassword())); }
            if (client.getFull_name() != null) { final_client.setFull_name(client.getFull_name()); }
            if (client.getAddress() != null) { final_client.setAddress(client.getAddress()); }
            if (client.getLocation_city() != null) { final_client.setLocation_city(client.getLocation_city()); }
            if (client.getBirthdate() != null) { final_client.setBirthdate(client.getBirthdate()); }
            System.out.println(final_client);
            return Optional.of(clientRepository.saveAndFlush(final_client));
        }
        return Optional.empty();
    }


    @Override
    public boolean delete(String email){
        Optional<Client> client = clientRepository.findById(email);
        if (client.isPresent()) {
            clientRepository.delete(client.get());
            return true;
        } else {
            return false;
        }
    }


    @Override
    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

}
