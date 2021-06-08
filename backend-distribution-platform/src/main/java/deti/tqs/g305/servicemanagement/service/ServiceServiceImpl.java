package deti.tqs.g305.servicemanagement.service;

import java.util.List;
import java.util.Optional;

import deti.tqs.g305.servicemanagement.model.BusinessService;
import deti.tqs.g305.servicemanagement.model.Client;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.model.ServiceStatus;
import deti.tqs.g305.servicemanagement.repository.ClientRepository;
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
        
        if(sc !=null){
            ServiceStatus scStatus= sc.getStatus();
            ServiceStatus sc1Status = serviceContract.getStatus();
            if(serviceContract.getReview() != 0){

                // in order to add review the contract has to have finished and can't have a review
                if(sc.getReview()!=0 || scStatus!= ServiceStatus.Finnished){
                    return Optional.empty();
                }
            }
            else{
                if(scStatus==ServiceStatus.Finnished || scStatus==ServiceStatus.Rejected){
                    return Optional.empty();
                }
                else if(scStatus==ServiceStatus.Waiting && sc1Status==ServiceStatus.Finnished){
                    return Optional.empty();
                }
                else if(scStatus==ServiceStatus.Accepted && (sc1Status==ServiceStatus.Waiting || sc1Status==ServiceStatus.Rejected)){
                    return Optional.empty();
                }
            }
            return Optional.of(serviceContractRepository.save(serviceContract));
        }
        return Optional.empty();
    }

    @Override
    public Page<ServiceContract> getServiceContracts(String username, Pageable page, String userType) {

        switch (userType) {
            case "Client":
                return serviceContractRepository.findByClient_Email(username, page);
            case "Provider":
                return serviceContractRepository.findByProviderService_Provider_Email(username, page);
            case "Business":
                return serviceContractRepository.findByBusinessService_Business_Email(username,page);
            default:
                return null;
        }
        
    }

    @Override
    public Optional<ServiceContract> getServiceContract(String username,long serviceContractId) {
        ServiceContract sc = serviceContractRepository.findById(serviceContractId);
        
        if(sc!=null){
            if(sc.getClient().getEmail().equals(username) || sc.getProviderService().getProvider().getEmail().equals(username)
             || sc.getBusinessService().getBusiness().getEmail().equals(username)){
                return Optional.of(sc);
            }
        }
        return Optional.empty();
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