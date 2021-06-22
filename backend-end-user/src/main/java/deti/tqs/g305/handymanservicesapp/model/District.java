package deti.tqs.g305.handymanservicesapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;


@Data
public class District{

    public long id;

    public String name;

    private Set<City> cities;

    public District(){

    }
    public District(long id, String name){
        this.id=id;
        this.name=name;
    }

}