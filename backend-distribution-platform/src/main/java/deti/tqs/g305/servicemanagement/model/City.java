package deti.tqs.g305.servicemanagement.model;

import javax.persistence.*;

import lombok.Data;


@Embeddable
@Entity
@Data
@Table(name="CITY")
public class City{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(name = "name")
    public String name;

    @ManyToOne()
    @JoinColumn(name = "district_id")
    public District district;

    public City(){

    }
    public City( String name, District district){
        this.name=name;
        this.district=district;
    }
    
}