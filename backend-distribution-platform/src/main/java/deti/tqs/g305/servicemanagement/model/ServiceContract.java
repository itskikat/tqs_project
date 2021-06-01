package deti.tqs.g305.servicemanagement.model;

/**
 * ServiceContract
 */

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="SERVICE_CONTRACT")
public class ServiceContract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    //ProviderServiceId, date, businessId, status (Accepted, Waiting, Finished), userId, review,

    public ServiceContract() {
        
    }

    public ServiceContract(Date date) {
        this.date=date;
    }
}