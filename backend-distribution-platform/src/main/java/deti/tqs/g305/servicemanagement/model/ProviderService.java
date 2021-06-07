package deti.tqs.g305.servicemanagement.model;

/**
 * ProviderService
 */


import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="PROVIDER_SERVICE")
public class ProviderService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne()
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne()
    @JoinColumn(name = "service_type_id")
    private ServiceType service;

    @Column(name="description")
    private String description;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy="providerService")
    private List<ServiceContract> serviceContract;

    public ProviderService(){

    }

    public ProviderService(String description, Provider provider, ServiceType serviceType){
        this.description=description;
        this.provider=provider;
        this.service= serviceType;
    }
}