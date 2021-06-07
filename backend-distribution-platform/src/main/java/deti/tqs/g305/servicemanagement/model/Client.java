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

    public Client(String google_id, String username, String email, String full_name, String address, LocalDate birthdate) {
        super(google_id, username, email, full_name);
        this.address = address;
        this.birthdate = birthdate;
    }
}
