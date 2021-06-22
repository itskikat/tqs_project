package deti.tqs.g305.handymanservicesapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;


@Data
public class ServiceType {

    public long id;

    public String name;

    public boolean hasExtras;

    public Set<BusinessService> businessServices;

    public Set<ProviderService> providerServices;

    public ServiceType(){

    }
    public ServiceType(String name, boolean hasExtras){
        this.name=name;
        this.hasExtras= hasExtras;
    }

    public void setHasExtras(boolean extra){
        this.hasExtras= extra;
    }
    public boolean getHasExtras(){
        return this.hasExtras;
    }

}