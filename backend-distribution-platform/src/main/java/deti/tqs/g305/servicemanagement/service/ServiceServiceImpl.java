package deti.tqs.g305.servicemanagement.service;

import java.util.List;
import java.util.Optional;

import deti.tqs.g305.servicemanagement.model.BusinessService;
import deti.tqs.g305.servicemanagement.model.Client;
import deti.tqs.g305.servicemanagement.model.ProviderService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.model.ServiceStatus;
import deti.tqs.g305.servicemanagement.repository.ClientRepository;
import deti.tqs.g305.servicemanagement.repository.ServiceContractRepository;
import deti.tqs.g305.servicemanagement.repository.ProviderServiceRepository;
import deti.tqs.g305.servicemanagement.repository.BusinessServiceRepository;
import deti.tqs.g305.servicemanagement.repository.ClientRepository;


import org.springframework.beans.factory.annotation.Autowired;


/**
 * ServiceServiceImpl
 */
@Service
@Transactional
public class ServiceServiceImpl implements ServiceService{

    @Autowired
    private ServiceContractRepository serviceContractRepository;

    @Autowired
    private ProviderServiceRepository providerServiceRepository;

    @Autowired
    private BusinessServiceRepository businessServiceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Optional<ServiceContract> saveServiceContract(ServiceContract serviceContract) {
        ServiceContract sc = serviceContractRepository.findById(serviceContract.getId());

        if(sc == null && serviceContract.getProviderService()!= null && serviceContract.getBusinessService()!= null 
            && serviceContract.getClient()!= null ){

            ProviderService ps = providerServiceRepository.findById(serviceContract.getProviderService().getId());
            if(ps== null){
                return Optional.empty();
            }
            serviceContract.setProviderService(ps);
        
            BusinessService bs = businessServiceRepository.findById(serviceContract.getBusinessService().getId());
            if(bs== null){
                return Optional.empty();
            }
            serviceContract.setBusinessService(bs);

            Client c = clientRepository.findByUsername(serviceContract.getClient().getUsername());
            if(c== null){
                return Optional.empty();
            }
            serviceContract.setClient(c);
            return Optional.of(serviceContractRepository.save(serviceContract));
        }
        
        return Optional.empty();
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
            sc.setReview(serviceContract.getReview());
            sc.setStatus(serviceContract.getStatus());
            return Optional.of(serviceContractRepository.save(sc));
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
    public Optional<BusinessService> saveBusinessService(BusinessService businessService) {
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