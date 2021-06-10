package deti.tqs.g305.servicemanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import deti.tqs.g305.servicemanagement.model.BusinessService;

/**
 * BusinessServiceRepository
 */
@Repository
public interface BusinessServiceRepository extends JpaRepository<BusinessService, Long>{
    
    public BusinessService findById(long id);
    public Page<BusinessService> findByBusiness_Id(String business_id, Pageable page);

}