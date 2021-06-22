package deti.tqs.g305.servicemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.model.ServiceStatus;

import java.util.List;


/**
 * ServiceContractRepository
 */
@Repository
public interface ServiceContractRepository extends JpaRepository<ServiceContract, Long> {

    public ServiceContract findById(long id);

    public Page<ServiceContract> findAll(Pageable page);

    // Client
    public Page<ServiceContract> findByClientEmail(String email, Pageable page);
    public Page<ServiceContract> findByStatusAndProviderService_Service_IdAndClientEmail(ServiceStatus status, Long type, String username, Pageable page);
    public Page<ServiceContract> findByStatusAndClientEmail(ServiceStatus status,  String username, Pageable page);
    public Page<ServiceContract> findByProviderService_Service_IdAndClientEmail( Long type, String username, Pageable page);

    // Provider
    public List<ServiceContract> findByProviderService_Provider_Email(String Username);
    public Page<ServiceContract> findByProviderService_Provider_Email(String Username, Pageable page);
    public Page<ServiceContract> findByProviderService_Service_IdAndProviderService_Provider_Email( Long type, String username, Pageable page);
    public Page<ServiceContract> findByStatusAndProviderService_Provider_Email(ServiceStatus status, String username, Pageable page);
    public Page<ServiceContract> findByStatusAndProviderService_Service_IdAndProviderService_Provider_Email(ServiceStatus status, Long type, String username, Pageable page);

    //Business
    public Page<ServiceContract> findByBusinessService_Business_Email(String Username, Pageable page);
    public List<ServiceContract> findByStatusAndBusinessService_Business_Email(ServiceStatus status, String business_id);
    public List<ServiceContract> findByBusinessService_Business_Email(String Username);
    public List<ServiceContract> findByBusinessServiceId(long id);
       
}
