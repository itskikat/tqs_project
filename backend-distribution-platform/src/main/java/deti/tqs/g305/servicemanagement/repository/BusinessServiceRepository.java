package deti.tqs.g305.servicemanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import deti.tqs.g305.servicemanagement.model.BusinessService;

import java.util.Optional;


/**
 * BusinessServiceRepository
 */
@Repository
public interface BusinessServiceRepository extends JpaRepository<BusinessService, Long>{
    
    public BusinessService findById(long id);
    public Page<BusinessService> findByBusiness_Email(String business_id, Pageable page);
    public Page<BusinessService> findByBusiness_EmailAndService_NameContains(String business_id, Pageable page, String name);

    @Query(value = "SELECT service_type_id FROM business_service WHERE business_id = :business_id  GROUP BY service_type_id ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    public Long findByBusiness_Email_MostRequestedServiceTypeId(@Param("business_id") String business_id);

}