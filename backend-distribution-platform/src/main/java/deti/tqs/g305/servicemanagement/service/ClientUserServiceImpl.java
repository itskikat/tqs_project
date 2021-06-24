package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.Business;
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
            return Optional.of(clientRepository.saveAndFlush(client));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> update(String email, Client client) {
        return Optional.empty();
    }
}
