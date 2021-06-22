package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.Business;
import deti.tqs.g305.servicemanagement.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BusinessUserServiceImpl implements BusinessUserService {

    @Autowired
    private BusinessRepository businessRepository;

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
        Optional<Business> b = businessRepository.findById(business.getEmail());
        if(b.isEmpty()) {
            return Optional.of(businessRepository.save(business));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Business> updateBusiness(String email, Business business) {
        Optional<Business> b = businessRepository.findByEmail(email);
        if(b.isEmpty()) {
            if (business.getEmail() != null) { business.setEmail(business.getEmail()); }
            if (business.getFull_name() != null) { business.setFull_name(business.getFull_name()); }
            if (business.getApikey() != null) { business.setApikey(business.getApikey()); }
            if (business.getPassword() != null) { business.setPassword(business.getPassword()); }
            if (business.getName() != null) { business.setName(business.getName()); }
            if (business.getAddress() != null) { business.setAddress(business.getAddress()); }
            if (business.getNif() != null) { business.setNif(business.getNif()); }

            return Optional.of(businessRepository.save(business));
        }
        return Optional.empty();
    }
}
