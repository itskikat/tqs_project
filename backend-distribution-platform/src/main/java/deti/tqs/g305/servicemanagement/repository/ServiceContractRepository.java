package deti.tqs.g305.servicemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import deti.tqs.g305.servicemanagement.model.ServiceContract;


/**
 * ServiceContractRepository
 */
@Repository
public interface ServiceContractRepository extends JpaRepository<ServiceContract, Long>{

    public ServiceContract findById(long id);
    
    public Page<ServiceContract> findByClient_Email(String email, Pageable page);

    public Page<ServiceContract> findByProviderService_Provider_Email(String Username, Pageable page);

    public Page<ServiceContract> findByBusinessService_Business_Email(String Username, Pageable page);
}
