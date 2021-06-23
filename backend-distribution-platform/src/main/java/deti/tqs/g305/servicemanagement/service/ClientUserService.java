package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.Business;
import deti.tqs.g305.servicemanagement.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientUserService {
    Optional<Client> create(Client client);
    Optional<Client> update(String email, Client client);
    boolean delete(String email);
    Optional<Client> findByEmail(String email);
}
