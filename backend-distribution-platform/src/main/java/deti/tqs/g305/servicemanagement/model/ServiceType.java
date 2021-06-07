package deti.tqs.g305.servicemanagement.model;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

import java.util.Set;

import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Entity
@Data
@Table(name="SERVICE_TYPE")
public class ServiceType{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(name = "name")
    public String name;

    @Column(name="extras")
    public boolean hasExtras;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy="service")
    public Set<BusinessService> businessServices;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy="service")
    public Set<ProviderService> providerServices;

    public ServiceType(){

    }
    public ServiceType( String name, boolean hasExtras){
        this.name=name;
        this.hasExtras= hasExtras;
    }

}