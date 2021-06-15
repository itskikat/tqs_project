package deti.tqs.g305.servicemanagement.model;
import org.hibernate.annotations.CreationTimestamp;

/**
 * ServiceContract
 */

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="SERVICE_CONTRACT")
public class ServiceContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="contract_date")
    @CreationTimestamp
    private LocalDateTime date;

    @ManyToOne()
    @JoinColumn(name = "provider_service")
    private ProviderService providerService;

    @ManyToOne()
    @JoinColumn(name = "business_service")
    private BusinessService businessService;

    @Enumerated(EnumType.ORDINAL)
    private ServiceStatus status;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name="review")  
    private int review;
    
    public ServiceContract() {
        
    }

    public ServiceContract(BusinessService businessService,ProviderService providerService, ServiceStatus status, Client client, int review) {
        this.businessService=businessService;
        this.status=status;
        this.client=client;
        this.review=review;
        this.providerService=providerService;
    }
}