package deti.tqs.g305.handymanservicesapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
public class BusinessService{

    private long id;

    private Business business;

    private ServiceType service;
    
    private double price;

    private List<ServiceContract> serviceContract;

    public BusinessService(){

    }

    public BusinessService(double price, ServiceType service, Business business){
        this.price = price;
        this.service = service;
        this.business = business;
    }
  

}
