package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> getUserByEmail(String email);

}
