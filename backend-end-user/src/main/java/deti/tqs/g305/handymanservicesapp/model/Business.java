package deti.tqs.g305.handymanservicesapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
public class Business extends User {


    private String apikey;

    private String name;

    private String address;

    private String nif;

    private Set<BusinessService> businessServices;

    public Business(){

    }

    public Business(String email, String full_name,String password,String apikey, String name, String address, String nif) {
        super(email,full_name,password);
        this.apikey=apikey;
        this.name= name;
        this.address= address;
        this.nif = nif;

    }
    

}
