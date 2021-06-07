package deti.tqs.g305.servicemanagement.model;

import lombok.Data;
import lombok.ToString;
import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy="business")
    private Set<BusinessService> businessServices;

    public Business(){

    }

    public Business(String google_id, String username, String email, String full_name,String apikey, String name, String address, String nif){
        super(google_id,username,email,full_name);
        this.apikey=apikey;
        this.name= name;
        this.address= address;
        this.nif = nif;

    }
    

}
