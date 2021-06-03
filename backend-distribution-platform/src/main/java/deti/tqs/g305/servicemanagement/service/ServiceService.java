package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.model.ServiceStatus;

import java.util.Optional;
import java.util.List;

/**
 * ServiceService
 */
public interface ServiceService {

    public ServiceContract saveServiceContract(ServiceContract serviceContract);
    public Optional<ServiceContract> updateServiceContract(long serviceContractId, ServiceContract serviceContract);
    public Optional<List<ServiceContract>> getClientServiceContracts(long clientId);
    public Optional<List<ServiceContract>> getProviderServiceContracts(long providerId);
}