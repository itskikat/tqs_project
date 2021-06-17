package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.BusinessService;
import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.model.ServiceStatus;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * ServiceService
 */
public interface ServiceService {

    public Optional<ServiceContract> saveServiceContract(ServiceContract serviceContract);
    public Optional<ServiceContract> updateServiceContract(long serviceContractId, ServiceContract serviceContract);
    public Page<ServiceContract> getServiceContracts(String username, Pageable page, String userType, Optional<ServiceStatus> status, Optional<Long> type);
    public Optional<ServiceContract> getServiceContract(String username,long serviceContractId);


    public Optional<BusinessService> saveBusinessService(BusinessService businessService);
    public boolean deleteBusinessService(long businessServiceId);
    public Optional<BusinessService> getBusinessService(String businessName, Long businessServiceId);
    public Optional<BusinessService> updateBusinessService(long businessServiceId, BusinessService businessService);
    public Page<BusinessService> getBusinessBusinessServices(String businessId, Pageable page, Optional<String> name);
}