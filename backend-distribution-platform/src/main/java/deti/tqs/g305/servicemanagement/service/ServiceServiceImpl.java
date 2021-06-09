package deti.tqs.g305.servicemanagement.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import deti.tqs.g305.servicemanagement.model.*;

import deti.tqs.g305.servicemanagement.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import deti.tqs.g305.servicemanagement.repository.ClientRepository;


import org.springframework.beans.factory.annotation.Autowired;


/**
 * ServiceServiceImpl
 */
@Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

    Logger logger = LoggerFactory.getLogger(ServiceService.class); // to log everything

    @Autowired
    private ServiceContractRepository serviceContractRepository;

    @Autowired
    private ProviderServiceRepository providerServiceRepository;

    @Autowired
    private BusinessServiceRepository businessServiceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

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
                return serviceContractRepository.findByClient_Username(username, page);
            case "Provider":
                return serviceContractRepository.findByProviderService_Provider_Username(username, page);
            case "Business":
                return serviceContractRepository.findByBusinessService_Business_Username(username,page);
            default:
                return null;
        }
        
    }

    @Override
    public Optional<ServiceContract> getServiceContract(String username,long serviceContractId) {
        ServiceContract sc = serviceContractRepository.findById(serviceContractId);
        
        if(sc!=null){
            if(sc.getClient().getUsername().equals(username) || sc.getProviderService().getProvider().getUsername().equals(username)
             || sc.getBusinessService().getBusiness().getUsername().equals(username)){
                return Optional.of(sc);
            }
        }
        return Optional.empty();
    }




    // BusinessService
    @Override
    public Optional<BusinessService> saveBusinessService(BusinessService businessService) {
        BusinessService bs = businessServiceRepository.findById(businessService.getId());

        if(bs == null && businessService.getService() != null && businessService.getServiceContract() != null ) {

            ServiceType st = serviceTypeRepository.findById(businessService.getService().getId());
            if (st == null) {
                return Optional.empty();
            }
            businessService.setService(st);

            List<ServiceContract> scList = serviceContractRepository.findByBusinessServiceId(businessService.getId());
            if (scList.isEmpty()) {
                return Optional.empty();
            }
            businessService.setServiceContract(scList);
            return Optional.of(businessServiceRepository.save(businessService));
        }
        return Optional.empty();
    }

    @Override
    public void deleteBusinessService(long businessServiceId) throws NoSuchElementException {
        BusinessService bs = businessServiceRepository.findById(businessServiceId);
        if (bs != null) {
            businessServiceRepository.delete(bs);
            logger.info("BusinessService successfully deleted!");
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<BusinessService> updateBusinessService(long businessServiceId, BusinessService businessService) {
        BusinessService bs = businessServiceRepository.findById(businessServiceId);
        if(bs != null) {
            if (businessService.getService() != null) {
                bs.setService(businessService.getService());
            }
            if (businessService.getServiceContract() != null) {
                bs.setServiceContract(businessService.getServiceContract());
            }
            if (businessService.getBusiness() != null) {
                bs.setBusiness(businessService.getBusiness());
            }
            bs.setPrice(businessService.getPrice());

            return Optional.of(businessServiceRepository.save(bs));
        }
        return Optional.empty();
    }

    @Override
    public Page<BusinessService> getBusinessBusinessServices(String businessId, Pageable page) {
        return businessServiceRepository.findByBusiness_Id(businessId, page);
    }

}