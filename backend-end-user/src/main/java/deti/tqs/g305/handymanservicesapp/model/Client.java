package deti.tqs.g305.handymanservicesapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Data
public class Client extends User {

    private String address;

    private LocalDate birthdate;

    private List<ServiceContract> serviceContract;

    public Client() {

    }

    public Client(String email, String password, String full_name, String address, LocalDate birthdate) {
        super(email, full_name, password);
        this.address = address;
        this.birthdate = birthdate;
    }
}
