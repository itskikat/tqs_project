package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.User;
import deti.tqs.g305.servicemanagement.model.UserAuthorities;
import deti.tqs.g305.servicemanagement.model.UserAuthority;
import deti.tqs.g305.servicemanagement.repository.BusinessRepository;
import deti.tqs.g305.servicemanagement.repository.ClientRepository;
import deti.tqs.g305.servicemanagement.repository.ProviderRepository;
import deti.tqs.g305.servicemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // Get user
        User user = userRepository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + s));

        // Get authority
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (businessRepository.findByEmail(s).isPresent()) {
            authorities.add(new UserAuthority(UserAuthorities.BUSINESS.name()));
        } else if (providerRepository.findByEmail(s).isPresent()) {
            authorities.add(new UserAuthority(UserAuthorities.PROVIDER.name()));
        } else if (clientRepository.findByEmail(s).isPresent()) {
            authorities.add(new UserAuthority(UserAuthorities.CLIENT.name()));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
