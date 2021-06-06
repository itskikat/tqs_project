package deti.tqs.g305.servicemanagement.service;

import java.util.List;
import java.util.Optional;

import deti.tqs.g305.servicemanagement.model.BusinessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.repository.ServiceContractRepository;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * ServiceServiceImpl
 */
@Service
@Transactional
public class ServiceServiceImpl implements ServiceService{

    @Autowired
    private ServiceContractRepository serviceContractRepository;

    @Override
    public ServiceContract saveServiceContract(ServiceContract serviceContract) {
        return serviceContractRepository.save(serviceContract);
    }

    @Override
    public Optional<ServiceContract> updateServiceContract(long serviceContractId, ServiceContract serviceContract) {
        ServiceContract sc = serviceContractRepository.findById(serviceContractId);
        if(sc ==null){
            return Optional.of(serviceContractRepository.save(serviceContract));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<ServiceContract>> getServiceContracts(long Id) {
        return null;
    }

    @Override
    public Optional<ServiceContract> getServiceContract(long userId,long serviceContractId) {
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