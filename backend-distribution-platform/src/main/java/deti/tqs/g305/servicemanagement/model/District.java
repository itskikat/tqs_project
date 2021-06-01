package deti.tqs.g305.servicemanagement.model;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

import java.util.Set;

import lombok.Data;


@Entity
@Data
@Table(name="DISTRICT")
public class District{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(name = "name")
    public String name;

    @OneToMany(targetEntity=City.class)
    private Set<City> cities;

    public District(){

    }
    public District(long id, String name){
        this.id=id;
        this.name=name;
    }

}