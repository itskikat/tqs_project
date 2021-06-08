package deti.tqs.g305.servicemanagement.repository;

import deti.tqs.g305.servicemanagement.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ClientRepository
 */

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    public Client findByEmail(String email);

}
