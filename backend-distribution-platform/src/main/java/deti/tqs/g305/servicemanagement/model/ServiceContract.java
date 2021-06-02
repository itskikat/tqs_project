package deti.tqs.g305.servicemanagement.model;

/**
 * ServiceContract
 */

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name="SERVICE_CONTRACT")
public class ServiceContract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="contract_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

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

    public ServiceContract(Date date, BusinessService businessService,ProviderService providerService, ServiceStatus status, Client client, int review) {
        this.date=date;
        this.businessService=businessService;
        this.status=status;
        this.client=client;
        this.review=review;
        this.providerService=providerService;
    }
}