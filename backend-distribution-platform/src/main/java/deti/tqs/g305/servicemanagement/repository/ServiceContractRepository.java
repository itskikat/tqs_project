package deti.tqs.g305.servicemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import deti.tqs.g305.servicemanagement.model.ServiceContract;


/**
 * ServiceContractRepository
 */
@Repository
public interface ServiceContractRepository extends JpaRepository<ServiceContract, Long>{

    
}