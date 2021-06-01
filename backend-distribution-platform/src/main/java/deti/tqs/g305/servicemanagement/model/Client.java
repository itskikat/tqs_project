package deti.tqs.g305.servicemanagement.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@DiscriminatorValue("Client")
public class Client extends User {

    // Googleid, Username, email, full_name, address, birthdate

    @Column(name = "address")
    private String address;

    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    public Client() {

    }

    public Client(String google_id, String username, String email, String full_name, String address, Date birthdate) {
        super(google_id, username, email, full_name);
        this.address = address;
        this.birthdate = birthdate;
    }
}
