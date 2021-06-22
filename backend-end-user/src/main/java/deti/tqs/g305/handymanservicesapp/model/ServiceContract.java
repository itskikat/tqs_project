package deti.tqs.g305.handymanservicesapp.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class ServiceContract {

    private long id;

    private LocalDateTime date;

    private ProviderService providerService;

    private BusinessService businessService;

    private ServiceStatus status;

    private Client client;

    private int review;
    
    public ServiceContract() {
        
    }

    public ServiceContract(BusinessService businessService, ProviderService providerService, ServiceStatus status, Client client, int review) {
        this.businessService=businessService;
        this.status=status;
        this.client=client;
        this.review=review;
        this.providerService=providerService;
    }
}