package deti.tqs.g305.servicemanagement.repository;

import deti.tqs.g305.servicemanagement.model.BusinessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import deti.tqs.g305.servicemanagement.model.ProviderService;

import java.util.Optional;
import java.util.Map;
import java.util.List;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * ProviderServiceRepository
 */
@Repository
public interface ProviderServiceRepository extends JpaRepository<ProviderService, Long>{

    public Optional<ProviderService> findById(long id);
    public Page<ProviderService> findByProvider_Email(String provider_id, Pageable page);
    public Page<ProviderService> findByProvider_EmailAndService_NameContains(String provider_id, Pageable page, String name);

    @Query(value = "SELECT SUM(bs.price) FROM business_service bs, service_contract sc, provider_service ps where ps.provider_id=:provider_id "
      + "AND bs.id=sc.business_service AND ps.id=provider_service AND status=2 AND sc.contract_date>=:start_date AND sc.contract_date<=:end_date", nativeQuery = true)
    public Double getTotalProfit(@Param("provider_id") String provider_id, @Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date);

    @Query(value = "SELECT COUNT(sc.id) FROM  service_contract sc, provider_service ps where ps.provider_id=:provider_id "
      + "AND ps.id=provider_service AND status=2 AND sc.contract_date>=:start_date AND sc.contract_date<=:end_date", nativeQuery = true)
    public Integer getTotalFinished(@Param("provider_id") String provider_id, @Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date);

    @Query(value = "SELECT ps.id  FROM  service_contract sc, provider_service ps where ps.provider_id=:provider_id "
      + "AND ps.id=provider_service AND status=2 AND sc.contract_date>=:start_date AND sc.contract_date<=:end_date GROUP BY ps.id ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    public Long getTotalMostContractsProviderService(@Param("provider_id") String provider_id, @Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date);

    @Query(value = "SELECT ps.id FROM business_service bs, service_contract sc, provider_service ps where ps.provider_id=:provider_id "
      + "AND bs.id=sc.business_service AND ps.id=provider_service AND status=2 AND sc.contract_date>=:start_date AND sc.contract_date<=:end_date  GROUP BY ps.id ORDER BY SUM(bs.price) DESC LIMIT 1", nativeQuery = true)
    public Long getTotalMostProfitProviderService(@Param("provider_id") String provider_id, @Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date);

    @Query(value = "SELECT date_trunc('day', sc.contract_date) as day, SUM(bs.price) as price FROM business_service bs, service_contract sc, provider_service ps where ps.provider_id=:provider_id "
      + "AND bs.id=sc.business_service AND ps.id=provider_service AND status=2 AND sc.contract_date>=:start_date AND sc.contract_date<=:end_date  GROUP BY day ", nativeQuery = true)
    public List<Object[]> getProfitHistory(@Param("provider_id") String provider_id, @Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date);

    @Query(value = "SELECT date_trunc('day', sc.contract_date) as day, COUNT(sc.id) as ncontracts FROM  service_contract sc, provider_service ps where ps.provider_id=:provider_id "
    + "AND ps.id=provider_service AND status=2 AND sc.contract_date>=:start_date AND sc.contract_date<=:end_date  GROUP BY day ", nativeQuery = true)
    public List<Object[]>  getContractsHistory(@Param("provider_id") String provider_id, @Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date);
}