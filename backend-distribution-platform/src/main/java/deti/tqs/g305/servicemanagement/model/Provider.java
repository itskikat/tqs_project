package deti.tqs.g305.servicemanagement.model;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@DiscriminatorValue("Provider")
public class Provider extends User {

    @Column(name = "category")
    private String category;

    @ElementCollection
    @MapKeyColumn(name = "day_of_week")
    @Column(name = "working_hours")
    private Map<Integer, String> working_hours;

    @ElementCollection
    @Column(name = "location_city")
    private List<City> location_city;

    @ElementCollection
    @Column(name = "location_district")
    private List<District> location_district;

    @OneToMany(mappedBy="provider")
    private Set<ProviderService> providerServices;

    @Column(name = "nif")
    private Integer nif;

    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;


    public Provider() {

    }

    public Provider(String google_id, String username, String email, String full_name,
                    Map<Integer, String> working_hours, List<City> location_city, List<District> location_district, int nif, Date birthdate) {
        super(google_id, username, email, full_name);
        this.working_hours = working_hours;
        this.location_city = location_city;
        this.location_district = location_district;
        this.nif = nif;
        this.birthdate = birthdate;
    }

}