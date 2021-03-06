package deti.tqs.g305.servicemanagement.model;

import lombok.Data;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.List;


@Entity
@Data
@Table(name="BUSINESS_SERVICE")
public class BusinessService{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne()
    @JoinColumn(name = "business_id")
    private Business business;

    @ManyToOne()
    @JoinColumn(name = "service_type_id")
    private ServiceType service;
    
    @Column(name = "price")
    private double price;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy="businessService", cascade = { CascadeType.ALL })
    private List<ServiceContract> serviceContract;

    public BusinessService(){

    }

    public BusinessService(double price, ServiceType service, Business business){
        this.price = price;
        this.service = service;
        this.business = business;
    }
  

}
