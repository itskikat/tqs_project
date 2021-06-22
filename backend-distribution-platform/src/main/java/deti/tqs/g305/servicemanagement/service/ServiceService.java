package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.BusinessService;
import deti.tqs.g305.servicemanagement.model.ProviderService;
import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.model.ServiceStatus;

import deti.tqs.g305.servicemanagement.model.ServiceType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;

/**
 * ServiceService
 */
public interface ServiceService {

    //Service Contracts
    public Optional<ServiceContract> saveServiceContract(ServiceContract serviceContract);
    public Optional<ServiceContract> updateServiceContract(long serviceContractId, ServiceContract serviceContract);
    public Page<ServiceContract> getServiceContracts(String username, Pageable page, String userType, Optional<ServiceStatus> status, Optional<Long> type);
    public List<ServiceContract> getProviderServiceContracts(String username);
    public Optional<ServiceContract> getServiceContract(String username,long serviceContractId);

    // ProviderService
    Optional<ProviderService> saveProviderService(ProviderService providerService);
    boolean deleteProviderService(long providerServiceId);
    Optional<ProviderService> updateProviderService(long providerServiceId, ProviderService providerService);
    public Page<ProviderService> getProviderProviderServices(String providerId, Pageable page, Optional<String> name);
    Optional<ProviderService> getProviderService(String name, Long providerServiceId);

    //ProviderServiceStatistics
    public Optional<Double> getTotalProfit(String provider_id,LocalDate start_date, LocalDate end_date );
    public Optional<Integer> getTotalFinished(String provider_id,LocalDate start_date, LocalDate end_date );
    public Optional<ProviderService> getTotalMostContractsProviderService(String provider_id,LocalDate start_date, LocalDate end_date );
    public Optional<ProviderService> getTotalMostProfitProviderService(String provider_id,LocalDate start_date, LocalDate end_date );
    public Optional<Map<LocalDate, Double>> getProfitHistory(String provider_id,LocalDate start_date, LocalDate end_date );
    public Optional<Map<LocalDate, Integer>> getContractsHistory(String provider_id,LocalDate start_date, LocalDate end_date );


    public Optional<BusinessService> saveBusinessService(BusinessService businessService);
    public boolean deleteBusinessService(long businessServiceId);
    public Optional<BusinessService> getBusinessService(String businessName, Long businessServiceId);
    public Optional<BusinessService> updateBusinessService(long businessServiceId, BusinessService businessService);
    public Page<BusinessService> getBusinessBusinessServices(String businessId, Pageable page, Optional<String> name);

    //Business statistics
    public Double getBusinessBusinessServiceProfit(String businessId, Optional<LocalDate> start_date, Optional<LocalDate> end_date);
    public Integer getTotalBusinessServiceContracts(String business_id, Optional<LocalDate> start_date, Optional<LocalDate> end_date);
    public Optional<ServiceType> getBusinessMostRequestedServiceType(String business_id, Optional<LocalDate> start_date, Optional<LocalDate> end_date);
    public Optional<Map<LocalDate,Double>> getBusinessProfitHistory (String business_id, LocalDate start_date, LocalDate end_date);

    public List<ProviderService> getMatches(String clientid, Long serviceId);
}
