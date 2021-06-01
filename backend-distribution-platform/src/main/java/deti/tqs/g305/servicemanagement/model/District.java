package deti.tqs.g305.servicemanagement.model;

import javax.persistence.*;

import java.util.Set;

import lombok.Data;


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

    @OneToMany(targetEntity=City.class)
    private Set<City> cities;



}