package deti.tqs.g305.servicemanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import deti.tqs.g305.servicemanagement.model.BusinessService;

import java.time.LocalDate;
import java.util.List;


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

    @Query(value = "SELECT SUM(bs.price) FROM business_service bs, service_contract sc WHERE bs.business_id = :business_id " +
            "AND sc.business_service = bs.id AND status = 2 AND sc.contract_date > :start_date AND sc.contract_date < :end_date ", nativeQuery = true)
    public Double findByBusiness_Email_TotalProfitDateInterval(@Param("business_id") String business_id, @Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date);

    @Query(value = "SELECT COUNT(sc.id) FROM service_contract sc, business_service bs where bs.business_id = :business_id "
            + "AND sc.business_service = bs.id AND status = 2 AND sc.contract_date > :start_date AND sc.contract_date < :end_date", nativeQuery = true)
    public Integer findByBusiness_Email_TotalContractsFinishedDateInterval(@Param("business_id") String business_id, @Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date);

    @Query(value = "SELECT service_type_id FROM business_service bs, service_contract sc WHERE bs.business_id = :business_id " +
            "AND sc.business_service = bs.id AND sc.contract_date > :start_date AND sc.contract_date < :end_date " +
            "GROUP BY service_type_id ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    public Long findByBusiness_Email_MostRequestedServiceTypeIdDateInterval(@Param("business_id") String business_id, @Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date);

    @Query(value = "SELECT date_trunc('day', sc.contract_date) AS day, SUM(bs.price) AS price FROM business_service bs, service_contract sc  WHERE bs.business_id = :business_id "
            + "AND sc.business_service = bs.id AND status = 2 AND sc.contract_date > :start_date AND sc.contract_date < :end_date  GROUP BY day ", nativeQuery = true)
    public List<Object[]> findByBusiness_Email_TotalProfitDateInterval_History(@Param("business_id") String business_id, @Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date);
}