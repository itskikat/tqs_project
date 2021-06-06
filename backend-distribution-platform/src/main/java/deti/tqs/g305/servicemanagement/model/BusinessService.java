package deti.tqs.g305.servicemanagement.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="BUSINESS_SERVICE")
public class BusinessService{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne()
    @JoinColumn(name = "business_id")
    private Business business;

    @ManyToOne()
    @JoinColumn(name = "service_type_id")
    private ServiceType service;
    
    @Column(name = "price")
    private int price;

    @OneToMany(mappedBy="businessService")
    private List<ServiceContract> serviceContract;

    public BusinessService(){

    }

    public BusinessService(int price, ServiceType service, Business business){
        this.price=price;
        this.service=service;
        this.business=business;

    }
  

}
