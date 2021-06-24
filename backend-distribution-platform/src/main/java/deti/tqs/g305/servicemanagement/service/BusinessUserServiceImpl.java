package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.Business;
import deti.tqs.g305.servicemanagement.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
@Transactional
public class BusinessUserServiceImpl implements BusinessUserService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public Optional<Business> findByEmail(String email) {
        return businessRepository.findByEmail(email);
    }

    @Override
    public List<Business> getBusiness() {
        return businessRepository.findAll();
    }

    @Override
    public boolean deleteBusiness(String email) {
        Optional<Business> business = businessRepository.findById(email);
        if (business.isPresent()) {
            businessRepository.delete(business.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Business> createBusiness(Business business) {
        Optional<Business> b = businessRepository.findByEmail(business.getEmail());
        if(b.isEmpty()) {
            business.setPassword(bcryptEncoder.encode(business.getPassword()));
            businessRepository.saveAndFlush(business);
            return generateToken(business.getEmail());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Business> updateBusiness(String email, Business business) {
        Optional<Business> b = businessRepository.findByEmail(email);
        if(b.isPresent()) {
            Business b1= b.get();
            if (business.getEmail() != null) { b1.setEmail(business.getEmail()); }
            if (business.getFull_name() != null) { b1.setFull_name(business.getFull_name()); }
            if (business.getApikey() != null) { b1.setApikey(business.getApikey()); }
            if (business.getPassword() != null) { b1.setPassword(business.getPassword()); }
            if (business.getName() != null) { b1.setName(business.getName()); }
            if (business.getAddress() != null) { b1.setAddress(business.getAddress()); }
            if (business.getNif() != null) { b1.setNif(business.getNif()); }
            System.out.println("AQUI");
            return Optional.of(businessRepository.saveAndFlush(b1));
        }
        System.out.println(email);
        return Optional.empty();
    }

    @Override
    public Optional<Business> generateToken(String email){
        Optional<Business> b = businessRepository.findByEmail(email);
        if(b.isPresent()){
            Business bu= b.get();
            String token = this.getRandomToken();
            bu.setApikey(token);
            b= Optional.of(businessRepository.saveAndFlush(bu));
        }
        return b;
    }

    @Override
    public String getRandomToken(){
        UUID uuid = UUID.randomUUID();
        Optional<Business> b = businessRepository.findByApikey(uuid.toString());
        if(b.isPresent()){
            return getRandomToken();
        }
        return uuid.toString();
    }
}
