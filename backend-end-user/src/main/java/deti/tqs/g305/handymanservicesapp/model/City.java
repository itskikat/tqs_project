package deti.tqs.g305.handymanservicesapp.model;

import lombok.Data;

import javax.persistence.*;


@Data
public class City{

    public long id;

    public String name;

    public District district;

    public City(){

    }
    public City(long id, String name, District district){
        this.id=id;
        this.name=name;
        this.district=district;
    }
    
}