package deti.tqs.g305.servicemanagement.model;

import lombok.Data;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@DiscriminatorValue("Client")
public class Client extends User {

    @Column(name = "address")
    private String address;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy="client")
    private List<ServiceContract> serviceContract;

    public Client() {

    }

    public Client(String email, String password, String full_name, String address, LocalDate birthdate) {
        super(email, full_name, password);
        this.address = address;
        this.birthdate = birthdate;
    }
}
