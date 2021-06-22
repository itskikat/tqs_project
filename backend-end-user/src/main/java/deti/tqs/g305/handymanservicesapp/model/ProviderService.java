package deti.tqs.g305.handymanservicesapp.model;

/**
 * ProviderService
 */


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
public class ProviderService {

    private long id;

    private Provider provider;

    private ServiceType service;

    private String description;

    private List<ServiceContract> serviceContract;

    public ProviderService(){

    }

    public ProviderService(String description, Provider provider, ServiceType serviceType){
        this.description=description;
        this.provider=provider;
        this.service= serviceType;
    }
}