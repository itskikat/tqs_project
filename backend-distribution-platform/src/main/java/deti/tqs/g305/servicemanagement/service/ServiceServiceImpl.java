package deti.tqs.g305.servicemanagement.service;

import java.util.List;
import java.util.Optional;

import deti.tqs.g305.servicemanagement.model.BusinessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import deti.tqs.g305.servicemanagement.model.ServiceContract;


/**
 * ServiceServiceImpl
 */
@Service
@Transactional
public class ServiceServiceImpl implements ServiceService{

    @Override
    public ServiceContract saveServiceContract(ServiceContract serviceContract) {
        return null;
    }

    @Override
    public Optional<ServiceContract> updateServiceContract(long serviceContractId, ServiceContract serviceContract) {
        return null;
    }

    @Override
    public Optional<List<ServiceContract>> getClientServiceContracts(long clientId) {
        return null;
    }

    @Override
    public Optional<List<ServiceContract>> getProviderServiceContracts(long providerId) {
        return null;
    }


    // BusinessService
    @Override
    public BusinessService saveBusinessService(BusinessService businessService) {
        return null;
    }

    @Override
    public String deleteBusinessService(long businessServiceId) {
        return null;
    }

    @Override
    public Optional<BusinessService> updateBusinessService(long businessServiceId, BusinessService businessService) {
        return null;
    }

    @Override
    public Optional<List<BusinessService>> getBusinessBusinessServices(long businessId) {
        return null;
    }




}