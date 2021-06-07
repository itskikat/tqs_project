package deti.tqs.g305.servicemanagement.model;

import javax.persistence.*;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;


@Embeddable
@Entity
@Data
@Table(name="DISTRICT")
public class District{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(name = "name")
    public String name;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(targetEntity=City.class)
    private Set<City> cities;

    public District(){

    }
    public District(long id, String name){
        this.id=id;
        this.name=name;
    }

}