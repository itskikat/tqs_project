package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.Business;

import java.util.List;
import java.util.Optional;

public interface BusinessUserService {
    Optional<Business> findByEmail(String email);
    List<Business> getBusiness();
    boolean deleteBusiness(String email);
    Optional<Business> createBusiness(Business provider);
    Optional<Business> updateBusiness(String email, Business provider);
}
