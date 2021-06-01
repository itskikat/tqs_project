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
@Table(name="SERVICE_TYPE")
public class ServiceType{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(name = "name")
    public String name;

    @Column(name="extras")
    public boolean hasExtras;

    @OneToMany(mappedBy="service")
    public Set<BusinessService> businessServices;

    public ServiceType(){

    }
    public ServiceType(long id, String name, boolean hasExtras){
        this.id=id;
        this.name=name;
        this.hasExtras= hasExtras;
    }

}