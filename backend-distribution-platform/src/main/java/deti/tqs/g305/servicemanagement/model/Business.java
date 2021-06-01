package deti.tqs.g305.servicemanagement.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@DiscriminatorValue("Business")
public class Business extends User {


    @Column(name = "apikey")
    private String apikey;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "nif")
    private String nif;

    public Business(){

    }

    public Business(String apikey, String name, String address, String nif){
        this.apikey=apikey;
        this.name= name;
        this.address= address;
        this.nif = nif;

    }
    

}
