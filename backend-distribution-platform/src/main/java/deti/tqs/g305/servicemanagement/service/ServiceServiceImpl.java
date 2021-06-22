package deti.tqs.g305.servicemanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Optional;
import java.math.BigInteger;
import java.sql.Timestamp;

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

            Optional<ProviderService> ps = providerServiceRepository.findById(serviceContract.getProviderService().getId());
            if(!ps.isPresent()){
                return Optional.empty();
            }
            serviceContract.setProviderService(ps.get());
        
            BusinessService bs = businessServiceRepository.findById(serviceContract.getBusinessService().getId());
            if(bs == null){
                return Optional.empty();
            }
            serviceContract.setBusinessService(bs);

            Optional<Client> c = clientRepository.findByEmail(serviceContract.getClient().getEmail());
            if(!c.isPresent()){
                return Optional.empty();
            }
            serviceContract.setClient(c.get());
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
                if(sc.getReview()!=0 || scStatus!= ServiceStatus.FINNISHED){
                    return Optional.empty();
                }
            }
            else{
                if(scStatus==ServiceStatus.FINNISHED || scStatus==ServiceStatus.REJECTED){
                    return Optional.empty();
                }
                else if(scStatus==ServiceStatus.WAITING && sc1Status==ServiceStatus.FINNISHED){
                    return Optional.empty();
                }
                else if(scStatus==ServiceStatus.ACCEPTED && (sc1Status==ServiceStatus.WAITING || sc1Status==ServiceStatus.REJECTED)){
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
    public Page<ServiceContract> getServiceContracts(String username, Pageable page, String userType, Optional<ServiceStatus> status, Optional<Long> type) {

        switch (userType) {
            case "Client":
                if(status.isPresent() && type.isPresent()){
                    return serviceContractRepository.findByStatusAndProviderService_Service_IdAndClientEmail(status.get(), type.get(),username, page);
                }
                else if (status.isPresent()){
                    return serviceContractRepository.findByStatusAndClientEmail(status.get(),username, page);
                }
                else if(type.isPresent()){
                    return serviceContractRepository.findByProviderService_Service_IdAndClientEmail(type.get(),username, page);
                }
                return serviceContractRepository.findByClientEmail(username, page);
            case "Provider":
                if(status.isPresent() && type.isPresent()){
                    return serviceContractRepository.findByStatusAndProviderService_Service_IdAndProviderService_Provider_Email(status.get(), type.get(),username, page);
                }
                else if (status.isPresent()){
                    return serviceContractRepository.findByStatusAndProviderService_Provider_Email(status.get(),username, page);
                }
                else if(type.isPresent()){
                    return serviceContractRepository.findByProviderService_Service_IdAndProviderService_Provider_Email(type.get(),username, page);
                }
                return serviceContractRepository.findByProviderService_Provider_Email(username, page);
            case "Business":
                return serviceContractRepository.findByBusinessService_Business_Email(username, page);
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

    // ProviderService
    @Override
    public Optional<ProviderService> saveProviderService(ProviderService providerService) {
        Optional<ProviderService> bs = providerServiceRepository.findById(providerService.getId());

        if(bs.isEmpty() && providerService.getService() != null ) {
            ServiceType st = serviceTypeRepository.findById(providerService.getService().getId());
            if (st == null) {
                return Optional.empty();
            }
            providerService.setService(st);
            return Optional.of(providerServiceRepository.save(providerService));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteProviderService(long providerServiceId){
        Optional<ProviderService> bs = providerServiceRepository.findById(providerServiceId);
        if (bs.isPresent()) {
            providerServiceRepository.delete(bs.get());
            logger.info("ProviderService successfully deleted!");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<ProviderService> updateProviderService(long providerServiceId, ProviderService providerService) {
        Optional<ProviderService> bs = providerServiceRepository.findById(providerServiceId);
        if(bs.isPresent()) {
            ProviderService ps = bs.get();
            if (providerService.getService() != null) {
                ps.setService(providerService.getService());
            }
            if (providerService.getServiceContract() != null) {
                ps.setServiceContract(providerService.getServiceContract());
            }
            if (providerService.getProvider() != null) {
                ps.setProvider(providerService.getProvider());
            }
            ps.setDescription(providerService.getDescription());
            return Optional.of(providerServiceRepository.save(ps));
        }
        return Optional.empty();
    }

    @Override
    public Page<ProviderService> getProviderProviderServices(String providerId, Pageable page, Optional<String> name) {
        if (name.isPresent()) {
            return providerServiceRepository.findByProvider_EmailAndService_NameContains(providerId, page, name.get());
        }
        return providerServiceRepository.findByProvider_Email(providerId, page);
    }

    @Override
    public Optional<ProviderService> getProviderService(String name, Long providerServiceId){
        Optional<ProviderService> bs = providerServiceRepository.findById(providerServiceId);
        if(bs.isPresent()){
            if(!bs.get().getProvider().getEmail().equals(name)){
                bs=Optional.empty();
            }
        }
        return bs;
    }

    // BusinessService
    @Override
    public Optional<BusinessService> saveBusinessService(BusinessService businessService) {
        BusinessService bs = businessServiceRepository.findById(businessService.getId());

        if(bs == null && businessService.getService() != null ) {

            ServiceType st = serviceTypeRepository.findById(businessService.getService().getId());
            if (st == null) {
                return Optional.empty();
            }
            businessService.setService(st);
            return Optional.of(businessServiceRepository.save(businessService));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteBusinessService(long businessServiceId){
        BusinessService bs = businessServiceRepository.findById(businessServiceId);
        if (bs != null) {
            businessServiceRepository.delete(bs);
            logger.info("BusinessService successfully deleted!");
            return true;
        } else {
            return false;
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
    public Page<BusinessService> getBusinessBusinessServices(String businessId, Pageable page, Optional<String> name) {
        if (name.isPresent()) {
            return businessServiceRepository.findByBusiness_EmailAndService_NameContains(businessId, page, name.get());
        }
        return businessServiceRepository.findByBusiness_Email(businessId, page);
    }

    @Override
    public Double getBusinessBusinessServiceProfit(String business_id, Optional<LocalDate> start_date, Optional<LocalDate> end_date) {
        if (start_date.isPresent() && end_date.isPresent()) {
            return businessServiceRepository.findByBusiness_Email_TotalProfitDateInterval(business_id, start_date.get(), end_date.get());
        }
        else {
            List<ServiceContract> scList = serviceContractRepository.findByStatusAndBusinessService_Business_Email(ServiceStatus.FINNISHED, business_id);
            double profit = 0;
            for (ServiceContract serviceContract : scList) {
                double val = serviceContract.getBusinessService().getPrice();
                profit += val;
            }
            return profit;
        }

    }

    @Override
    public Integer getTotalBusinessServiceContracts(String business_id, Optional<LocalDate> start_date, Optional<LocalDate> end_date) {
        if (start_date.isPresent() && end_date.isPresent()) {
            return businessServiceRepository.findByBusiness_Email_TotalContractsFinishedDateInterval(business_id, start_date.get(), end_date.get());
        }
        else {
            List<ServiceContract> scs = serviceContractRepository.findByBusinessService_Business_Email(business_id);
            return scs.size();
        }
    }

    @Override
    public Optional<ServiceType> getBusinessMostRequestedServiceType(String business_id, Optional<LocalDate> start_date, Optional<LocalDate> end_date) {
        long id;
        if (start_date.isPresent() && end_date.isPresent()) {
            if(start_date.get().isBefore(end_date.get()) ){
                id = businessServiceRepository.findByBusiness_Email_MostRequestedServiceTypeIdDateInterval(business_id, start_date.get(), end_date.get());
            }
            else{
                return Optional.empty();
            }
        }
        else {
            id = businessServiceRepository.findByBusiness_Email_MostRequestedServiceTypeId(business_id);
        }
        return Optional.of(serviceTypeRepository.findById(id));
    }

    @Override
    public Optional<Map<LocalDate,Double>> getBusinessProfitHistory(String business_id, LocalDate start_date, LocalDate end_date){
        if(start_date.isBefore(end_date)){
            Map<LocalDate, Double> profitHistory = new TreeMap<LocalDate, Double>();
            List<Object[]> results = businessServiceRepository.findByBusiness_Email_TotalProfitDateInterval_History(business_id, start_date, end_date);
            if(results!=null){
                for(Object[] obj : results){
                    Timestamp t = (Timestamp) obj[0];
                    profitHistory.put(t.toLocalDateTime().toLocalDate() , (Double) obj[1]);
                }
                return Optional.of(profitHistory);
            }
        }
        return Optional.empty();
    }
  
    public Optional<BusinessService> getBusinessService(String name, Long businessServiceId){
        Optional<BusinessService> bs = businessServiceRepository.findById(businessServiceId);
        if (bs.isPresent()) {
            if (!bs.get().getBusiness().getEmail().equals(name)) {
                bs = Optional.empty();
            }
        }
        return bs;
    }

    @Override
    public Optional<Double> getTotalProfit(String provider_id,LocalDate start_date, LocalDate end_date ){
        if(start_date.isBefore(end_date)){
            Double d = providerServiceRepository.getTotalProfit(provider_id, start_date, end_date);
            if(d!=null){
                return Optional.of(d);
            }
            return Optional.of(0.0);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Integer> getTotalFinished(String provider_id,LocalDate start_date, LocalDate end_date ){
        if(start_date.isBefore(end_date)){
            Integer i = providerServiceRepository.getTotalFinished(provider_id, start_date, end_date);
            if(i!=null){
                return Optional.of(i);
            }
            return Optional.of(0);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProviderService> getTotalMostContractsProviderService(String provider_id,LocalDate start_date, LocalDate end_date ){
        if(start_date.isBefore(end_date)){
            Long id = providerServiceRepository.getTotalMostContractsProviderService(provider_id, start_date, end_date);
            if(id!=null){
                return providerServiceRepository.findById(id);
            }
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<ProviderService> getTotalMostProfitProviderService(String provider_id,LocalDate start_date, LocalDate end_date ){
        if(start_date.isBefore(end_date)){
            Long id= providerServiceRepository.getTotalMostProfitProviderService(provider_id, start_date, end_date);
            if(id!=null){
                return providerServiceRepository.findById(id);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Map<LocalDate,Double>> getProfitHistory(String provider_id,LocalDate start_date, LocalDate end_date ){
        if(start_date.isBefore(end_date)){
            Map<LocalDate, Double> profitHistory = new TreeMap<LocalDate, Double>();
            List<Object[]> results = providerServiceRepository.getProfitHistory(provider_id, start_date, end_date);
            if(results!=null){
                for(Object[] obj : results){
                    Timestamp t = (Timestamp) obj[0];
                    profitHistory.put(t.toLocalDateTime().toLocalDate() , (Double) obj[1]);
                }
                return Optional.of(profitHistory);
            }  
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<Map<LocalDate,Integer>> getContractsHistory(String provider_id,LocalDate start_date, LocalDate end_date ){
        if(start_date.isBefore(end_date)){
            Map<LocalDate, Integer> contractHistory = new TreeMap<LocalDate, Integer>();
            List<Object[]> results = providerServiceRepository.getContractsHistory(provider_id, start_date, end_date);
            if(results!=null){
                for(Object[] obj : results){
                    Timestamp t = (Timestamp) obj[0];
                    BigInteger bi = (BigInteger) obj[1];
                    contractHistory.put(t.toLocalDateTime().toLocalDate() ,bi.intValue() );
                }
                return Optional.of(contractHistory);
            }
        }
        return Optional.empty();
    }


}