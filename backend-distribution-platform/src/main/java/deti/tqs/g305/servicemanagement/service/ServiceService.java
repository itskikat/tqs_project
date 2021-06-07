package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.BusinessService;
import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.model.ServiceStatus;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.List;

/**
 * ServiceService
 */
public interface ServiceService {

    public Optional<ServiceContract> saveServiceContract(ServiceContract serviceContract);
    public Optional<ServiceContract> updateServiceContract(long serviceContractId, ServiceContract serviceContract);
    public Page<ServiceContract> getServiceContracts(String username, Pageable page, String userType);
    public Optional<ServiceContract> getServiceContract(String username,long serviceContractId);


    public Optional<BusinessService> saveBusinessService(BusinessService businessService);
    public String deleteBusinessService(long businessServiceId);
    public Optional<BusinessService> updateBusinessService(long businessServiceId, BusinessService businessService);
    public Optional<List<BusinessService>> getBusinessBusinessServices(long businessId);
}