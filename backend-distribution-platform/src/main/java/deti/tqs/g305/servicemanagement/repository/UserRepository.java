package deti.tqs.g305.servicemanagement.repository;

import deti.tqs.g305.servicemanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 */

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
