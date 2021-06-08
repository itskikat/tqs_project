package deti.tqs.g305.servicemanagement.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    public UserDetails getDetails(String username);

}
